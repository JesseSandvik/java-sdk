package com.blckroot.sdk.file.system.executor;

import java.io.PrintStream;
import java.util.List;

public class FileSystemExecutor implements FileSystemExecutorContract {
    private final FileSystemExecutorUtility fileSystemExecutorUtility;

    public FileSystemExecutor() {
        this.fileSystemExecutorUtility = new FileSystemExecutorUtility();
    }

    @Override
    public Integer executeCommand(List<String> command, PrintStream printStream) {
        return fileSystemExecutorUtility.executeCommand(command, printStream);
    }
}
