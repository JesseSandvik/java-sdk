package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;
import com.blckroot.cmd.positionalParameter.PositionalParameter;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.*;

import java.util.ArrayDeque;
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

//            Get the command
            ExecutableCommand executableCommand = getExecutableCommandForParseResult(commandLineParseResult);

//            Set value of matched positional to command positional parameter
            if (executableCommand != null) {
                List<PositionalParameter> positionalParameters = executableCommand.getPositionalParameters();
                List<PositionalParamSpec> matchedPositionalParameters = commandLineParseResult.matchedPositionals();
                if (!positionalParameters.isEmpty() && !matchedPositionalParameters.isEmpty()) {
                    positionalParameters.forEach(positionalParameter -> {
                        matchedPositionalParameters.forEach(matchedPositionalParameter -> {
                            if (positionalParameter.getLabel().equalsIgnoreCase(matchedPositionalParameter.paramLabel())) {
                                positionalParameter.setValue(matchedPositionalParameter.getValue());
                            }
                        });
                    });
                }
            }
        }
        return 0;
    }

    private ExecutableCommand getExecutableCommandForParseResult(ParseResult parseResult) {
        String parseResultCommandName = parseResult.commandSpec().name();
        if (parseResultCommandName.equalsIgnoreCase(parentCommand.getName())) {
            return parentCommand;
        }

        if (!parentCommand.getExecutableSubcommands().isEmpty()) {
            Queue<ExecutableCommand> executableCommandQueue =
                    new ArrayDeque<>(parentCommand.getExecutableSubcommands());
            while (!executableCommandQueue.isEmpty()) {
                ExecutableCommand executableCommand = executableCommandQueue.remove();

                if (parseResultCommandName.equalsIgnoreCase(executableCommand.getName())) {
                    return executableCommand;
                }

                executableCommandQueue.addAll(executableCommand.getExecutableSubcommands());
            }
        }
        return null;
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
