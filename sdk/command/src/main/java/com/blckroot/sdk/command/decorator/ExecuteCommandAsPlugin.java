package com.blckroot.sdk.command.decorator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.file.system.executor.FileSystemExecutor;
import com.blckroot.sdk.file.system.validator.FileSystemValidator;

import java.util.Properties;

public class ExecuteCommandAsPlugin extends CommandDecorator {

    public ExecuteCommandAsPlugin(Command command) {
        super(command);
    }

    private Boolean propertyIsValid(Command command, String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        return command.getProperties().getProperty(key) != null;
    }

    private Boolean propertiesAreValid(Properties properties) {
        return properties != null && !properties.isEmpty();
    }

    @Override
    public Integer call() throws Exception {
        if (!propertiesAreValid(command.getProperties())) {
//            TODO: improve handling behavior
            return 1;
        }

        final String EXECUTABLE_FILE_PATH_PROPERTY_KEY = "executable.file.path";
        if (!propertyIsValid(command, EXECUTABLE_FILE_PATH_PROPERTY_KEY)) {
            System.out.println("executable file path property does not exist");
//            TODO: handle missing executable file path property from properties.
            return 1;
        }

        String executableFilePath = command.getProperties().getProperty(EXECUTABLE_FILE_PATH_PROPERTY_KEY);
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        if (!fileSystemValidator.fileExists(executableFilePath) || !fileSystemValidator.fileCanExecute(executableFilePath)) {
            System.out.println("executable file does not exist: " + executableFilePath);
//            TODO: handle missing file/non-executable file
            return 1;
        }

        command.addArgument(executableFilePath);

        command.getPositionalParameters().forEach(positionalParameter -> {
            if (positionalParameter.getValue() != null) {
                command.addArgument(positionalParameter.getValue().toString());
            }
        });

        command.getOptions().forEach(option -> {
            if (option.getValue() != null) {
                command.addArgument(option.getLongName());
                command.addArgument(option.getValue().toString());
            }
        });

        FileSystemExecutor fileSystemExecutor = new FileSystemExecutor();
        fileSystemExecutor.executeCommand(command.getArguments(), System.out);
        return super.call();
    }
}
