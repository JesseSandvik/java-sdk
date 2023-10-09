package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;

public class Main {
    public static void main(String[] args) throws Exception {
        ExecutableCommand commandA = new ExecutableCommand("commandA", "");
        ExecutableCommand commandB = new ExecutableCommand("commandB", "");
        ExecutableCommand commandC = new ExecutableCommand("commandC", "");
        commandA.addExecutableSubcommand(commandB);
        commandA.addExecutableSubcommand(commandC);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(commandA);
        int exitCode = commandOrchestrator.execute(new String[]{"commandB", "-h"});
        System.exit(exitCode);
    }
}
