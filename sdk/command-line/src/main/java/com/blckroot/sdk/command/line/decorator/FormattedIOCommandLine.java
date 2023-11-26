package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.line.CommandLine;

import java.util.ArrayDeque;
import java.util.Queue;

public class FormattedIOCommandLine extends IOCommandLine {
    public FormattedIOCommandLine(CommandLine commandLine) {
        super(commandLine);
    }

    String getFormattedHeading(String body) {
        return "%n" + body + ":%n";
    }

    @Override
    public Integer execute(String[] arguments) throws Exception {
        Queue<picocli.CommandLine> commandLineQueue = new ArrayDeque<>();
        commandLineQueue.add(commandLine.getPicocliCommandLine());
        while (!commandLineQueue.isEmpty()) {
            picocli.CommandLine currentPicocliCommandLine = commandLineQueue.remove();
            currentPicocliCommandLine.getCommandSpec().usageMessage()
                    .autoWidth(true)
                    .abbreviateSynopsis(true)
                    .adjustLineBreaksForWideCJKCharacters(true)
                    .parameterListHeading(getFormattedHeading("Positional Parameters"))
                    .optionListHeading(getFormattedHeading("Options"))
                    .commandListHeading(getFormattedHeading("Commands"));

            if (!currentPicocliCommandLine.getSubcommands().isEmpty()) {
                commandLineQueue.addAll(currentPicocliCommandLine.getSubcommands().values());
            }
        }
        return parseCommandLine(arguments);
    }
}
