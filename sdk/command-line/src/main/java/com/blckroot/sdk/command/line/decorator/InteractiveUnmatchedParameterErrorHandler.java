package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.line.CommandLine;
import picocli.CommandLine.UnmatchedArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InteractiveUnmatchedParameterErrorHandler extends InteractiveCommandLine {
    public InteractiveUnmatchedParameterErrorHandler(CommandLine commandLine) {
        super(commandLine);
    }

    private List<String> getAvailableParameters(picocli.CommandLine picocliCommandLine) {
        List<String> availableParameters = new ArrayList<>(picocliCommandLine.getSubcommands().keySet());
        picocliCommandLine.getCommandSpec().options()
                .forEach(optionSpec -> availableParameters.add(optionSpec.longestName()));
        return availableParameters;
    }

    private Integer getMatchingCharacterCount(char[] characterListA, char[] characterListB) {
        int matchingCharacterCount = 0;

        for (int i = 0; i < Math.min(characterListA.length, characterListB.length); i++) {
            if (characterListA[i] == characterListB[i]) {
                matchingCharacterCount += 1;
            }
        }
        return  matchingCharacterCount;
    }

    private String getMisspelledParameterCandidate(List<String> availableParameters, String unmatchedArgument) {
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

    private String getUnmatchedParameterMessage(String unmatchedParameter, String usageHelpCategory, String commandName) {
        return "'" + unmatchedParameter + "' is not a recognized " + usageHelpCategory.toLowerCase() + " for " + commandName + ".";
    }

    private String getUsageHelpCategoryMessage(String usageHelpCategory, String commandName) {
        return "Please refer to the '" + usageHelpCategory + "s' section for available " + commandName + " " + usageHelpCategory.toLowerCase() + "s.\n";
    }

    private String getMisspelledParameterMessage(String misspelledParameterCandidate) {
        return "Did you mean " + "'" + misspelledParameterCandidate + "'?";
    }

    @Override
    public Integer execute(String[] arguments) throws Exception {
        try {
            addNoInputOptionToCommandLineCommands();
            return commandLine.execute(arguments);
        } catch (UnmatchedArgumentException unmatchedArgumentException) {
            picocli.CommandLine executedCommandLine = unmatchedArgumentException.getCommandLine();
            List<String> originalArguments = executedCommandLine.getParseResult().originalArgs();
            List<String> availableParameters = getAvailableParameters(executedCommandLine);
            String unmatchedArgument = unmatchedArgumentException.getUnmatched().get(0);

            if (unmatchedArgument.equalsIgnoreCase("help")) {
                executedCommandLine.usage(executedCommandLine.getOut());
                return executedCommandLine.getCommandSpec().exitCodeOnUsageHelp();
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
                return executedCommandLine.getCommandSpec().exitCodeOnInvalidInput();
            }

            String misspelledParameterMessage = getMisspelledParameterMessage(misspelledParameterCandidate);
            boolean nonInteractiveOptionProvided = originalArguments.contains(NON_INTERACTIVE_OPTION_LONG_NAME);

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
                    return commandLine.execute(updatedArguments);
                }
            }
            executedCommandLine.getErr().println(unmatchedParameterMessage);
            executedCommandLine.getErr().println(usageHelpCategoryMessage);
            executedCommandLine.usage(executedCommandLine.getErr());
            executedCommandLine.getErr().println(misspelledParameterMessage);
            return executedCommandLine.getCommandSpec().exitCodeOnInvalidInput();
        }
    }
}
