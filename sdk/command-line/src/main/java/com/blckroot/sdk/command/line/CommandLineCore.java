package com.blckroot.sdk.command.line;

import com.blckroot.sdk.command.Command;

public class CommandLineCore implements CommandLine {
    private final Command rootCommand;
    private final picocli.CommandLine commandLine;

    public CommandLineCore(Command rootCommand) {
        this.rootCommand = rootCommand;
        this.commandLine = buildCommandLine(rootCommand);
    }

    @Override
    public Command getRootCommand() {
        return rootCommand;
    }

    @Override
    public picocli.CommandLine getCommandLine() {
        return commandLine;
    }

    private picocli.CommandLine buildCommandLine(Command command) {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(command).addStandardUsageHelpOption();

        if (command.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelpOption();
        }

        if (!command.getPositionalParameters().isEmpty()) {
            command.getPositionalParameters()
                    .forEach(commandLineBuilder::addPositionalParameter);
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

    @Override
    public Integer execute(String[] arguments) {
        return 0;
    }
}
