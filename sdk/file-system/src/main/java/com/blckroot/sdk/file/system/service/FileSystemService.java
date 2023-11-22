package com.blckroot.sdk.file.system.service;

import java.util.Properties;

import static java.lang.System.Logger.Level.DEBUG;
import static java.lang.System.Logger.Level.TRACE;

public class FileSystemService implements FileSystemServiceContract {
    private static final System.Logger LOGGER = System.getLogger(FileSystemService.class.getName());
    private final FileSystemServiceUtility fileSystemServiceUtility;

    public FileSystemService() {
        this.fileSystemServiceUtility = new FileSystemServiceUtility();
        LOGGER.log(TRACE, "instantiated: " + fileSystemServiceUtility.getClass().getName());
    }

    @Override
    public Properties getPropertiesFromFile(String propertiesFile) {
        LOGGER.log(DEBUG, "getting properties from file: " + propertiesFile);
        return fileSystemServiceUtility.getPropertiesFromFile(propertiesFile);
    }
}
