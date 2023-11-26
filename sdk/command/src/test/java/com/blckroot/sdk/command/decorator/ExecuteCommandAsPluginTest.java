package com.blckroot.sdk.command.decorator;

import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ExecuteCommandAsPluginTest {
    final String VALID_EXECUTABLE_FILE_PATH = "src/test/resources/echo";
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
    void EXECUTE_COMMAND_AS_PLUGIN__exit_code__no_properties() throws Exception {
        int successExitCode = 0;
        Command command = new CallableCommand("test");
        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        int actual = executablePluginCommand.call();
        assertNotEquals(successExitCode, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__exit_code__no_executable_file_path_property() throws Exception {
        int successExitCode = 0;
        Properties properties = new Properties();
        properties.setProperty("placeholder key", "placeholder value");

        Command command = new CallableCommand("test");
        command.setProperties(properties);

        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        int actual = executablePluginCommand.call();
        assertNotEquals(successExitCode, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__exit_code__executable_file_path_does_not_exist() throws Exception {
        int successExitCode = 0;
        Properties properties = new Properties();
        properties.setProperty("executable.file.path", "src/test/resources/bad-file-path");

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);

        int actual = executablePluginCommand.call();
        assertNotEquals(successExitCode, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__exit_code__executable_file_path_not_executable() throws Exception {
        int successExitCode = 0;
        Properties properties = new Properties();
        properties.setProperty("executable.file.path", "src/test/resources/");

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);

        int actual = executablePluginCommand.call();
        assertNotEquals(successExitCode, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__exit_code__executable_file_path() throws Exception {
        int successExitCode = 0;
        Properties properties = new Properties();
        properties.setProperty("executable.file.path", "src/test/resources/echo");

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);

        int actual = executablePluginCommand.call();
        assertEquals(successExitCode, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__arguments__executable_file_path() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("executable.file.path", VALID_EXECUTABLE_FILE_PATH);

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        executablePluginCommand.call();

        String actual = command.getArguments().get(0);
        assertEquals(VALID_EXECUTABLE_FILE_PATH, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__arguments__positional_parameter() throws Exception {
        String expected = "positional parameter value";

        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("positional-parameter-A");
        positionalParameter.setSynopsis("positional-parameter-A synopsis");
        positionalParameter.setValue(expected);

        Properties properties = new Properties();
        properties.setProperty("executable.file.path", VALID_EXECUTABLE_FILE_PATH);

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        command.addPositionalParameter(positionalParameter);

        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        executablePluginCommand.call();

        String actual = command.getArguments().get(1);
        assertEquals(expected, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__arguments__option() throws Exception {
        String expected = "--optionA";

        Option option = new Option();
        option.setLongName(expected);
        option.setValue(true);

        Properties properties = new Properties();
        properties.setProperty("executable.file.path", VALID_EXECUTABLE_FILE_PATH);

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        command.addOption(option);

        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        executablePluginCommand.call();

        String actual = command.getArguments().get(1);
        assertEquals(expected, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__arguments__option__boolean_value() throws Exception {
        boolean expected = true;

        Option option = new Option();
        option.setLongName("--optionA");
        option.setValue(expected);

        Properties properties = new Properties();
        properties.setProperty("executable.file.path", VALID_EXECUTABLE_FILE_PATH);

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        command.addOption(option);

        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        executablePluginCommand.call();

        boolean actual = Boolean.parseBoolean(command.getArguments().get(2));
        assertEquals(expected, actual);
    }

    @Test
    void EXECUTE_COMMAND_AS_PLUGIN__arguments__option__string_value() throws Exception {
        String expected = "value for --optionA";

        Option option = new Option();
        option.setLongName("--optionA");
        option.setLabel("<labelA>");
        option.setValue(expected);

        Properties properties = new Properties();
        properties.setProperty("executable.file.path", VALID_EXECUTABLE_FILE_PATH);

        Command command = new CallableCommand("test");
        command.setProperties(properties);
        command.addOption(option);

        Command executablePluginCommand = new ExecuteCommandAsPlugin(command);
        executablePluginCommand.call();

        String actual = command.getArguments().get(2);
        assertEquals(expected, actual);
    }
}
