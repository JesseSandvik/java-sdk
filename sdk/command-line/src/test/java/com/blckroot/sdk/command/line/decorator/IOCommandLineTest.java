package com.blckroot.sdk.command.line.decorator;

import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.line.CommandLine;
import com.blckroot.sdk.command.line.CommandLineCore;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class IOCommandLineTest {
    private final Command baseCommand = new CallableCommand("test");
    private final String baseCommandVersion = "1.0.0";
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

    Option getOption() {
        Option option = new Option();
        option.setLongName("--optionA");
        option.setSynopsis("OptionA synopsis");
        return option;
    }

    void addOptionShortName(Option option) {
        option.setShortName("-a");
    }

    void addOptionLabel(Option option) {
        option.setLabel("<labelA>");
    }

    Command getSubcommand() {
        return new CallableCommand("subtestA");
    }

    // BASE COMMAND

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__exit_code__success() throws Exception {
        int expected = 0;
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{"-h"});
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_name() throws Exception {
        String expected = baseCommand.getName();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_version__short() throws Exception {
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_version__long() throws Exception {
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_version__short() throws Exception {
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_version__long() throws Exception {
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_positional_parameter__label() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_positional_parameter__label() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addPositionalParameter(positionalParameter);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_positional_parameter__synopsis() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_positional_parameter__synopsis() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addPositionalParameter(positionalParameter);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_option__long_name() throws Exception {
        Option option = getOption();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_option__long_name() throws Exception {
        Option option = getOption();
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_option__synopsis() throws Exception {
        Option option = getOption();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_option__synopsis() throws Exception {
        Option option = getOption();
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_option__short_name() throws Exception {
        Option option = getOption();
        addOptionShortName(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_option__short_name() throws Exception {
        Option option = getOption();
        addOptionShortName(option);
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_option__label() throws Exception {
        Option option = getOption();
        addOptionLabel(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_option__label() throws Exception {
        Option option = getOption();
        addOptionLabel(option);
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_no_subcommand__name() throws Exception {
        Command subcommand = getSubcommand();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(subcommand.getName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__short_option__output__command_subcommand__name() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(subcommand.getName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__exit_code__success() throws Exception {
        int expected = 0;
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{"-help"});
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_name() throws Exception {
        String expected = baseCommand.getName();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_version__short() throws Exception {
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_version__long() throws Exception {
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_version__short() throws Exception {
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_version__long() throws Exception {
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_positional_parameter__label() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_positional_parameter__label() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addPositionalParameter(positionalParameter);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_positional_parameter__synopsis() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_positional_parameter__synopsis() throws Exception {
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addPositionalParameter(positionalParameter);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_option__long_name() throws Exception {
        Option option = getOption();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_option__long_name() throws Exception {
        Option option = getOption();
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_option__synopsis() throws Exception {
        Option option = getOption();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_option__synopsis() throws Exception {
        Option option = getOption();
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_option__short_name() throws Exception {
        Option option = getOption();
        addOptionShortName(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_option__short_name() throws Exception {
        Option option = getOption();
        addOptionShortName(option);
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_option__label() throws Exception {
        Option option = getOption();
        addOptionLabel(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_option__label() throws Exception {
        Option option = getOption();
        addOptionLabel(option);
        baseCommand.addOption(option);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_no_subcommand__name() throws Exception {
        Command subcommand = getSubcommand();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertFalse(out.toString().contains(subcommand.getName()));
    }

    @Test
    void IO_COMMAND_LINE__usage_help__long_option__output__command_subcommand__name() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{"-help"});

        assertTrue(out.toString().contains(subcommand.getName()));
    }

    @Test
    void IO_COMMAND_LINE__version_help__short_option__exit_code__success() throws Exception {
        int expected = 0;
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{"-v"});
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__version_help__short_option__output__command__version() throws Exception {
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{"-v"});
        assertTrue(out.toString().contains(baseCommandVersion));
    }

    @Test
    void IO_COMMAND_LINE__version_help__long_option__exit_code__success() throws Exception {
        int expected = 0;
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{"--version"});
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__version_help__long_option__output__command__version() throws Exception {
        baseCommand.setVersion(baseCommandVersion);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{"--version"});
        assertTrue(out.toString().contains(baseCommandVersion));
    }

    @Test
    void IO_COMMAND_LINE__parser__positional_parameter() throws Exception {
        String expected = "positional parameter value";
        baseCommand.addPositionalParameter(getPositionalParameter());

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{expected});

        String actual = baseCommand.getPositionalParameters().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__parser__option__short_name() throws Exception {
        String expected = "option value";
        Option option = getOption();
        addOptionShortName(option);
        addOptionLabel(option);
        baseCommand.addOption(option);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{option.getShortName(), expected});

        String actual = baseCommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__parser__option__long_name() throws Exception {
        String expected = "option value";
        Option option = getOption();
        addOptionShortName(option);
        addOptionLabel(option);
        baseCommand.addOption(option);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{option.getLongName(), expected});

        String actual = baseCommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    // SUBCOMMAND

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__exit_code__success() throws Exception {
        int expected = 0;
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{subcommand.getName(), "-h"});
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_name() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        String expected = subcommand.getName();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{expected, "-h"});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_version__short() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_version__long() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_version__short() throws Exception {
        Command subcommand = getSubcommand();
        subcommand.setVersion(baseCommandVersion);
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_version__long() throws Exception {
        Command subcommand = getSubcommand();
        subcommand.setVersion(baseCommandVersion);
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_positional_parameter__label() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        PositionalParameter positionalParameter = getPositionalParameter();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_positional_parameter__label() throws Exception {
        Command subcommand = getSubcommand();
        PositionalParameter positionalParameter = getPositionalParameter();
        subcommand.addPositionalParameter(positionalParameter);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_positional_parameter__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_positional_parameter__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        PositionalParameter positionalParameter = getPositionalParameter();
        subcommand.addPositionalParameter(positionalParameter);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_option__long_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_option__long_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_option__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_option__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_option__short_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_option__short_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_no_option__label() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionLabel(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__short_option__output__command_option__label() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionLabel(option);
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "-h"});

        assertTrue(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__exit_code__success() throws Exception {
        int expected = 0;
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        int actual = commandLine.execute(new String[]{subcommand.getName(), "--help"});
        assertEquals(expected, actual);
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_name() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        String expected = subcommand.getName();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{expected, "--help"});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_version__short() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_version__long() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_version__short() throws Exception {
        Command subcommand = getSubcommand();
        subcommand.setVersion(baseCommandVersion);
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_version__long() throws Exception {
        Command subcommand = getSubcommand();
        subcommand.setVersion(baseCommandVersion);
        baseCommand.addSubcommand(subcommand);

        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_positional_parameter__label() throws Exception {
        Command subcommand = getSubcommand();
        baseCommand.addSubcommand(subcommand);

        PositionalParameter positionalParameter = getPositionalParameter();
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_positional_parameter__label() throws Exception {
        Command subcommand = getSubcommand();
        PositionalParameter positionalParameter = getPositionalParameter();
        subcommand.addPositionalParameter(positionalParameter);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_positional_parameter__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        PositionalParameter positionalParameter = getPositionalParameter();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_positional_parameter__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        PositionalParameter positionalParameter = getPositionalParameter();
        subcommand.addPositionalParameter(positionalParameter);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_option__long_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_option__long_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_option__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_option__synopsis() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_option__short_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_option__short_name() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_no_option__label() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionLabel(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void IO_COMMAND_LINE__subcommand__usage_help__long_option__output__command_option__label() throws Exception {
        Command subcommand = getSubcommand();
        Option option = getOption();
        addOptionLabel(option);
        subcommand.addOption(option);
        baseCommand.addSubcommand(subcommand);
        CommandLine commandLine = new IOCommandLine(getCommandLineCore());
        commandLine.execute(new String[]{subcommand.getName(), "--help"});

        assertTrue(out.toString().contains(option.getLabel()));
    }
}
