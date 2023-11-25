package com.blckroot.sdk.command.framework.command.decorator.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.decorator.FrameworkCommandDecorator;
import com.blckroot.sdk.file.system.executor.FileSystemExecutor;
import com.blckroot.sdk.file.system.validator.FileSystemValidator;

import java.util.Properties;

public class ExecuteCommandAsPlugin extends FrameworkCommandDecorator {

    public ExecuteCommandAsPlugin(FrameworkBaseCommand frameworkCommand) {
        super(frameworkCommand);
    }

    private Boolean propertyIsValid(FrameworkBaseCommand frameworkBaseCommand, String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        return frameworkBaseCommand.getProperties().getProperty(key) != null;
    }

    private Boolean propertiesAreValid(Properties properties) {
        return properties != null && !properties.isEmpty();
    }

    @Override
    public Integer call() throws Exception {
        if (!propertiesAreValid(frameworkCommand.getProperties())) {
//            TODO: improve handling behavior
            return 1;
        }

        final String EXECUTABLE_FILE_PATH_PROPERTY_KEY = "executable.file.path";
        if (!propertyIsValid(frameworkCommand, EXECUTABLE_FILE_PATH_PROPERTY_KEY)) {
            System.out.println("executable file path property does not exist");
//            TODO: handle missing executable file path property from properties.
            return 1;
        }

        String executableFilePath = frameworkCommand.getProperties().getProperty(EXECUTABLE_FILE_PATH_PROPERTY_KEY);
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        if (!fileSystemValidator.fileExists(executableFilePath) || !fileSystemValidator.fileCanExecute(executableFilePath)) {
            System.out.println("executable file does not exist: " + executableFilePath);
//            TODO: handle missing file/non-executable file
            return 1;
        }

        frameworkCommand.addArgument(executableFilePath);

        frameworkCommand.getPositionalParameters().forEach(positionalParameter -> {
            if (positionalParameter.getValue() != null) {
                frameworkCommand.addArgument(positionalParameter.getValue().toString());
            }
        });

        frameworkCommand.getOptions().forEach(option -> {
            if (option.getValue() != null) {
                frameworkCommand.addArgument(option.getLongName());
                frameworkCommand.addArgument(option.getValue().toString());
            }
        });

        FileSystemExecutor fileSystemExecutor = new FileSystemExecutor();
        fileSystemExecutor.executeCommand(frameworkCommand.getArguments(), System.out);
        return super.call();
    }
}
