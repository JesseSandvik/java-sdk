package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.orchestrator.execution.CommandOrchestratorExecution;
import com.blckroot.sdk.command.orchestrator.execution.CommandOrchestratorExecutionState;

import static com.blckroot.sdk.command.orchestrator.execution.CommandOrchestratorExecutionState.*;

public class CommandOrchestrator {
    private final CommandOrchestratorExecution commandOrchestratorExecution;
    private CommandOrchestratorExecutionState commandOrchestratorExecutionState;

    public CommandOrchestrator(Command command) {
        this.commandOrchestratorExecution = new CommandOrchestratorExecution();
        this.commandOrchestratorExecution.setRootCommand(command);
        this.commandOrchestratorExecutionState = INITIAL;
    }

    public Integer execute(String[] arguments) {
        commandOrchestratorExecution.setArguments(arguments);

        while (commandOrchestratorExecutionState != FINISHED && commandOrchestratorExecution.getExitCode() == 0) {
            commandOrchestratorExecution.setExitCode(
                    commandOrchestratorExecutionState.execute(commandOrchestratorExecution));
            commandOrchestratorExecutionState = commandOrchestratorExecutionState.transitionToNextState();
        }
        return commandOrchestratorExecution.getExitCode();
    }
}
