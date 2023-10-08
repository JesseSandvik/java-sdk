package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;
import com.blckroot.cmd.positionalParameter.PositionalParameter;


public class Main {
    public static void main(String[] args) {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");

        PositionalParameter positionalParameterA = new PositionalParameter("labelA", "Test description labelA.");
        PositionalParameter positionalParameterB = new PositionalParameter("labelB", "Test description labelB.");
        PositionalParameter positionalParameterC = new PositionalParameter("labelC", "Test description labelC.");
        command.addPositionalParameter(positionalParameterA);
        command.addPositionalParameter(positionalParameterB);
        command.addPositionalParameter(positionalParameterC);

        ExecutableCommand executableCommandA = new ExecutableCommand("testA", "");
        ExecutableCommand executableCommandB = new ExecutableCommand("testB", "");
        ExecutableCommand executableCommandC = new ExecutableCommand("testC", "");
        command.addExecutableSubcommand(executableCommandA);
        command.addExecutableSubcommand(executableCommandB);
        command.addExecutableSubcommand(executableCommandC);

        CommandLineUtility commandLineUtility = new CommandLineUtility(command);
        int exitCode = commandLineUtility.execute(args);
        System.exit(exitCode);
    }
}
