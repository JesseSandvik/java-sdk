package com.blckroot.cmd.filesystem;

public class FileSystemService implements FileSystemServiceContract {
    private final FileSystemServiceUtility fileSystemServiceUtility = new FileSystemServiceUtility();
    @Override
    public Boolean fileExists(String filePath) {
        return this.fileSystemServiceUtility.fileExists(filePath);
    }

    @Override
    public Boolean fileCanExecute(String filePath) {
        return this.fileSystemServiceUtility.fileCanExecute(filePath);
    }
}
