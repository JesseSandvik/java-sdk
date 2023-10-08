package com.blckroot.cmd.filesystem;

import java.io.File;

class FileSystemServiceUtility implements FileSystemServiceContract {
    @Override
    public Boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    @Override
    public Boolean fileCanExecute(String filePath) {
        return new File(filePath).canExecute();
    }
}
