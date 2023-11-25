package com.blckroot;

import com.blckroot.sdk.command.framework.CommandFramework;
import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.decorator.framework.ExecuteCommandAsPlugin;
import com.blckroot.sdk.command.framework.command.decorator.framework.SetAttributesFromPropertiesFile;

public class Main {
    public static void main(String[] args) throws Exception {
        final String PROPERTIES_FILE_PATH = "sdk/command/src/test/resources/";

        FrameworkBaseCommand command = new TestCommand();

        FrameworkBaseCommand commandWithAttributes = new SetAttributesFromPropertiesFile(command, PROPERTIES_FILE_PATH);
        FrameworkBaseCommand executablePluginCommand = new ExecuteCommandAsPlugin(commandWithAttributes);

        CommandFramework commandFramework = new CommandFramework(executablePluginCommand);
        int exitCode = commandFramework.execute(args);

        System.exit(exitCode);
    }
}