package com.blckroot.sdk.command;

import com.blckroot.sdk.command.orchestrator.CommandOrchestrator;

public class Main {
    public static void main(String[] args) {
        System.setProperty("app.config.dir", "command-orchestrator/src/test/resources/");
        Command command = CommandFactory.create("test");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        int exitCode = commandOrchestrator.execute(args);
        System.exit(exitCode);
    }
}
