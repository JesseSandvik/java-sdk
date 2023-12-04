package com.blckroot.sdk.file.system.validator;

import static java.lang.System.Logger.Level.*;

public class FileSystemValidator implements FileSystemValidatorContract {
    private static final System.Logger LOGGER = System.getLogger(FileSystemValidator.class.getName());
    private final FileSystemValidatorUtility fileSystemValidatorUtility;

    public FileSystemValidator() {
        this.fileSystemValidatorUtility = new FileSystemValidatorUtility();
        LOGGER.log(TRACE, "instantiated: " + fileSystemValidatorUtility.getClass().getName());
    }

    @Override
    public Boolean fileCanExecute(String file) {
        LOGGER.log(DEBUG, "validating file can execute: " + file);
        return fileSystemValidatorUtility.fileCanExecute(file);
    }

    @Override
    public Boolean fileExists(String file) {
        LOGGER.log(DEBUG, "validating file exists: " + file);
        return fileSystemValidatorUtility.fileExists(file);
    }
}
