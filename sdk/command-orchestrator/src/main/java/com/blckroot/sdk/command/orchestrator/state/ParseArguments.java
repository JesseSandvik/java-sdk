package com.blckroot.sdk.command.orchestrator.state;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import com.blckroot.sdk.command.orchestrator.model.CommandExecution;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.util.*;

class ParseArguments {
    private static Integer usageHelp(CommandLine commandLine) {
        commandLine.usage(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnUsageHelp();
    }

    private static Integer versionHelp(CommandLine commandLine) {
        commandLine.printVersionHelp(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnVersionHelp();
    }

    private static Command getCommandForParseResult(CommandExecution commandExecution, ParseResult parseResult) {
        String commandName = parseResult.commandSpec().name();
        Command command = commandExecution.getCommand();
        if (commandName.equalsIgnoreCase(command.getName())) {
            return command;
        }

        if (!command.getSubcommands().isEmpty()) {
            Queue<Command> commandQueue = new ArrayDeque<>(command.getSubcommands());
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

    private static List<String> getAvailableParameters(picocli.CommandLine picocliCommandLine) {
        List<String> availableParameters = new ArrayList<>(picocliCommandLine.getSubcommands().keySet());
        picocliCommandLine.getCommandSpec().options()
                .forEach(optionSpec -> availableParameters.add(optionSpec.longestName()));
        return availableParameters;
    }

    private static Integer getMatchingCharacterCount(char[] characterListA, char[] characterListB) {
        int matchingCharacterCount = 0;

        for (int i = 0; i < Math.min(characterListA.length, characterListB.length); i++) {
            if (characterListA[i] == characterListB[i]) {
                matchingCharacterCount += 1;
            }
        }
        return  matchingCharacterCount;
    }

    private static String getMisspelledParameterCandidate(List<String> availableParameters, String unmatchedArgument) {
        String misspelledParameterCandidate = null;
        for (String availableParameter : availableParameters) {
            String unmatchedArgumentAlphanumeric = unmatchedArgument.replaceAll("[^a-zA-Z0-9]", "");
            char[] unmatchedArgumentAlphanumericCharacters = unmatchedArgumentAlphanumeric.toLowerCase().toCharArray();
            char[] availableParameterAlphanumericCharacters =
                    availableParameter.replaceAll("[^a-zA-Z0-9]", "").toLowerCase().toCharArray();

            int matchingAlphanumericCharacterCount = getMatchingCharacterCount(
                    unmatchedArgumentAlphanumericCharacters, availableParameterAlphanumericCharacters);

            if (matchingAlphanumericCharacterCount >= unmatchedArgumentAlphanumeric.length() / 2) {
                misspelledParameterCandidate = availableParameter;
            }
        }
        return misspelledParameterCandidate;
    }

    private static String getUnmatchedParameterMessage(String unmatchedParameter, String usageHelpCategory, String commandName) {
        return "'" + unmatchedParameter + "' is not a recognized " + usageHelpCategory.toLowerCase() + " for " + commandName + ".";
    }

    private static String getUsageHelpCategoryMessage(String usageHelpCategory, String commandName) {
        return "Please refer to the '" + usageHelpCategory + "s' section for available " + commandName + " " + usageHelpCategory.toLowerCase() + "s.\n";
    }

    private static String getMisspelledParameterMessage(String misspelledParameterCandidate) {
        return "Did you mean " + "'" + misspelledParameterCandidate + "'?";
    }

    public static void run(CommandExecution commandExecution) {
        try {
            CommandLine picocliCommandLine = commandExecution.getPicocliCommandLine();
            ParseResult parseResult = picocliCommandLine.parseArgs(commandExecution.getArguments());
            Queue<CommandLine> commandLineQueue = new ArrayDeque<>(parseResult.asCommandLineList());
            while (!commandLineQueue.isEmpty()) {
                CommandLine currentCommandLine = commandLineQueue.remove();

                if (currentCommandLine.isUsageHelpRequested()) {
                    commandExecution.setExitCode(usageHelp(currentCommandLine));
                }

                if (currentCommandLine.isVersionHelpRequested()) {
                    commandExecution.setExitCode(versionHelp(currentCommandLine));
                }

                ParseResult currentParseResult = currentCommandLine.getParseResult();
                Command currentCommand = getCommandForParseResult(commandExecution, currentParseResult);
                if (currentCommand == null) {
                    return;
                }

                if (currentParseResult.expandedArgs().isEmpty()) {
                    if (currentCommand.isExecutesWithoutArguments()) {
                        commandExecution.setCommand(currentCommand);
                        return;
                    }
                    commandExecution.setExitCode(usageHelp(currentCommandLine));
                    return;
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
            }
        } catch (CommandLine.UnmatchedArgumentException unmatchedArgumentException) {
            picocli.CommandLine executedCommandLine = unmatchedArgumentException.getCommandLine();
            List<String> originalArguments = executedCommandLine.getParseResult().originalArgs();
            List<String> availableParameters = getAvailableParameters(executedCommandLine);
            String unmatchedArgument = unmatchedArgumentException.getUnmatched().get(0);

            if (unmatchedArgument.equalsIgnoreCase("help")) {
                executedCommandLine.usage(executedCommandLine.getOut());
                commandExecution.setExitCode(executedCommandLine.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }

            String unmatchedArgumentUsageHelpCategory = unmatchedArgument.startsWith("-") ? "Option" : "Command";
            String misspelledParameterCandidate = getMisspelledParameterCandidate(availableParameters, unmatchedArgument);

            String unmatchedParameterMessage = getUnmatchedParameterMessage(
                    unmatchedArgument, unmatchedArgumentUsageHelpCategory, executedCommandLine.getCommandName());
            String usageHelpCategoryMessage = getUsageHelpCategoryMessage(
                    unmatchedArgumentUsageHelpCategory, executedCommandLine.getCommandName());

            if (misspelledParameterCandidate == null) {
                executedCommandLine.getErr().println(unmatchedParameterMessage);
                executedCommandLine.getErr().println(usageHelpCategoryMessage);
                executedCommandLine.usage(executedCommandLine.getErr());
                commandExecution.setExitCode(executedCommandLine.getCommandSpec().exitCodeOnInvalidInput());
                return;
            }

            String misspelledParameterMessage = getMisspelledParameterMessage(misspelledParameterCandidate);
            boolean nonInteractiveOptionProvided = originalArguments.contains("--no-input");

            if (!nonInteractiveOptionProvided) {
                executedCommandLine.getErr().println(unmatchedParameterMessage);
                executedCommandLine.getErr().println(misspelledParameterMessage + "[y/n]");
                Scanner scanner = new Scanner(System.in);

                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")) {
                    String[] updatedArguments = new String[originalArguments.size()];

                    for (int i = 0; i < originalArguments.size(); i++) {
                        if (originalArguments.get(i).equalsIgnoreCase(unmatchedArgument)) {
                            updatedArguments[i] = misspelledParameterCandidate;
                        } else {
                            updatedArguments[i] = originalArguments.get(i);
                        }
                    }
                    commandExecution.setArguments(updatedArguments);
                    run(commandExecution);
                    return;
                }
            }
            executedCommandLine.getErr().println(unmatchedParameterMessage);
            executedCommandLine.getErr().println(usageHelpCategoryMessage);
            executedCommandLine.usage(executedCommandLine.getErr());
            executedCommandLine.getErr().println(misspelledParameterMessage);
            commandExecution.setExitCode(executedCommandLine.getCommandSpec().exitCodeOnInvalidInput());
        }
    }
}
