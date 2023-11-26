package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.Command;
import picocli.CommandLine;

class GenerateCommandLine extends CommandOrchestratorUtilityDecorator {
    GenerateCommandLine(CommandOrchestratorUtility commandOrchestratorUtility) {
        super(commandOrchestratorUtility);
    }

    private CommandLine buildCommandLine(Command command) {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(command).addStandardUsageHelpOption();

        if (command.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelpOption();
        }

        if (!command.getPositionalParameters().isEmpty()) {
            command.getPositionalParameters()
                    .forEach(commandLineBuilder::addPositionalParameter);
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

    @Override
    public Integer execute(String[] arguments) {
        commandOrchestratorUtility.setCommandLine(buildCommandLine(this.commandOrchestratorUtility.getCommand()));
        return super.execute(arguments);
    }
}
