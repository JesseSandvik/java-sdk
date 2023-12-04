package com.blckroot.sdk.file.system.service;

import com.blckroot.sdk.file.system.validator.FileSystemValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class FileSystemServiceUtility implements FileSystemServiceContract {
    @Override
    public Properties getPropertiesFromFile(String propertiesFile) {
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        if (!fileSystemValidator.fileExists(propertiesFile)) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(propertiesFile)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
