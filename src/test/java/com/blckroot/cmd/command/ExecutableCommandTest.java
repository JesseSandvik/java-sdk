package com.blckroot.cmd.command;

import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ExecutableCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    //  ***** Set Command Attributes ***********************************************************************************

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_name() {
        String expected = "test";
        ExecutableCommand executableCommand = new ExecutableCommand(expected, "");

        String actual = executableCommand.getName();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_version() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        String expected = "1.2.3";
        executableCommand.setVersion(expected);

        String actual = executableCommand.getVersion();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_usage_description_synopsis() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        String expected = "Test description synopsis.";
        executableCommand.setUsageDescriptionSynopsis(expected);

        String actual = executableCommand.getUsageDescriptionSynopsis();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_usage_description_full() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        String expected = "Test description full.";
        executableCommand.setUsageDescriptionFull(expected);

        String actual = executableCommand.getUsageDescriptionFull();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executes_without_arguments() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        boolean expected = true;
        executableCommand.executesWithoutArguments(expected);

        boolean actual = executableCommand.executesWithoutArguments();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_positional_parameters() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        executableCommand.addPositionalParameter(positionalParameter);
        boolean expected = false;

        boolean actual = executableCommand.getPositionalParameters().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_options() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        Option option = new Option("", "");
        executableCommand.addOption(option);
        boolean expected = false;

        boolean actual = executableCommand.getOptions().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executable_subcommands() {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        ExecutableCommand executableSubcommand = new ExecutableCommand("subtest", "");
        executableCommand.addExecutableSubcommand(executableSubcommand);
        boolean expected = false;

        boolean actual = executableCommand.getExecutableSubcommands().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_ATTRIBUTES_SET_executable_file_path() {
        String expected = "/src/file/path";
        ExecutableCommand executableCommand = new ExecutableCommand("test", expected);

        String actual = executableCommand.getExecutableFilePath();
        assertEquals(actual, expected);
    }

    //  ***** Call Command *********************************************************************************************

    @Test
    void EXECUTABLE_COMMAND_call_no_arguments() throws Exception {
        ExecutableCommand executableCommand = new ExecutableCommand("test", "");
        int expected = 0;

        int actual = executableCommand.call();
        assertEquals(actual, expected);
    }

    @Test
    void EXECUTABLE_COMMAND_call_positional_parameter() throws Exception {
        ExecutableCommand executableCommand = new ExecutableCommand("echo", "src/test/resources/echo");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        String expected = "Hello, World!";
        positionalParameter.setValue(expected);
        executableCommand.addPositionalParameter(positionalParameter);
        executableCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_boolean() throws Exception {
        ExecutableCommand executableCommand = new ExecutableCommand("echo", "src/test/resources/echo");
        Option option = new Option(new String[]{"-t", "--test"}, "");
        option.setValue(true);
        executableCommand.addOption(option);
        String expected = option.getLongestName();
        executableCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void EXECUTABLE_COMMAND_call_option_string() throws Exception {
        ExecutableCommand executableCommand = new ExecutableCommand("echo", "src/test/resources/echo");
        Option option = new Option(new String[]{"-t", "--test"}, "", "");
        String value = "Hello";
        option.setValue(value);
        executableCommand.addOption(option);
        String expected = option.getLongestName() + " " + value;
        executableCommand.call();

        assertTrue(outContent.toString().contains(expected));
    }
}
