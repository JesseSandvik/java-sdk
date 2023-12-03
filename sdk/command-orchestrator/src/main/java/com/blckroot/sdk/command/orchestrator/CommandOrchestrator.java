package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.decorator.ExecuteCommandAsPlugin;
import com.blckroot.sdk.command.decorator.SetAttributesFromPropertiesFile;
import com.blckroot.sdk.command.orchestrator.model.CommandExecution;
import com.blckroot.sdk.command.orchestrator.state.CommandExecutionState;

public class CommandOrchestrator {
    private final CommandExecution commandExecution;
    private CommandExecutionState commandExecutionState;

    public CommandOrchestrator(Command command) {
        String propertiesFileDirectory = System.getProperty("command.orchestrator.config.dir");
        Command commandToExecute =
                new SetAttributesFromPropertiesFile(new ExecuteCommandAsPlugin(command), propertiesFileDirectory);
        this.commandExecution = new CommandExecution(commandToExecute);
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
