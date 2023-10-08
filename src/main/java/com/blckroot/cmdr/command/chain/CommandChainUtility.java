package com.blckroot.cmdr.command.chain;

import com.blckroot.cmd.command.ExecutableCommand;

class CommandChainUtility implements CommandChainContract {
    private final ExecutableCommand parentCommand;

    CommandChainUtility(ExecutableCommand parentCommand) {
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
