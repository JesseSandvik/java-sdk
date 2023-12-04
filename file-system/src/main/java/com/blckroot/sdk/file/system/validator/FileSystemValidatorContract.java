package com.blckroot.sdk.file.system.validator;

interface FileSystemValidatorContract {
    Boolean fileCanExecute(String file);
    Boolean fileExists(String file);
}
