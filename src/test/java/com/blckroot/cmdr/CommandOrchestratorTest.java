package com.blckroot.cmdr;

import com.blckroot.cmd.command.ExecutableCommand;
import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CommandOrchestratorTest {
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

    //  ***** Print Usage Help *****************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_USAGE_HELP_no_arguments() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{});

        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_USAGE_HELP_standard_short_option() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_USAGE_HELP_standard_long_option() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"--help"});

        assertTrue(outContent.toString().contains(command.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_USAGE_HELP_exit_code_success() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        int expected = 0;
        int actual = commandOrchestrator.execute(new String[]{"-h"});

        assertEquals(actual, expected);
    }

    //  ***** Print Version Help ***************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_option_not_present_for_command_without_version() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});
        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_option_present_for_command_with_version() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});
        assertTrue(outContent.toString().contains("-v"));
        assertTrue(outContent.toString().contains("--version"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_standard_short_option() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-v"});

        assertTrue(outContent.toString().contains(command.getVersion()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_standard_long_option() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"--version"});

        assertTrue(outContent.toString().contains(command.getVersion()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_exit_code_success() throws Exception {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        int expected = 0;
        int actual = commandOrchestrator.execute(new String[]{"-v"});

        assertEquals(actual, expected);
    }

    //  ***** Print Positional Parameters ******************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_POSITIONAL_PARAMETERS_usage_help_label() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("labelA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addPositionalParameter(positionalParameter);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_POSITIONAL_PARAMETERS_usage_help_description() throws Exception {
        PositionalParameter positionalParameter =
                new PositionalParameter("", "Positional parameter description.");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addPositionalParameter(positionalParameter);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(positionalParameter.getDescription()));
    }

    //  ***** Print Options ********************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_long_name_no_short() throws Exception {
        Option option = new Option("--optionA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_description_no_short() throws Exception {
        Option option = new Option("--optionA", "Description for optionA.");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getDescription()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_label_no_short() throws Exception {
        Option option = new Option("--optionA", "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getParameterLabel()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_long_name() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_short_name() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getShortestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_description() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "Description for optionA.");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getDescription()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getParameterLabel()));
    }

    //  ***** Print Subcommands ****************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_usage_help_name() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_usage_help_synopsis() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.setUsageDescriptionSynopsis("subA synopsis.");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(subcommand.getUsageDescriptionSynopsis()));
    }

    //  ***** Print Subcommand Usage Help ******************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_USAGE_HELP_no_arguments() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName()});

        assertTrue(outContent.toString().contains(subcommand.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_USAGE_HELP_standard_short_option() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(subcommand.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_USAGE_HELP_standard_long_option() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(outContent.toString().contains(subcommand.getName()));
        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_USAGE_HELP_exit_code_success() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "--help"});

        int expected = 0;
        int actual = commandOrchestrator.execute(new String[]{"-h"});

        assertEquals(actual, expected);
    }

    //  ***** Print Subcommand Version Help ****************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_VERSION_HELP_option_not_present_for_command_without_version() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_VERSION_HELP_option_present_for_command_with_version() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.setVersion("5.6.7");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains("-v"));
        assertTrue(outContent.toString().contains("--version"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_VERSION_HELP_standard_short_option() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.setVersion("5.6.7");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-v"});

        assertTrue(outContent.toString().contains(subcommand.getVersion()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_VERSION_HELP_standard_long_option() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.setVersion("5.6.7");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "--version"});

        assertTrue(outContent.toString().contains(subcommand.getVersion()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_VERSION_HELP_exit_code_success() throws Exception {
        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.setVersion("5.6.7");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "--version"});

        int expected = 0;
        int actual = commandOrchestrator.execute(new String[]{subcommand.getName(), "-v"});

        assertEquals(actual, expected);
    }

    //  ***** Print Subcommand Positional Parameters *******************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_POSITIONAL_PARAMETERS_usage_help_label() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("labelA", "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addPositionalParameter(positionalParameter);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_POSITIONAL_PARAMETERS_usage_help_description() throws Exception {
        PositionalParameter positionalParameter =
                new PositionalParameter("", "Positional parameter description.");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addPositionalParameter(positionalParameter);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(positionalParameter.getDescription()));
    }

    //  ***** Print Subcommand Options *********************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_long_name_no_short() throws Exception {
        Option option = new Option("--optionA", "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_description_no_short() throws Exception {
        Option option = new Option("--optionA", "Description for optionA.");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getDescription()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_label_no_short() throws Exception {
        Option option = new Option("--optionA", "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getParameterLabel()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_long_name() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_short_name() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getShortestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_description() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "Description for optionA.");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getDescription()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_SUBCOMMANDS_OPTIONS_usage_help_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);


        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(outContent.toString().contains(option.getParameterLabel()));
    }

    //  ***** Print Nested Subcommands *********************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_NESTED_SUBCOMMANDS_usage_help_name() throws Exception {
        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(nestedSubcommand.getName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_NESTED_SUBCOMMANDS_usage_help_synopsis() throws Exception {
        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.setUsageDescriptionSynopsis("nestedSubA synopsis.");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(nestedSubcommand.getUsageDescriptionSynopsis()));
    }

    //  ***** Set Positional Parameter Values **************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_POSITIONAL_PARAMETER_parent_command() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("labelA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addPositionalParameter(positionalParameter);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{expected});

        Object actual = positionalParameter.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_POSITIONAL_PARAMETER_subcommand() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("labelA", "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addPositionalParameter(positionalParameter);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), expected});

        Object actual = positionalParameter.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_POSITIONAL_PARAMETER_nested_subcommand() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("labelA", "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addPositionalParameter(positionalParameter);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{nestedSubcommand.getName(), expected});

        Object actual = positionalParameter.getValue();

        assertEquals(actual, expected);
    }

    //  ***** Set Option Values ****************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_no_short_no_label_parent_command() throws Exception {
        Option option = new Option("--optionA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName()});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_no_short_label_parent_command() throws Exception {
        Option option = new Option("--optionA", "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName(), expected});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_short_no_label_parent_command() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getShortestName()});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_short_label_parent_command() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getShortestName(), expected});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_long_no_label_parent_command() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName()});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_long_label_parent_command() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName(), expected});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

@Test
void COMMAND_ORCHESTRATOR_VALUES_OPTION_no_short_no_label_subcommand() throws Exception {
    Option option = new Option("--optionA", "");

    ExecutableCommand subcommand = new ExecutableCommand("subA", "");
    subcommand.addOption(option);

    ExecutableCommand command = new ExecutableCommand("test", "");
    command.addExecutableSubcommand(subcommand);

    boolean expected = true;

    CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
    commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName()});

    Object actual = option.getValue();

    assertEquals(actual, expected);
}

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_no_short_label_subcommand() throws Exception {
        Option option = new Option("--optionA", "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName(), expected});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_short_no_label_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getShortestName()});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_short_label_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getShortestName(), expected});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_long_no_label_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName()});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_long_label_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName(), expected});

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_no_short_no_label_nested_subcommand() throws Exception {
        Option option = new Option("--optionA", "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName()
                }
        );

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_no_short_label_nested_subcommand() throws Exception {
        Option option = new Option("--optionA", "", "<labelA>");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName(),
                        expected
                }
        );

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_short_no_label_nested_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getShortestName()
                }
        );

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_short_label_nested_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getShortestName(),
                        expected
                }
        );

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_long_no_label_nested_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        boolean expected = true;

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName()
                }
        );

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    @Test
    void COMMAND_ORCHESTRATOR_VALUES_OPTION_long_label_nested_subcommand() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("nestedSubA", "");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName(),
                        expected
                }
        );

        Object actual = option.getValue();

        assertEquals(actual, expected);
    }

    //  ***** Command Execution ****************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_positional_parameter() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("text", "");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addPositionalParameter(positionalParameter);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{expected});

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_option_no_short_no_label() throws Exception {
        Option option = new Option("--optionA", "");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName()});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_option_no_short_label() throws Exception {
        Option option = new Option("--optionA", "","<labelA>");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addOption(option);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName(), value});

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_option_short_no_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getShortestName()});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_option_short_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addOption(option);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getShortestName(), value});

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_option_long_no_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName()});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_parent_command_option_long_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");
        ExecutableCommand command = new ExecutableCommand("echo", "src/test/resources/echo");
        command.addOption(option);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{option.getLongestName(), value});

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_positional_parameter() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("text", "");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addPositionalParameter(positionalParameter);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), expected});

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_option_no_short_no_label() throws Exception {
        Option option = new Option("--optionA", "");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName()});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_option_no_short_label() throws Exception {
        Option option = new Option("--optionA", "","<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName(), value});

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_option_short_no_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getShortestName()});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_option_short_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getShortestName(), value});

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_option_long_no_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName()});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_subcommand_option_long_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand subcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        subcommand.addOption(option);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongestName(), value});

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }
//    Nested Subcommand
    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_positional_parameter() throws Exception {
        PositionalParameter positionalParameter = new PositionalParameter("text", "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addPositionalParameter(positionalParameter);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String expected = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        expected
                }
        );

        assertTrue(outContent.toString().contains(expected));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_option_no_short_no_label() throws Exception {
        Option option = new Option("--optionA", "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName()
                }
        );

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_option_no_short_label() throws Exception {
        Option option = new Option("--optionA", "","<labelA>");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName(),
                        value
                }
        );

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_option_short_no_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getShortestName()
                }
        );

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_option_short_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getShortestName(),
                        value
                }
        );

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_option_long_no_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName()
                }
        );

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_COMMAND_EXECUTION_nested_subcommand_option_long_label() throws Exception {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand nestedSubcommand = new ExecutableCommand("echo", "src/test/resources/echo");
        nestedSubcommand.addOption(option);

        ExecutableCommand subcommand = new ExecutableCommand("subA", "");
        subcommand.addExecutableSubcommand(nestedSubcommand);

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addExecutableSubcommand(subcommand);

        String value = "Hello";

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(
                new String[]{
                        subcommand.getName(),
                        nestedSubcommand.getName(),
                        option.getLongestName(),
                        value
                }
        );

        assertTrue(outContent.toString().contains(option.getLongestName()));
        assertTrue(outContent.toString().contains(value));
    }
}
