package com.blckroot.sdk.command.orchestrator.model;

import com.blckroot.sdk.command.Command;
import picocli.CommandLine;

public class CommandExecution {
    private Command command;
    private CommandLine picocliCommandLine;
    private String[] arguments;
    private Integer exitCode;

    public CommandExecution(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public CommandLine getPicocliCommandLine() {
        return picocliCommandLine;
    }

    public void setPicocliCommandLine(CommandLine picocliCommandLine) {
        this.picocliCommandLine = picocliCommandLine;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public Integer getExitCode() {
        return exitCode;
    }

    public void setExitCode(Integer exitCode) {
        this.exitCode = exitCode;
    }
}
