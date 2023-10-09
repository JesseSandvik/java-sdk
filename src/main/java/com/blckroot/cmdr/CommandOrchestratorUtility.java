package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class CommandOrchestratorUtility implements CommandOrchestratorContract {
    private final ExecutableCommand parentCommand;

    CommandOrchestratorUtility(ExecutableCommand parentCommand) {
        this.parentCommand = parentCommand;
    }

    public Integer execute(String[] arguments) throws Exception {
        CommandLineBuilder commandLineBuilder = new CommandLineBuilder(parentCommand);
        commandLineBuilder.addStandardUsageHelp();

        if (parentCommand.getVersion() != null) {
            commandLineBuilder.addStandardVersionHelp();
        }

        return handleParseResult(commandLineBuilder.build().parseArgs(arguments));
    }

    private Integer handleParseResult(ParseResult parseResult) throws Exception {
        Queue<CommandLine> commandLineQueue = new ArrayDeque<>(parseResult.asCommandLineList());

        while (!commandLineQueue.isEmpty()) {
            CommandLine commandLine = commandLineQueue.remove();

            if (commandLine.isUsageHelpRequested()) {
                return usageHelp(commandLine);
            }

            if (commandLine.isVersionHelpRequested()) {
                return versionHelp(commandLine);
            }

            ParseResult commandLineParseResult = commandLine.getParseResult();

            if (commandLineParseResult.expandedArgs().isEmpty()) {
                return usageHelp(commandLine);
            }
        }
        return parentCommand.call();
    }

    private Integer usageHelp(CommandLine commandLine) {
        commandLine.usage(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnUsageHelp();
    }

    private Integer versionHelp(CommandLine commandLine) {
        commandLine.printVersionHelp(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnVersionHelp();
    }
}
