package com.blckroot.sdk.file.system.service;

import java.util.Properties;

interface FileSystemServiceContract {
    Properties getPropertiesFromFile(String propertiesFile);
}
