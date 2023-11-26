package com.blckroot.sdk.command.line;

import com.blckroot.sdk.command.Command;

public interface CommandLine {
    Command getRootCommand();
    picocli.CommandLine getCommandLine();
    Integer execute(String[] arguments);
}
