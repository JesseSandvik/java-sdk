package com.blckroot.sdk.command.orchestrator;

abstract class CommandOrchestratorUtilityDecorator implements CommandOrchestratorContract {
    protected final CommandOrchestratorUtility commandOrchestratorUtility;

    CommandOrchestratorUtilityDecorator(CommandOrchestratorUtility commandOrchestratorUtility) {
        this.commandOrchestratorUtility = commandOrchestratorUtility;
    }

    @Override
    public Integer execute(String[] arguments) {
        return commandOrchestratorUtility.execute(arguments);
    }
}
