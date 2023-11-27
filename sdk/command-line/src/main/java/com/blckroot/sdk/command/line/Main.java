package com.blckroot.sdk.command.line;


import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.decorator.ExecuteCommandAsPlugin;
import com.blckroot.sdk.command.decorator.SetAttributesFromPropertiesFile;
import com.blckroot.sdk.command.line.decorator.FormattedIOCommandLine;
import com.blckroot.sdk.command.line.decorator.IOCommandLine;
import com.blckroot.sdk.command.line.decorator.InteractiveUnmatchedParameterErrorHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        String propertiesFileDirectory = "sdk/command-line/src/test/resources/";
//        Command command = new SetAttributesFromPropertiesFile(new ExecuteCommandAsPlugin(new CallableCommand("test")), propertiesFileDirectory);
        Command command = new CallableCommand("testing123");
//        CommandLine commandLine = new InteractiveUnmatchedParameterErrorHandler(new FormattedIOCommandLine(new CommandLineCore(command)));
        CommandLine commandLine = new IOCommandLine(new CommandLineCore(command));
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
