package com.blckroot.sdk.command.orchestrator.state;

import com.blckroot.sdk.command.orchestrator.model.CommandExecution;

public interface CommandExecutionStateContract {
    void run(CommandExecution commandExecution);
}
