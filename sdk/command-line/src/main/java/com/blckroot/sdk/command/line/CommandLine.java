package com.blckroot.sdk.command.line;

import com.blckroot.sdk.command.Command;

public interface CommandLine {
    Command getRootCommand();
    picocli.CommandLine getPicocliCommandLine();
    Integer execute(String[] arguments) throws Exception;
}
