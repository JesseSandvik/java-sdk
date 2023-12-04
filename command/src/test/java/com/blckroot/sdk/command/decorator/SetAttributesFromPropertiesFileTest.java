package com.blckroot.sdk.command.decorator;

import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.file.system.service.FileSystemService;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class SetAttributesFromPropertiesFileTest {
    private final String VALID_PROPERTIES_DIRECTORY = "src/test/resources/";
    private final String VALID_PROPERTIES_FILE_PATH = VALID_PROPERTIES_DIRECTORY + "test.properties";

    // Command Model Attributes

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__null() throws Exception {
        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), null);
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__empty() throws Exception {
        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), "");
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__blank() throws Exception {
        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), "   ");
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__invalid() throws Exception {
        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), "src/test/");
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__properties() throws Exception {
        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        assertNotNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__version() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("version");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();

        String actual = command.getVersion();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__synopsis() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("synopsis");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__description() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("description");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__executes_without_arguments() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        boolean expected = Boolean.parseBoolean(properties.getProperty("executes.without.arguments"));

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        boolean actual = command.isExecutesWithoutArguments();
        assertEquals(expected, actual);
    }

    // Framework Command Positional Parameters

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter_count() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        int expected = Integer.parseInt(properties.getProperty("positional.parameter.count"));

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        int actual = command.getPositionalParameters().size();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__label__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.positional.parameter.label");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getPositionalParameters().get(0).getLabel();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__synopsis__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.positional.parameter.synopsis");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getPositionalParameters().get(0).getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__value__string__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.positional.parameter.value");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = (String) command.getPositionalParameters().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__label__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("2.positional.parameter.label");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getPositionalParameters().get(1).getLabel();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__synopsis__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("2.positional.parameter.synopsis");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getPositionalParameters().get(1).getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__value__boolean__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        boolean expected = Boolean.parseBoolean(properties.getProperty("2.positional.parameter.value"));

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        boolean actual = !command.getPositionalParameters().get(1).getValue().toString().isBlank();
        assertEquals(expected, actual);
    }

    // Framework Command Options

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_count() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        int expected = Integer.parseInt(properties.getProperty("option.count"));

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        int actual = command.getOptions().size();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_long_name__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.option.long.name");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(0).getLongName();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_short_name__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.option.short.name");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(0).getShortName();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_synopsis__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.option.synopsis");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(0).getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_label__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.option.label");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(0).getLabel();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_value__string__first_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.option.value");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_long_name__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("2.option.long.name");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(1).getLongName();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_short_name__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("2.option.short.name");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(1).getShortName();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_synopsis__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("2.option.synopsis");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(1).getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_label__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("2.option.label");

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getOptions().get(1).getLabel();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__option_value__boolean__second_index() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        boolean expected = Boolean.parseBoolean(properties.getProperty("2.option.value"));

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        boolean actual = (boolean) !command.getOptions().get(1).getValue().toString().isBlank();
        assertEquals(expected, actual);
    }

    // Framework Command Subcommands

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__subcommands() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        int expected = properties.getProperty("subcommands").split(",").length;

        Command command = new SetAttributesFromPropertiesFile(
                new CallableCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        int actual = command.getSubcommands().size();
    }
}
