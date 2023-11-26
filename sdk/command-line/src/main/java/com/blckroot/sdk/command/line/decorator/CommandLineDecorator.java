package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.line.CommandLine;

abstract class CommandLineDecorator implements CommandLine {
    protected final CommandLine commandLine;

    CommandLineDecorator(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public Command getRootCommand() {
        return commandLine.getRootCommand();
    }

    @Override
    public picocli.CommandLine getPicocliCommandLine() {
        return commandLine.getPicocliCommandLine();
    }

    @Override
    public Integer execute(String[] arguments) throws Exception {
        return commandLine.execute(arguments);
    }
}
