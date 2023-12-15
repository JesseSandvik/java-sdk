package com.blckroot.sdk.command;

import com.blckroot.sdk.file.system.executor.FileSystemExecutor;

import java.util.ArrayList;
import java.util.List;

class ExecutorCommand extends Command {
    @Override
    public Integer call() {
        final String EXECUTABLE_FILE_PATH_PROPERTY_KEY = "executable.file.path";
        String executableFilePath = getProperties().getProperty(EXECUTABLE_FILE_PATH_PROPERTY_KEY);

        List<String> arguments = new ArrayList<>();
        arguments.add(executableFilePath);

        getPositionalParameters().forEach(positionalParameter -> {
            if (positionalParameter.getValue() != null) {
                arguments.add(positionalParameter.getValue().toString());
            }
        });

        getOptions().forEach(option -> {
            if (option.getValue() != null) {
                arguments.add(option.getLongName());
                arguments.add(option.getValue().toString());
            }
        });

        FileSystemExecutor fileSystemExecutor = new FileSystemExecutor();
        return fileSystemExecutor.executeCommand(arguments, System.out);
    }
}
