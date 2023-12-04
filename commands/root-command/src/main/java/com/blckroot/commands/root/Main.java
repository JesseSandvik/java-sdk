package com.blckroot.commands.root;

import com.blckroot.sdk.command.orchestrator.CommandOrchestrator;

public class Main {
    public static void main(String[] args) {
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(new RootCommand("gherkin"));
        commandOrchestrator.execute(args);
    }
}
