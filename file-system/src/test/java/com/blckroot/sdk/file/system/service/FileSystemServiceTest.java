package com.blckroot.sdk.file.system.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemServiceTest {

    @Test
    void FILE_SYSTEM_SERVICE__get_properties_from_file__file_path_null() {
        FileSystemService fileSystemService = new FileSystemService();
        assertNull(fileSystemService.getPropertiesFromFile(null));
    }

    @Test
    void FILE_SYSTEM_SERVICE__get_properties_from_file__file_path_empty() {
        FileSystemService fileSystemService = new FileSystemService();
        assertNull(fileSystemService.getPropertiesFromFile(""));
    }

    @Test
    void FILE_SYSTEM_SERVICE__get_properties_from_file__file_path_blank() {
        FileSystemService fileSystemService = new FileSystemService();
        assertNull(fileSystemService.getPropertiesFromFile("   "));
    }

    @Test
    void FILE_SYSTEM_SERVICE__get_properties_from_file__file_path_does_not_exist() {
        FileSystemService fileSystemService = new FileSystemService();
        assertNull(fileSystemService.getPropertiesFromFile("src/test/resources/bad.properties"));
    }

    @Test
    void FILE_SYSTEM_SERVICE__get_properties_from_file__file_path_exists() {
        String expected = "valueA";
        FileSystemService fileSystemService = new FileSystemService();
        String actual = fileSystemService
                .getPropertiesFromFile("src/test/resources/test.properties")
                .getProperty("keyA");

        assertEquals(expected, actual);
    }
}
