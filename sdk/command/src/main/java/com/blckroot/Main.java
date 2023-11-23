package com.blckroot;

import com.blckroot.sdk.command.framework.CommandFramework;
import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.decorator.framework.SetAttributesFromPropertiesFile;

public class Main {
    public static void main(String[] args) throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(new TestCommand(), "sdk/command/src/test/resources/");

        CommandFramework commandFramework = new CommandFramework(command);
        int exitCode = commandFramework.execute(args);

        System.exit(exitCode);
    }
}