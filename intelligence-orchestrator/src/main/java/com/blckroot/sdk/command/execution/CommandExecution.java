package com.blckroot.sdk.command.execution;

import com.blckroot.sdk.command.Command;

import static com.blckroot.sdk.command.execution.CommandExecutionState.*;

class CommandExecution {
    private final Command rootCommand;
    private CommandExecutionState commandExecutionState;
    private String[] arguments;
    private Command calledCommand;
    int exitCode = 0;

    public CommandExecution(Command command) {
        this.rootCommand = command;
        this.commandExecutionState = INITIAL;
    }

    public Command getRootCommand() {
        return rootCommand;
    }

    public CommandExecutionState getCommandExecutionState() {
        return commandExecutionState;
    }

    public void setCommandExecutionState(CommandExecutionState commandExecutionState) {
        this.commandExecutionState = commandExecutionState;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public Command getCalledCommand() {
        return calledCommand;
    }

    public void setCalledCommand(Command calledCommand) {
        this.calledCommand = calledCommand;
    }

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public Integer execute(String[] arguments) {
        this.arguments = arguments;

        while (commandExecutionState != FINISHED) {
            commandExecutionState.execute(this);
            commandExecutionState = commandExecutionState.transitionToNextState();
        }
        return exitCode;
    }
}
