package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;
import com.blckroot.cmd.option.Option;
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

            ExecutableCommand executableCommand = getExecutableCommandForParseResult(commandLineParseResult);
            if (executableCommand != null) {
                List<PositionalParameter> positionalParameters = executableCommand.getPositionalParameters();
                List<PositionalParamSpec> matchedPositionalParameters = commandLineParseResult.matchedPositionals();
                if (!positionalParameters.isEmpty() && !matchedPositionalParameters.isEmpty()) {
                    setPositionalParameterValuesForMatchedPositionalParameters(
                            positionalParameters, matchedPositionalParameters);
                }

                List<Option> options = executableCommand.getOptions();
                List<OptionSpec> matchedOptions = commandLineParseResult.matchedOptions();
                if (!options.isEmpty() && !matchedOptions.isEmpty()) {
                    setOptionValuesForMatchedOptions(options, matchedOptions);
                }

                if (commandLineQueue.isEmpty()) {
                    return executableCommand.call();
                }
            }
        }
        return 0;
    }

    private void setPositionalParameterValuesForMatchedPositionalParameters(
            List<PositionalParameter> positionalParameters,
            List<PositionalParamSpec> matchedPositionalParameters
            ) {
        positionalParameters.forEach(positionalParameter -> {
            matchedPositionalParameters.forEach(matchedPositionalParameter -> {
                if (positionalParameter.getLabel().equalsIgnoreCase(matchedPositionalParameter.paramLabel())) {
                    positionalParameter.setValue(matchedPositionalParameter.getValue());
                }
            });
        });
    }

    private void setOptionValuesForMatchedOptions(List<Option> options, List<OptionSpec> matchedOptions) {
        options.forEach(option -> {
            matchedOptions.forEach(matchedOption -> {
                if (option.getLongestName().equalsIgnoreCase(matchedOption.longestName())) {
                    option.setValue(matchedOption.getValue());
                }
            });
        });
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
