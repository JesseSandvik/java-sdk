package com.blckroot.cmd.command;

import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    //  ***** Set Command Attributes ***********************************************************************************

    @Test
    void COMMAND_ATTRIBUTES_SET_name() {
        String expected = "test";
        Command command = new Command(expected);

        String actual = command.getName();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_version() {
        Command command = new Command("test");
        String expected = "1.2.3";
        command.setVersion(expected);

        String actual = command.getVersion();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_usage_description_synopsis() {
        Command command = new Command("test");
        String expected = "Test description synopsis.";
        command.setUsageDescriptionSynopsis(expected);

        String actual = command.getUsageDescriptionSynopsis();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_usage_description_full() {
        Command command = new Command("test");
        String expected = "Test description full.";
        command.setUsageDescriptionFull(expected);

        String actual = command.getUsageDescriptionFull();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_executes_without_arguments() {
        Command command = new Command("test");
        boolean expected = true;
        command.executesWithoutArguments(expected);

        boolean actual = command.executesWithoutArguments();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_positional_parameters() {
        Command command = new Command("test");
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        command.addPositionalParameter(positionalParameter);
        boolean expected = false;

        boolean actual = command.getPositionalParameters().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_options() {
        Command command = new Command("test");
        Option option = new Option("", "");
        command.addOption(option);
        boolean expected = false;

        boolean actual = command.getOptions().isEmpty();
        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ATTRIBUTES_SET_subcommands() {
        Command command = new Command("test");
        Command subcommand = new Command("testSubA");
        command.addSubcommand(subcommand);
        boolean expected = false;

        boolean actual = command.getSubcommands().isEmpty();
        assertEquals(actual, expected);
    }

    //  ***** Call Command *********************************************************************************************

    @Test
    void COMMAND_call() throws Exception {
        Command command = new Command("test");
        int expected = 0;

        int actual = command.call();
        assertEquals(actual, expected);
    }
}
