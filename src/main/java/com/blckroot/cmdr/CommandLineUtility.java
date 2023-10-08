package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;

class CommandLineUtility {
    private final ExecutableCommand parentCommand;

    CommandLineUtility(ExecutableCommand parentCommand) {
        this.parentCommand = parentCommand;
    }

    public Integer execute(String[] args) {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(parentCommand);
        commandLineBuilder.addStandardUsageHelp();

        if (parentCommand.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelp();
        }

        return commandLineBuilder.build().execute(args);
    }
}
