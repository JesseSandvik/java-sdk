package com.blckroot.sdk.command.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

class CommandFrameworkUtility implements CommandFrameworkContract {
    private final FrameworkBaseCommand frameworkBaseCommand;

    CommandFrameworkUtility(FrameworkBaseCommand frameworkBaseCommand) {
        this.frameworkBaseCommand = frameworkBaseCommand;
    }

    private Integer usageHelp(CommandLine commandLine) {
        commandLine.usage(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnUsageHelp();
    }

    private Integer versionHelp(CommandLine commandLine) {
        commandLine.printVersionHelp(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnVersionHelp();
    }

    private FrameworkBaseCommand getFrameworkBaseCommandForParseResult(ParseResult parseResult) {
        String frameworkBaseCommandName = parseResult.commandSpec().name();
        if (frameworkBaseCommandName.equalsIgnoreCase(frameworkBaseCommand.getName())) {
            return frameworkBaseCommand;
        }

        if (!frameworkBaseCommand.getFrameworkSubcommands().isEmpty()) {
            Queue<FrameworkBaseCommand> frameworkBaseCommandQueue =
                    new ArrayDeque<>(frameworkBaseCommand.getFrameworkSubcommands());
            while (!frameworkBaseCommandQueue.isEmpty()) {
                FrameworkBaseCommand currentFrameworkBaseCommand = frameworkBaseCommandQueue.remove();
                if (frameworkBaseCommandName.equalsIgnoreCase(currentFrameworkBaseCommand.getName())) {
                    return currentFrameworkBaseCommand;
                }
                frameworkBaseCommandQueue.addAll(currentFrameworkBaseCommand.getFrameworkSubcommands());
            }
        }
        return null;
    }

    @Override
    public Integer execute(String[] arguments) {
        CommandLine commandLine = new CommandLineBuilder(frameworkBaseCommand).build();
        ParseResult parseResult = commandLine.parseArgs(arguments);

        try {
            Queue<CommandLine> commandLineQueue = new ArrayDeque<>(parseResult.asCommandLineList());
            while (!commandLineQueue.isEmpty()) {
                CommandLine currentCommandLine = commandLineQueue.remove();

                if (currentCommandLine.isUsageHelpRequested()) {
                    return usageHelp(currentCommandLine);
                }

                if (currentCommandLine.isVersionHelpRequested()) {
                    return versionHelp(currentCommandLine);
                }

                ParseResult currentParseResult = currentCommandLine.getParseResult();
                FrameworkBaseCommand currentFrameworkBaseCommand =
                        getFrameworkBaseCommandForParseResult(currentParseResult);
                if (currentFrameworkBaseCommand == null) {
                    return 0;
                }

                if (currentParseResult.expandedArgs().isEmpty()) {
                    if (currentFrameworkBaseCommand.isExecutesWithoutArguments()) {
                        return currentFrameworkBaseCommand.call();
                    }
                    return usageHelp(currentCommandLine);
                }

                List<PositionalParameter> positionalParameters = currentFrameworkBaseCommand.getPositionalParameters();
                if (!positionalParameters.isEmpty()) {
                    List<PositionalParamSpec> matchedPositionalParameters = currentParseResult.matchedPositionals();
                    if (!matchedPositionalParameters.isEmpty()) {
                        currentFrameworkBaseCommand.getPositionalParameters().forEach(positionalParameter -> {
                            matchedPositionalParameters.forEach(positionalParamSpec -> {
                                if (positionalParameter.getLabel().equalsIgnoreCase(positionalParamSpec.paramLabel())) {
                                    positionalParameter.setValue(positionalParamSpec.getValue());
                                }
                            });
                        });
                    }
                }

                List<Option> options = currentFrameworkBaseCommand.getOptions();
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
                    return currentFrameworkBaseCommand.call();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
