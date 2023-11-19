package com.blckroot;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.decorator.framework.SetAttributesFromPropertiesFile;

public class Main {
    public static void main(String[] args) throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(new TestCommand(), "sdk/command/src/test/resources/");
        int exitCode = command.call();

        System.out.println(command.getVersion());
        System.out.println(command.getSynopsis());
        System.out.println(command.getDescription());
        System.exit(exitCode);
    }
}