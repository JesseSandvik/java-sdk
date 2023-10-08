package com.blckroot.cmd.command;

import java.util.List;

interface ExecutableCommandContract extends CommandContract {
    String getExecutableFilePath();
    List<ExecutableCommand> getExecutableSubcommands();
    void addExecutableSubcommand(ExecutableCommand executableSubcommand);
}
