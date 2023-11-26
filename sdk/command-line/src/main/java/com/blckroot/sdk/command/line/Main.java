package com.blckroot.sdk.command.line;


import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.decorator.ExecuteCommandAsPlugin;
import com.blckroot.sdk.command.decorator.SetAttributesFromPropertiesFile;
import com.blckroot.sdk.command.line.decorator.ParseCommandLine;

public class Main {
    public static void main(String[] args) {
        String propertiesFileDirectory = "sdk/command-line/src/test/resources/";
        Command command = new SetAttributesFromPropertiesFile(new ExecuteCommandAsPlugin(new CallableCommand("test")), propertiesFileDirectory);
        CommandLine parseCommandLine = new ParseCommandLine(new CommandLineCore(command));
        int exitCode = parseCommandLine.execute(args);
        System.exit(exitCode);
    }
}
