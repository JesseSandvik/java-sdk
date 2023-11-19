package com.blckroot;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.decorator.framework.SetAttributesFromPropertiesFile;

public class Main {
    public static void main(String[] args) throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(new TestCommand(), "sdk/command/src/test/resources/");
        int exitCode = command.call();

        System.out.println(command.getProperties().getProperty("1.positional.parameter.label"));
        System.out.println(command.getPositionalParameters().get(0).getLabel());
        System.exit(exitCode);
    }
}