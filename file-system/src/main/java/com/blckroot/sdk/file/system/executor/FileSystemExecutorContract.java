package com.blckroot.sdk.file.system.executor;

import java.io.PrintStream;
import java.util.List;

interface FileSystemExecutorContract {
    Integer executeCommand(List<String> command, PrintStream printStream);
}
