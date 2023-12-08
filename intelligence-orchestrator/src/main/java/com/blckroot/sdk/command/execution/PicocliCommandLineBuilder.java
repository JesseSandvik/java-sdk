package com.blckroot.sdk.command.execution;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import picocli.CommandLine;

class PicocliCommandLineBuilder {
    private final Command command;
    private final CommandLine.Model.CommandSpec commandSpec;

    public PicocliCommandLineBuilder(Command command) {
        this.command = command;
        this.commandSpec = CommandLine.Model.CommandSpec.create().name(command.getName());
    }

    public PicocliCommandLineBuilder addCustomUsageHelpFormat(Boolean addCustomUsageHelpFormat) {
        if (addCustomUsageHelpFormat) {
            commandSpec.usageMessage()
                    .abbreviateSynopsis(true)
                    .parameterListHeading("%nPositional Parameters:%n")
                    .optionListHeading("%nOptions:%n")
                    .commandListHeading("%nCommands:%n");

            if (command.getSynopsis() != null) {
                commandSpec.usageMessage().description(command.getSynopsis());
            }

            if (command.getDescription() != null) {
                commandSpec.usageMessage().footer("%n" + command.getDescription() + "%n");
            }
        }
        return this;
    }

    public PicocliCommandLineBuilder addStandardUsageHelpOption(Boolean addStandardUsageHelpOption) {
        if (addStandardUsageHelpOption) {
            commandSpec.addOption(CommandLine.Model.OptionSpec
                    .builder("-h", "--help")
                    .description("Show this help message and exit.")
                    .usageHelp(true)
                    .build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addStandardVersionHelpOption(Boolean addStandardVersionHelpOption) {
        if (addStandardVersionHelpOption && command.getVersion() != null) {
            commandSpec.version(command.getVersion());
            commandSpec.addOption(CommandLine.Model.OptionSpec
                    .builder("-v", "--version")
                    .description("Print version information and exit.")
                    .versionHelp(true)
                    .build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addDisableInteractiveInputOption(Boolean addDisableInteractiveInputOption) {
        if (addDisableInteractiveInputOption) {
            commandSpec.addOption(CommandLine.Model.OptionSpec
                    .builder("--no-input")
                    .description("Disable interactive input.")
                    .type(Boolean.class)
                    .build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addPositionalParameter(PositionalParameter positionalParameter) {
        if (positionalParameter.getLabel() != null && positionalParameter.getSynopsis() != null) {
            commandSpec.addPositional(CommandLine.Model.PositionalParamSpec
                    .builder()
                    .paramLabel(positionalParameter.getLabel())
                    .description(positionalParameter.getSynopsis())
                    .build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addOption(Option option) {
        if (option.getLongName() != null && option.getSynopsis() != null) {
            CommandLine.Model.OptionSpec.Builder optionSpecBuilder = null;
            if (option.getShortName() != null) {
                optionSpecBuilder = CommandLine.Model.OptionSpec.builder(option.getShortName(), option.getLongName());
            } else {
                optionSpecBuilder = CommandLine.Model.OptionSpec.builder(option.getLongName());
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

    public PicocliCommandLineBuilder addSubcommand(CommandLine commandLine) {
        commandSpec.addSubcommand(commandLine.getCommandName(), commandLine);
        return this;
    }

    public CommandLine build() {
        return new CommandLine(commandSpec);
    }
}
