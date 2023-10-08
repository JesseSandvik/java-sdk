package com.blckroot.cmdr.command.chain;

import com.blckroot.cmd.command.ExecutableCommand;

public class CommandChain implements CommandChainContract {
    private final CommandChainUtility commandChainUtility;

    public CommandChain(ExecutableCommand executableCommand) {
        this.commandChainUtility = new CommandChainUtility(executableCommand);
    }

    @Override
    public Integer execute(String[] arguments) {
        return this.commandChainUtility.execute(arguments);
    }
}
