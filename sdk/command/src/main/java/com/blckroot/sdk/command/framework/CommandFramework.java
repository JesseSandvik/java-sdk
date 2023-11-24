package com.blckroot.sdk.command.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;

public class CommandFramework implements CommandFrameworkContract {
    private final CommandFrameworkUtility commandFrameworkUtility;

    public CommandFramework(FrameworkBaseCommand frameworkBaseCommand) {
        this.commandFrameworkUtility = new CommandFrameworkUtility(frameworkBaseCommand);
    }

    @Override
    public Integer execute(String[] arguments) {
        return commandFrameworkUtility.execute(arguments);
    }
}
