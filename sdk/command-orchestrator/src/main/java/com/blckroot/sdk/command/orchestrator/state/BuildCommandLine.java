package com.blckroot.sdk.command.orchestrator.state;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.orchestrator.model.CommandExecution;
import picocli.CommandLine;

class BuildCommandLine {

    public static void run(CommandExecution commandExecution) {
        CommandLine picocliCommandLine = buildCommandLine(commandExecution.getCommand());
        commandExecution.setPicocliCommandLine(picocliCommandLine);
    }

    private static CommandLine buildCommandLine(Command command) {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(command)
                .addCustomUsageHelpFormat()
                .addStandardUsageHelpOption();

        if (command.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelpOption();
        }

        if (!command.getPositionalParameters().isEmpty()) {
            command.getPositionalParameters().forEach(commandLineBuilder::addPositionalParameter);
        }

        if (!command.getOptions().isEmpty()) {
            command.getOptions().forEach(commandLineBuilder::addOption);
        }

        if (!command.getSubcommands().isEmpty()) {
            command.getSubcommands().forEach(subcommand -> {
                commandLineBuilder.addSubcommand(buildCommandLine(subcommand));
            });
        }
        return commandLineBuilder.build();
    }
}
