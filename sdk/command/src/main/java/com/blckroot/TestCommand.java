package com.blckroot;

import com.blckroot.sdk.command.framework.command.FrameworkCommand;

public class TestCommand extends FrameworkCommand {
    public TestCommand() {
        super("test");
    }

    @Override
    public Integer call() {
        System.out.println("TEST COMMAND EXECUTED SUCCESSFULLY");
        return 0;
    }
}
