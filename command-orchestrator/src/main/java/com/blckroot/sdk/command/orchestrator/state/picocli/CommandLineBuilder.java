package com.blckroot.sdk.command.orchestrator.state.picocli;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Model.PositionalParamSpec;

class CommandLineBuilder {
    private final Command command;
    private final CommandSpec commandSpec;

    CommandLineBuilder(Command command) {
        this.command = command;
        this.commandSpec = CommandSpec.create().name(command.getName());
    }

    public CommandLineBuilder addCustomUsageHelpFormat() {
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

        return this;
    }

    public CommandLineBuilder addStandardUsageHelpOption() {
        commandSpec.addOption(OptionSpec
                .builder("-h", "--help")
                .description("Show this help message and exit.")
                .usageHelp(true)
                .build());
        return this;
    }

    public CommandLineBuilder addStandardVersionHelpOption() {
        commandSpec.version(command.getVersion());

        commandSpec.addOption(OptionSpec
                .builder("-v", "--version")
                .description("Print version information and exit.")
                .versionHelp(true)
                .build());
        return this;
    }

    public CommandLineBuilder addDisableInteractiveInputOption() {
        commandSpec.addOption(OptionSpec
                .builder("--no-input")
                .description("Disable interactive input.")
                .type(Boolean.class)
                .build());
        return this;
    }

    public CommandLineBuilder addPositionalParameter(PositionalParameter positionalParameter) {
        if (positionalParameter.getLabel() != null && positionalParameter.getSynopsis() != null) {
            commandSpec.addPositional(PositionalParamSpec
                    .builder()
                    .paramLabel(positionalParameter.getLabel())
                    .description(positionalParameter.getSynopsis())
                    .build());
        }
        return this;
    }

    public CommandLineBuilder addOption(Option option) {
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

    public CommandLineBuilder addSubcommand(CommandLine commandLine) {
        commandSpec.addSubcommand(commandLine.getCommandName(), commandLine);
        return this;
    }

    public CommandLine build() {
        return new CommandLine(commandSpec);
    }
}