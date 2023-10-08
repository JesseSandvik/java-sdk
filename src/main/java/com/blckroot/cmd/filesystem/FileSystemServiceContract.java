package com.blckroot.cmd.filesystem;

interface FileSystemServiceContract {
    Boolean fileExists(String filePath);
    Boolean fileCanExecute(String filePath);
}
