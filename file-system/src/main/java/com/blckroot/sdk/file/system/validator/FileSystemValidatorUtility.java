package com.blckroot.sdk.file.system.validator;

import java.io.File;

class FileSystemValidatorUtility implements FileSystemValidatorContract {
    @Override
    public Boolean fileCanExecute(String file) {
        if (file == null) {
            return false;
        }
        File potentialExecutableFile = new File(file);
        return potentialExecutableFile.canExecute() && potentialExecutableFile.isFile();
    }

    @Override
    public Boolean fileExists(String file) {
        if (file == null) {
            return false;
        }
        return new File(file).isFile();
    }
}
