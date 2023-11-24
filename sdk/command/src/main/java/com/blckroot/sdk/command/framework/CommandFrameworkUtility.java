package com.blckroot.sdk.command.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import picocli.CommandLine;

class CommandFrameworkUtility implements CommandFrameworkContract {
    private final FrameworkBaseCommand frameworkBaseCommand;

    CommandFrameworkUtility(FrameworkBaseCommand frameworkBaseCommand) {
        this.frameworkBaseCommand = frameworkBaseCommand;
    }

    @Override
    public Integer execute(String[] arguments) {
        CommandLine commandLine = new CommandLineBuilder(frameworkBaseCommand).build();
        return commandLine.execute(arguments);
    }
}
