package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.Command;

public class CommandOrchestrator implements CommandOrchestratorContract {
    private final CommandOrchestratorUtility commandOrchestratorUtility;

    public CommandOrchestrator(Command command) {
        this.commandOrchestratorUtility = new CommandOrchestratorUtility(command);
    }

    @Override
    public Integer execute(String[] arguments) {
        return commandOrchestratorUtility.execute(arguments);
    }
}
