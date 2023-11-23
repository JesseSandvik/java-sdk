package com.blckroot.sdk.command.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

class CommandLineBuilder {
    private final FrameworkBaseCommand frameworkBaseCommand;
    private final CommandSpec commandSpec;

    CommandLineBuilder(FrameworkBaseCommand frameworkBaseCommand) {
        this.frameworkBaseCommand = frameworkBaseCommand;
        this.commandSpec = CommandSpec.create().name(frameworkBaseCommand.getName());
    }

    public CommandLineBuilder addCustomUsageHelpFormat(CommandSpec commandSpec) {
        commandSpec.usageMessage()
                .abbreviateSynopsis(true)
                .parameterListHeading("%nPositional Parameters:%n")
                .optionListHeading("%nOptions:%n")
                .commandListHeading("%nCommands:%n");

        if (frameworkBaseCommand.getSynopsis() != null) {
            commandSpec.usageMessage().description(frameworkBaseCommand.getSynopsis());
        }

        if (frameworkBaseCommand.getDescription() != null) {
            commandSpec.usageMessage().footer("%n" + frameworkBaseCommand.getDescription() + "%n");
        }

        return this;
    }

    public CommandLineBuilder addStandardUsageHelpOption(CommandSpec commandSpec) {
        commandSpec.addOption(OptionSpec
                .builder("-h", "--help")
                .usageHelp(true)
                .build());
        return this;
    }

    public CommandLineBuilder addStandardVersionHelpOption(CommandSpec commandSpec) {
        commandSpec.addOption(OptionSpec
                .builder("-v", "--version")
                .versionHelp(true)
                .build());
        return this;
    }

    public CommandLineBuilder addPositionalParameter(CommandSpec commandSpec, PositionalParameter positionalParameter) {
        if (positionalParameter.getLabel() != null && positionalParameter.getSynopsis() != null) {
            commandSpec.addPositional(PositionalParamSpec
                    .builder()
                    .paramLabel(positionalParameter.getLabel())
                    .description(positionalParameter.getSynopsis())
                    .build());
        }
        return this;
    }

    public CommandLineBuilder addOption(CommandSpec commandSpec, Option option) {
        if (option.getLongName() != null && option.getSynopsis() != null) {
            OptionSpec.Builder optionSpecBuilder = null;
            if (option.getShortName() != null) {
                optionSpecBuilder = OptionSpec.builder(option.getShortName(), option.getLongName());
            } else {
                optionSpecBuilder = OptionSpec.builder(option.getLongName());
            }
            optionSpecBuilder.description(option.getSynopsis());

            if (option.getLabel() != null) {
                optionSpecBuilder
                        .paramLabel(option.getLabel())
                        .type(String.class);
            } else {
                optionSpecBuilder.type(Boolean.class);
            }

            commandSpec.addOption(optionSpecBuilder.build());
        }
        return this;
    }

    public CommandLineBuilder addSubcommand(CommandSpec commandSpec, FrameworkBaseCommand frameworkBaseCommand) {
        commandSpec.addSubcommand(frameworkBaseCommand.getName(), new CommandLineBuilder(frameworkBaseCommand).build());
        return this;
    }

    public CommandLine build() {
        addCustomUsageHelpFormat(commandSpec);
        addStandardUsageHelpOption(commandSpec);

        if (frameworkBaseCommand.getVersion() != null) {
            addStandardVersionHelpOption(commandSpec);
        }

        if (!frameworkBaseCommand.getPositionalParameters().isEmpty()) {
            frameworkBaseCommand.getPositionalParameters()
                    .forEach(positionalParameter -> addPositionalParameter(commandSpec, positionalParameter));
        }

        if (!frameworkBaseCommand.getOptions().isEmpty()) {
            frameworkBaseCommand.getOptions().forEach(option -> addOption(commandSpec, option));
        }

        if (!frameworkBaseCommand.getFrameworkSubcommands().isEmpty()) {
            frameworkBaseCommand.getFrameworkSubcommands()
                    .forEach(frameworkBaseSubcommand -> addSubcommand(commandSpec, frameworkBaseSubcommand));
        }

        return new CommandLine(commandSpec);
    }
}
