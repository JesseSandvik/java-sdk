package com.blckroot.sdk.file.system.executor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileSystemExecutorTest {
    private final String EXECUTABLE_FILE_PATH = "src/test/resources/echo";
    final PrintStream originalOut = System.out;
    final PrintStream originalErr = System.err;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        out.reset();
        err.reset();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void FILE_SYSTEM_EXECUTOR__execute_command__exit_code__success() {
        int expected = 0;
        List<String> arguments = new ArrayList<>();

        arguments.add(EXECUTABLE_FILE_PATH);
        arguments.add("Hello, World!");

        FileSystemExecutor fileSystemExecutor = new FileSystemExecutor();
        int actual = fileSystemExecutor.executeCommand(arguments, System.out);
        assertEquals(expected, actual);
    }

    @Test
    void FILE_SYSTEM_EXECUTOR__execute_command__output__success() {
        String expected = "Hello, World!";
        List<String> arguments = new ArrayList<>();

        arguments.add(EXECUTABLE_FILE_PATH);
        arguments.add(expected);

        FileSystemExecutor fileSystemExecutor = new FileSystemExecutor();
        fileSystemExecutor.executeCommand(arguments, System.out);

        assertTrue(out.toString().contains(expected));
    }
}
