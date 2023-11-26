package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.line.CommandLine;
import picocli.CommandLine.Model.OptionSpec;

import java.util.ArrayDeque;
import java.util.Queue;

public class InteractiveCommandLine extends CommandLineDecorator {
    final String NON_INTERACTIVE_OPTION_LONG_NAME = "--no-input";
    final String NON_INTERACTIVE_OPTION_SYNOPSIS = "Disable interactive prompts";
    public InteractiveCommandLine(CommandLine commandLine) {
        super(commandLine);
    }

    void addNoInputOptionToCommandLineCommands() {
        Queue<picocli.CommandLine> commandLineQueue = new ArrayDeque<>();
        commandLineQueue.add(commandLine.getPicocliCommandLine());
        while (!commandLineQueue.isEmpty()) {
            picocli.CommandLine currentPicocliCommandLine = commandLineQueue.remove();
            OptionSpec noInputOptionSpec =
                    currentPicocliCommandLine.getCommandSpec().findOption(NON_INTERACTIVE_OPTION_LONG_NAME);
            if (noInputOptionSpec == null) {
                currentPicocliCommandLine.getCommandSpec().addOption(OptionSpec
                        .builder(NON_INTERACTIVE_OPTION_LONG_NAME)
                        .description(NON_INTERACTIVE_OPTION_SYNOPSIS)
                        .type(Boolean.class)
                        .build());
            }

            if (!currentPicocliCommandLine.getSubcommands().isEmpty()) {
                commandLineQueue.addAll(currentPicocliCommandLine.getSubcommands().values());
            }
        }
    }

    @Override
    public Integer execute(String[] arguments) throws Exception {
        addNoInputOptionToCommandLineCommands();
        return commandLine.execute(arguments);
    }
}
