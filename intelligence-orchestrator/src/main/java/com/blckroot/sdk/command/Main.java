package com.blckroot.sdk.command;

import com.blckroot.sdk.command.execution.CommandExecutionProcessor;

public class Main {
    public static void main(String[] args) throws Exception {
        Command command = CommandFactory.create("test");
        CommandExecutionProcessor commandExecutionProcessor = new CommandExecutionProcessor(command);
        int exitCode = commandExecutionProcessor.execute(args);
        System.exit(exitCode);
    }
}
