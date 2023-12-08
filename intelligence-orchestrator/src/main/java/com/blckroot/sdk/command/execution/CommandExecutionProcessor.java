package com.blckroot.sdk.command.execution;

import com.blckroot.sdk.command.Command;

public class CommandExecutionProcessor {
    private final CommandExecution commandExecution;

    public CommandExecutionProcessor(Command command) {
        this.commandExecution = new CommandExecution(command);
    }

    public Integer execute(String[] arguments) {
        return commandExecution.execute(arguments);
    }
}
