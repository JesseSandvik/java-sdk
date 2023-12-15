package com.blckroot.sdk.command.orchestrator.execution;

import com.blckroot.sdk.command.Command;


public class CommandOrchestratorExecution {
    private Command rootCommand;
    private CommandOrchestratorExecutionState commandExecutionState;
    private String[] arguments;
    private Command calledCommand;
    int exitCode = 0;

    public Command getRootCommand() {
        return rootCommand;
    }

    public void setRootCommand(Command rootCommand) {
        this.rootCommand = rootCommand;
    }

    public CommandOrchestratorExecutionState getCommandExecutionState() {
        return commandExecutionState;
    }

    public void setCommandExecutionState(CommandOrchestratorExecutionState commandExecutionState) {
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
}
