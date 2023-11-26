package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.line.CommandLine;

abstract class CommandLineDecorator implements CommandLine {
    protected final CommandLine commandOrchestrator;
    private Command rootCommand;
    private picocli.CommandLine commandLine;

    CommandLineDecorator(CommandLine commandOrchestrator) {
        this.commandOrchestrator = commandOrchestrator;
    }

    @Override
    public Command getRootCommand() {
        return rootCommand;
    }

    @Override
    public picocli.CommandLine getCommandLine() {
        return commandLine;
    }

    @Override
    public Integer execute(String[] arguments) {
        return commandOrchestrator.execute(arguments);
    }
}
