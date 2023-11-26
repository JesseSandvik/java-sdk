package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.line.CommandLine;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class ParseCommandLine extends CommandLineDecorator {
    public ParseCommandLine(CommandLine commandOrchestrator) {
        super(commandOrchestrator);
    }

    private Integer usageHelp(picocli.CommandLine commandLine) {
        commandLine.usage(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnUsageHelp();
    }

    private Integer versionHelp(picocli.CommandLine commandLine) {
        commandLine.printVersionHelp(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnVersionHelp();
    }

    private Command getCommandForParseResult(ParseResult parseResult) {
        String commandName = parseResult.commandSpec().name();
        Command rootCommand = super.commandOrchestrator.getRootCommand();
        if (commandName.equalsIgnoreCase(rootCommand.getName())) {
            return rootCommand;
        }

        if (!rootCommand.getSubcommands().isEmpty()) {
            Queue<Command> commandQueue = new ArrayDeque<>(rootCommand.getSubcommands());
            while (!commandQueue.isEmpty()) {
                Command currentCommand = commandQueue.remove();
                if (commandName.equalsIgnoreCase(currentCommand.getName())) {
                    return currentCommand;
                }
                commandQueue.addAll(currentCommand.getSubcommands());
            }
        }
        return null;
    }

    private Integer parseCommandLine(String[] arguments) {
        picocli.CommandLine commandLine = super.commandOrchestrator.getCommandLine();
        try {
            ParseResult parseResult = commandLine.parseArgs(arguments);
            Queue<picocli.CommandLine> commandLineQueue = new ArrayDeque<>(parseResult.asCommandLineList());
            while (!commandLineQueue.isEmpty()) {
                picocli.CommandLine currentCommandLine = commandLineQueue.remove();

                if (currentCommandLine.isUsageHelpRequested()) {
                    return usageHelp(currentCommandLine);
                }

                if (currentCommandLine.isVersionHelpRequested()) {
                    return versionHelp(currentCommandLine);
                }

                ParseResult currentParseResult = currentCommandLine.getParseResult();
                Command currentCommand = getCommandForParseResult(currentParseResult);
                if (currentCommand == null) {
                    return 0;
                }

                if (currentParseResult.expandedArgs().isEmpty()) {
                    if (currentCommand.isExecutesWithoutArguments()) {
                        return currentCommand.call();
                    }
                    return usageHelp(currentCommandLine);
                }

                List<PositionalParameter> positionalParameters = currentCommand.getPositionalParameters();
                if (!positionalParameters.isEmpty()) {
                    List<PositionalParamSpec> matchedPositionalParameters = currentParseResult.matchedPositionals();
                    if (!matchedPositionalParameters.isEmpty()) {
                        currentCommand.getPositionalParameters().forEach(positionalParameter -> {
                            matchedPositionalParameters.forEach(positionalParamSpec -> {
                                if (positionalParameter.getLabel().equalsIgnoreCase(positionalParamSpec.paramLabel())) {
                                    positionalParameter.setValue(positionalParamSpec.getValue());
                                }
                            });
                        });
                    }
                }

                List<Option> options = currentCommand.getOptions();
                if (!options.isEmpty()) {
                    List<OptionSpec> matchedOptions = currentParseResult.matchedOptions();
                    if (!matchedOptions.isEmpty()) {
                        options.forEach(option -> {
                            matchedOptions.forEach(optionSpec -> {
                                if (option.getLongName().equalsIgnoreCase(optionSpec.longestName())) {
                                    option.setValue(optionSpec.getValue());
                                }
                            });
                        });
                    }
                }

                if (commandLineQueue.isEmpty()) {
                    return currentCommand.call();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public Integer execute(String[] arguments) {
        int parseResultExitCode = parseCommandLine(arguments);
        if (parseResultExitCode == 0) {
            return super.execute(arguments);
        }
        return parseResultExitCode;
    }
}
