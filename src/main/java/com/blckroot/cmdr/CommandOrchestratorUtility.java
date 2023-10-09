package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;

class CommandOrchestratorUtility implements CommandOrchestratorContract {
    private final ExecutableCommand parentCommand;

    CommandOrchestratorUtility(ExecutableCommand parentCommand) {
        this.parentCommand = parentCommand;
    }

    public Integer execute(String[] arguments) {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(parentCommand);
        commandLineBuilder.addStandardUsageHelp();

        if (parentCommand.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelp();
        }

        return commandLineBuilder.build().execute(arguments);
    }
}
