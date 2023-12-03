package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.decorator.ExecuteCommandAsPlugin;
import com.blckroot.sdk.command.decorator.SetAttributesFromPropertiesFile;

public class Main {
    public static void main(String[] args) throws Exception {
        Command testCommand = new CallableCommand("test");
        String propertiesFileDirectory = "sdk/command-orchestrator/src/test/resources/";
        Command command = new SetAttributesFromPropertiesFile(new ExecuteCommandAsPlugin(testCommand), propertiesFileDirectory);
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(args);
    }
}