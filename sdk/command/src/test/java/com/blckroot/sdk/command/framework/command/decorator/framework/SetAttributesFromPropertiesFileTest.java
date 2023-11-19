package com.blckroot.sdk.command.framework.command.decorator.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.FrameworkCommand;
import com.blckroot.sdk.file.system.service.FileSystemService;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class SetAttributesFromPropertiesFileTest {
    private final String VALID_PROPERTIES_DIRECTORY = "src/test/resources/";
    private final String VALID_PROPERTIES_FILE_PATH = VALID_PROPERTIES_DIRECTORY + "test.properties";

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__null() throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), null);
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__empty() throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), "");
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__blank() throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), "   ");
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__invalid() throws Exception {
        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), "src/test/");
        command.call();
        assertNull(command.getProperties());
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__version() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("version");

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();

        String actual = command.getVersion();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__synopsis() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("synopsis");

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__description() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("description");

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getDescription();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__executes_without_arguments() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        boolean expected = Boolean.parseBoolean(properties.getProperty("executes.without.arguments"));

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        boolean actual = command.isExecutesWithoutArguments();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__label() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.positional.parameter.label");

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getPositionalParameters().get(0).getLabel();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__synopsis() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.positional.parameter.synopsis");

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = command.getPositionalParameters().get(0).getSynopsis();
        assertEquals(expected, actual);
    }

    @Test
    void SET_ATTRIBUTES_FROM_PROPERTIES_FILE__valid__positional_parameter__value__string() throws Exception {
        FileSystemService fileSystemService = new FileSystemService();
        Properties properties = fileSystemService.getPropertiesFromFile(VALID_PROPERTIES_FILE_PATH);
        String expected = properties.getProperty("1.positional.parameter.value");

        FrameworkBaseCommand command = new SetAttributesFromPropertiesFile(
                new FrameworkCommand("test"), VALID_PROPERTIES_DIRECTORY);
        command.call();
        String actual = (String) command.getPositionalParameters().get(0).getValue();
        assertEquals(expected, actual);
    }
}
