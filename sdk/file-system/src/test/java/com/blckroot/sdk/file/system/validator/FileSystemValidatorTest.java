package com.blckroot.sdk.file.system.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemValidatorTest {

    @Test
    void FILE_SYSTEM_VALIDATOR__file_can_execute__null() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileCanExecute(null);

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_can_execute__empty() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileCanExecute("");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_can_execute__blank() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileCanExecute("   ");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_can_execute__invalid() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileCanExecute("src/test/resources/dir/");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_can_execute__valid() {
        boolean expected = true;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileCanExecute("src/test/resources/echo");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_exists__null() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileExists(null);

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_exists__empty() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileExists("");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_exists__blank() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileExists("   ");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_exists__invalid__does_not_exist() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileExists("src/test/resources/bad-file");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_exists__invalid__directory() {
        boolean expected = false;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileExists("src/test/resources/dir/");

        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_VALIDATOR__file_exists__valid() {
        boolean expected = true;
        FileSystemValidator fileSystemValidator = new FileSystemValidator();
        boolean actual = fileSystemValidator.fileExists("src/test/resources/echo");

        assertEquals(expected, actual);
    }
}
