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
    void COMMAND_ORCHESTRATOR_USAGE_HELP_standard_short_option() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_USAGE_HELP_standard_long_option() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"--help"});

        assertTrue(outContent.toString().contains("-h"));
        assertTrue(outContent.toString().contains("--help"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_USAGE_HELP_exit_code_success() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        int expected = 0;
        int actual = commandOrchestrator.execute(new String[]{"-h"});

        assertEquals(actual, expected);
    }

    //  ***** Print Version Help ***************************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_option_not_present_for_command_without_version() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});
        assertFalse(outContent.toString().contains("-v"));
        assertFalse(outContent.toString().contains("--version"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_option_present_for_command_with_version() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});
        assertTrue(outContent.toString().contains("-v"));
        assertTrue(outContent.toString().contains("--version"));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_standard_short_option() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-v"});

        assertTrue(outContent.toString().contains(command.getVersion()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_standard_long_option() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"--version"});

        assertTrue(outContent.toString().contains(command.getVersion()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_VERSION_HELP_exit_code_success() {
        ExecutableCommand command = new ExecutableCommand("test", "");
        command.setVersion("1.2.3");
        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        int expected = 0;
        int actual = commandOrchestrator.execute(new String[]{"-v"});

        assertEquals(actual, expected);
    }

    //  ***** Print Positional Parameters ******************************************************************************

    @Test
    void COMMAND_ORCHESTRATOR_POSITIONAL_PARAMETERS_usage_help_label() {
        PositionalParameter positionalParameter = new PositionalParameter("labelA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addPositionalParameter(positionalParameter);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_POSITIONAL_PARAMETERS_usage_help_description() {
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
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_long_name_no_short() {
        Option option = new Option("--optionA", "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_description_no_short() {
        Option option = new Option("--optionA", "Description for optionA.");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getDescription()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_label_no_short() {
        Option option = new Option("--optionA", "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getParameterLabel()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_long_name() {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getLongestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_short_name() {
        Option option = new Option(new String[]{"--optionA", "-a"}, "");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getShortestName()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_description() {
        Option option = new Option(new String[]{"--optionA", "-a"}, "Description for optionA.");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getDescription()));
    }

    @Test
    void COMMAND_ORCHESTRATOR_OPTIONS_usage_help_label() {
        Option option = new Option(new String[]{"--optionA", "-a"}, "", "<labelA>");

        ExecutableCommand command = new ExecutableCommand("test", "");
        command.addOption(option);

        CommandOrchestrator commandOrchestrator = new CommandOrchestrator(command);
        commandOrchestrator.execute(new String[]{"-h"});

        assertTrue(outContent.toString().contains(option.getParameterLabel()));
    }
}
