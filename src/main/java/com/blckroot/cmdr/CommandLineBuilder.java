package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;
import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;
import picocli.CommandLine;
import picocli.CommandLine.Model.*;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

class CommandLineBuilder {
    private final ExecutableCommand executableCommand;
    private final CommandSpec commandSpec;

    CommandLineBuilder(ExecutableCommand executableCommand) {
        this.executableCommand = executableCommand;
        this.commandSpec = CommandSpec.create().name(executableCommand.getName());

        if (executableCommand.getVersion() != null) {
            this.commandSpec.version(executableCommand.getVersion());
        }

        if (executableCommand.getUsageDescriptionSynopsis() != null) {
            this.commandSpec.usageMessage().description(executableCommand.getUsageDescriptionSynopsis());
        }

        this.commandSpec.usageMessage()
                .optionListHeading("\nOptions:%n")
                .parameterListHeading("\nParameters:%n")
                .commandListHeading("\nCommands:%n");
    }

    public CommandLineBuilder addStandardUsageHelp() {
        this.commandSpec.addOption(
                OptionSpec
                        .builder(new String[]{"-h", "--help"})
                        .usageHelp(true)
                        .build()
        );
        return this;
    }

    public CommandLineBuilder addStandardVersionHelp() {
        this.commandSpec.addOption(
                OptionSpec
                        .builder(new String[]{"-v", "--version"})
                        .versionHelp(true)
                        .build()
        );
        return this;
    }

    private void addPositionalParameter(PositionalParameter positionalParameter) {
        this.commandSpec.addPositional(
                PositionalParamSpec
                        .builder()
                        .paramLabel(positionalParameter.getLabel())
                        .description(positionalParameter.getDescription())
                        .build()
        );
    }

    private void addOption(Option option) {
        OptionSpec.Builder optionSpecBuilder;

        if (option.getShortestName() != null) {
            optionSpecBuilder = OptionSpec.builder(
                    new String[]{option.getShortestName(), option.getLongestName()});
        } else {
            optionSpecBuilder = OptionSpec.builder(option.getLongestName());
        }

        optionSpecBuilder.description(option.getDescription());

        if (option.getParameterLabel() != null) {
            optionSpecBuilder
                    .paramLabel(option.getParameterLabel())
                    .type(String.class);
        } else {
            optionSpecBuilder.type(Boolean.class);
        }

        this.commandSpec.addOption(optionSpecBuilder.build());
    }

    private void addSubcommand(CommandLine commandLine) {
        this.commandSpec.addSubcommand(commandLine.getCommandName(), commandLine);
    }

    private void addPositionalParameters(List<PositionalParameter> positionalParameters) {
        for (PositionalParameter positionalParameter : positionalParameters) {
            addPositionalParameter(positionalParameter);
        }
    }

    private void addOptions(List<Option> options) {
        for (Option option :options) {
            addOption(option);
        }
    }

    private void buildSubcommands(List<ExecutableCommand> subcommands) {
        Queue<ExecutableCommand> subcommandsQueue = new ArrayDeque<>(subcommands);

        while (!subcommandsQueue.isEmpty()) {
            ExecutableCommand currentSubcommand = subcommandsQueue.remove();

            if (!currentSubcommand.getExecutableSubcommands().isEmpty()) {
                subcommandsQueue.addAll(currentSubcommand.getExecutableSubcommands());
            }

            CommandLineBuilder commandLineBuilder = new CommandLineBuilder(currentSubcommand);
            commandLineBuilder.addStandardVersionHelp();

            if (currentSubcommand.getVersion() != null) {
                commandLineBuilder.addStandardVersionHelp();
            }

            addSubcommand(commandLineBuilder.build());
        }
    }

    public CommandLine build() {
        if (!executableCommand.getPositionalParameters().isEmpty()) {
            addPositionalParameters(executableCommand.getPositionalParameters());
        }

        if (!executableCommand.getOptions().isEmpty()) {
            addOptions(executableCommand.getOptions());
        }

        if (!executableCommand.getExecutableSubcommands().isEmpty()) {
            buildSubcommands(executableCommand.getExecutableSubcommands());
        }
        return new CommandLine(this.commandSpec);
    }
}
