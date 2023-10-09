package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;

public class CommandOrchestrator implements CommandOrchestratorContract {
    private final CommandOrchestratorUtility commandChainUtility;

    public CommandOrchestrator(ExecutableCommand executableCommand) {
        this.commandChainUtility = new CommandOrchestratorUtility(executableCommand);
    }

    @Override
    public Integer execute(String[] arguments) throws Exception {
        return this.commandChainUtility.execute(arguments);
    }
}
