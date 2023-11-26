package com.blckroot.sdk.command.orchestrator;

import com.blckroot.sdk.command.Command;
import picocli.CommandLine;

class CommandOrchestratorUtility implements CommandOrchestratorContract {
    private final Command command;
    private CommandLine commandLine;

    CommandOrchestratorUtility(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }

    CommandLine getCommandLine() {
        return commandLine;
    }

    void setCommandLine(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    @Override
    public Integer execute(String[] arguments) {
        return 0;
    }
}
