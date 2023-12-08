package com.blckroot.sdk.command.execution;

import com.blckroot.sdk.command.Command;
import picocli.CommandLine;

class PicocliCommandLineGenerator {
    public static CommandLine generate(Command command) {
        return buildCommandLine(command);
    }

    private static CommandLine buildCommandLine(Command command) {
        PicocliCommandLineBuilder commandLineBuilder = new PicocliCommandLineBuilder(command)
                .addCustomUsageHelpFormat(true)
                .addDisableInteractiveInputOption(true)
                .addStandardUsageHelpOption(true);

        if (command.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelpOption(true);
        }

        if (!command.getPositionalParameters().isEmpty()) {
            command.getPositionalParameters().forEach(commandLineBuilder::addPositionalParameter);
        }

        if (!command.getOptions().isEmpty()) {
            command.getOptions().forEach(commandLineBuilder::addOption);
        }

        if (!command.getSubcommands().isEmpty()) {
            command.getSubcommands().forEach(subcommand -> {
                commandLineBuilder.addSubcommand(buildCommandLine(subcommand));
            });
        }
        return commandLineBuilder.build();
    }
}