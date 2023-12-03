package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.orchestrator.model.CommandExecution;
import com.blckroot.sdk.command.orchestrator.state.CommandExecutionState;

public class CommandOrchestrator {
    private final CommandExecution commandExecution;
    private CommandExecutionState commandExecutionState;

    public CommandOrchestrator(Command command) {
        this.commandExecution = new CommandExecution(command);
        this.commandExecutionState = CommandExecutionState.BUILD_COMMAND_LINE;
    }

    public void execute(String[] arguments) throws Exception {
        commandExecution.setArguments(arguments);
        while (commandExecutionState != null) {
            commandExecutionState.execute(commandExecution);

            if (commandExecution.getExitCode() == null) {
                commandExecutionState = commandExecutionState.transitionToNextState();
            } else {
                commandExecutionState = CommandExecutionState.EXIT;
            }
        }
    }
}
