package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.line.CommandLine;
import com.blckroot.sdk.command.line.CommandLineCore;
import com.blckroot.sdk.command.model.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class FormattedIOCommandLineTest {
    private final Command baseCommand = new CallableCommand("test");
    private final String COMMANDS_HEADING = "Commands:";
    private final String POSITIONAL_PARAMETER_HEADING = "Positional Parameters:";
    private final String OPTIONS_HEADING = "Options:";
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

    CommandLine getCommandLineCore() {
        return new CommandLineCore(baseCommand);
    }

    PositionalParameter getPositionalParameter() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("parameterA");
        positionalParameter.setSynopsis("Positional Parameter A synopsis");
        return positionalParameter;
    }

    Command getSubcommand() {
        return new CallableCommand("subtestA");
    }

    @Test
    void FORMATTED_IO_COMMAND_LINE__positional_parameters_heading__no_positional_parameter() throws Exception {
        FormattedIOCommandLine commandLine = new FormattedIOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{});
        assertFalse(out.toString().contains(POSITIONAL_PARAMETER_HEADING));
    }

    @Test
    void FORMATTED_IO_COMMAND_LINE__positional_parameters_heading__positional_parameter() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addPositionalParameter(positionalParameter);

        FormattedIOCommandLine commandLine = new FormattedIOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{});
        assertTrue(out.toString().contains(POSITIONAL_PARAMETER_HEADING));
    }

    @Test
    void FORMATTED_IO_COMMAND_LINE__options_heading() throws Exception {
        baseCommand.addPositionalParameter(getPositionalParameter());

        FormattedIOCommandLine commandLine = new FormattedIOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{});
        assertTrue(out.toString().contains(OPTIONS_HEADING));
    }

    @Test
    void FORMATTED_IO_COMMAND_LINE__commands_heading__no_subcommands() throws Exception {
        FormattedIOCommandLine commandLine = new FormattedIOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{});
        assertFalse(out.toString().contains(COMMANDS_HEADING));
    }

    @Test
    void FORMATTED_IO_COMMAND_LINE__commands_heading__subcommand() throws Exception {
        baseCommand.addSubcommand(getSubcommand());

        FormattedIOCommandLine commandLine = new FormattedIOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{});
        assertTrue(out.toString().contains(COMMANDS_HEADING));
    }
}
