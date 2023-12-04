package com.blckroot.sdk.command.orchestrator.state.picocli;

import com.blckroot.sdk.command.orchestrator.model.CommandExecution;
import com.blckroot.sdk.command.orchestrator.state.CommandExecutionStateContract;

public class ParseCommandLineArguments implements CommandExecutionStateContract {
    @Override
    public void run(CommandExecution commandExecution) {
        BuildPicocliCommandLine.build(commandExecution);
        ParsePicocliCommandLineArguments.parse(commandExecution);
    }
}
