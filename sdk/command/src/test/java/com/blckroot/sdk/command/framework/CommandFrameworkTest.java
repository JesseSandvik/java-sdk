package com.blckroot.sdk.command.framework;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.FrameworkCommand;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CommandFrameworkTest {
    private final FrameworkBaseCommand frameworkBaseCommand = new FrameworkCommand("test");
    private final String frameworkBaseCommandVersion = "1.0.0";
    private final String frameworkBaseCommandSynopsis = "test synopsis";
    private final String getFrameworkBaseCommandDescription = "test command full description with examples.";
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

    FrameworkBaseCommand getSubcommand() {
        FrameworkBaseCommand subcommand = new FrameworkCommand("subtestA");
        subcommand.setSynopsis("subtestA synopsis");
        return subcommand;
    }

    FrameworkBaseCommand getNestedSubcommand() {
        FrameworkBaseCommand subcommand = new FrameworkCommand("subtestB");
        subcommand.setSynopsis("subtestB synopsis");
        return subcommand;
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__exit_code__success() {
        int expected = 0;
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{"-h"});
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_name() {
        String expected = frameworkBaseCommand.getName();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_version__short() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_version__long() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_version__short() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_version__long() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_synopsis() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(frameworkBaseCommandSynopsis));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_synopsis() {
        frameworkBaseCommand.setSynopsis(frameworkBaseCommandSynopsis);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(frameworkBaseCommandSynopsis));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_description() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(getFrameworkBaseCommandDescription));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_description() {
        frameworkBaseCommand.setDescription(getFrameworkBaseCommandDescription);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(getFrameworkBaseCommandDescription));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_positional_parameter__label() {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_positional_parameter__label() {
        PositionalParameter positionalParameter = getPositionalParameter();
        frameworkBaseCommand.addPositionalParameter(positionalParameter);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_positional_parameter__synopsis() {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_positional_parameter__synopsis() {
        PositionalParameter positionalParameter = getPositionalParameter();
        frameworkBaseCommand.addPositionalParameter(positionalParameter);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__long_name() {
        Option option = getOption();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__long_name() {
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__synopsis() {
        Option option = getOption();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__synopsis() {
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__short_name() {
        Option option = getOption();
        addOptionShortName(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__short_name() {
        Option option = getOption();
        addOptionShortName(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__label() {
        Option option = getOption();
        addOptionLabel(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__label() {
        Option option = getOption();
        addOptionLabel(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(option.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_subcommand__name() {
        FrameworkBaseCommand subcommand = getSubcommand();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_subcommand__name() {
        FrameworkBaseCommand subcommand = getSubcommand();
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_subcommand__synopsis() {
        FrameworkBaseCommand subcommand = getSubcommand();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertFalse(out.toString().contains(subcommand.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_subcommand__synopsis() {
        FrameworkBaseCommand subcommand = getSubcommand();
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"-h"});

        assertTrue(out.toString().contains(subcommand.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__exit_code__success() {
        int expected = 0;
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{"--help"});
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_name() {
        String expected = frameworkBaseCommand.getName();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_version__short() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_version__long() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_version__short() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_version__long() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_synopsis() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(frameworkBaseCommandSynopsis));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_synopsis() {
        frameworkBaseCommand.setSynopsis(frameworkBaseCommandSynopsis);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(frameworkBaseCommandSynopsis));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_description() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(getFrameworkBaseCommandDescription));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_description() {
        frameworkBaseCommand.setDescription(getFrameworkBaseCommandDescription);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(getFrameworkBaseCommandDescription));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_positional_parameter__label() {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_positional_parameter__label() {
        PositionalParameter positionalParameter = getPositionalParameter();
        frameworkBaseCommand.addPositionalParameter(positionalParameter);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_positional_parameter__synopsis() {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_positional_parameter__synopsis() {
        PositionalParameter positionalParameter = getPositionalParameter();
        frameworkBaseCommand.addPositionalParameter(positionalParameter);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__long_name() {
        Option option = getOption();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__long_name() {
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__synopsis() {
        Option option = getOption();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__synopsis() {
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__short_name() {
        Option option = getOption();
        addOptionShortName(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__short_name() {
        Option option = getOption();
        addOptionShortName(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__label() {
        Option option = getOption();
        addOptionLabel(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__label() {
        Option option = getOption();
        addOptionLabel(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(option.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_subcommand__name() {
        FrameworkBaseCommand subcommand = getSubcommand();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_subcommand__name() {
        FrameworkBaseCommand subcommand = getSubcommand();
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_subcommand__synopsis() {
        FrameworkBaseCommand subcommand = getSubcommand();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains(subcommand.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_subcommand__synopsis() {
        FrameworkBaseCommand subcommand = getSubcommand();
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertTrue(out.toString().contains(subcommand.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__exit_code__success() {
        int expected = 0;
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{});
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_name() {
        String expected = frameworkBaseCommand.getName();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(expected));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_version__short() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{"--help"});

        assertFalse(out.toString().contains("-v"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_version__long() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains("--version"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_version__short() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains("-v"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_version__long() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains("--version"));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_synopsis() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(frameworkBaseCommandSynopsis));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_synopsis() {
        frameworkBaseCommand.setSynopsis(frameworkBaseCommandSynopsis);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(frameworkBaseCommandSynopsis));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_description() {
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(getFrameworkBaseCommandDescription));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_description() {
        frameworkBaseCommand.setDescription(getFrameworkBaseCommandDescription);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(getFrameworkBaseCommandDescription));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_positional_parameter__label() {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_positional_parameter__label() {
        PositionalParameter positionalParameter = getPositionalParameter();
        frameworkBaseCommand.addPositionalParameter(positionalParameter);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(positionalParameter.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_positional_parameter__synopsis() {
        PositionalParameter positionalParameter = getPositionalParameter();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_positional_parameter__synopsis() {
        PositionalParameter positionalParameter = getPositionalParameter();
        frameworkBaseCommand.addPositionalParameter(positionalParameter);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__long_name() {
        Option option = getOption();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(option.getLongName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__long_name() {
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(option.getLongName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__synopsis() {
        Option option = getOption();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__synopsis() {
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(option.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__short_name() {
        Option option = getOption();
        addOptionShortName(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(option.getShortName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__short_name() {
        Option option = getOption();
        addOptionShortName(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(option.getShortName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__label() {
        Option option = getOption();
        addOptionLabel(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(option.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__label() {
        Option option = getOption();
        addOptionLabel(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(option.getLabel()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_subcommand__name() {
        FrameworkBaseCommand subcommand = getSubcommand();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_subcommand__name() {
        FrameworkBaseCommand subcommand = getSubcommand();
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(subcommand.getName()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_subcommand__synopsis() {
        FrameworkBaseCommand subcommand = getSubcommand();
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertFalse(out.toString().contains(subcommand.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_subcommand__synopsis() {
        FrameworkBaseCommand subcommand = getSubcommand();
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{});

        assertTrue(out.toString().contains(subcommand.getSynopsis()));
    }

    @Test
    void COMMAND_FRAMEWORK__version_help__short_option__exit_code__success() {
        int expected = 0;
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{"-v"});
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__version_help__short_option__output() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{"-v"});
        assertTrue(out.toString().contains(frameworkBaseCommandVersion));
    }

    @Test
    void COMMAND_FRAMEWORK__version_help__long_option__exit_code__success() {
        int expected = 0;
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{"--version"});
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__version_help__long_option__output() {
        frameworkBaseCommand.setVersion(frameworkBaseCommandVersion);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        int actual = commandFramework.execute(new String[]{"--version"});
        assertTrue(out.toString().contains(frameworkBaseCommandVersion));
    }

    @Test
    void COMMAND_FRAMEWORK__parse__positional_parameter__base_command() {
        String expected = "first positional parameter value";
        frameworkBaseCommand.addPositionalParameter(getPositionalParameter());
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{expected});

        String actual = frameworkBaseCommand.getPositionalParameters().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__long_name__boolean__base_command() {
        boolean expected = true;
        Option option = getOption();
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{option.getLongName()});

        boolean actual = (boolean) frameworkBaseCommand.getOptions().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__short_name__boolean__base_command() {
        boolean expected = true;
        Option option = getOption();
        addOptionShortName(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{option.getShortName()});

        boolean actual = (boolean) frameworkBaseCommand.getOptions().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__long_name__string__base_command() {
        String expected = "option value";
        Option option = getOption();
        addOptionLabel(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{option.getLongName(), expected});

        String actual = frameworkBaseCommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__short_name__string__base_command() {
        String expected = "option value";
        Option option = getOption();
        addOptionShortName(option);
        addOptionLabel(option);
        frameworkBaseCommand.addOption(option);
        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{option.getShortName(), expected});

        String actual = frameworkBaseCommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__positional_parameter__subcommand() {
        String expected = "first positional parameter value";

        FrameworkBaseCommand subcommand = getSubcommand();
        subcommand.addPositionalParameter(getPositionalParameter());
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), expected});

        String actual = subcommand.getPositionalParameters().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__long_name__boolean__subcommand() {
        boolean expected = true;

        FrameworkBaseCommand subcommand = getSubcommand();
        Option option = getOption();
        subcommand.addOption(option);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), option.getLongName()});

        boolean actual = (boolean) subcommand.getOptions().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__short_name__boolean__subcommand() {
        boolean expected = true;

        FrameworkBaseCommand subcommand = getSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        subcommand.addOption(option);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), option.getShortName()});

        boolean actual = (boolean) subcommand.getOptions().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__long_name__string__subcommand() {
        String expected = "option value";

        FrameworkBaseCommand subcommand = getSubcommand();
        Option option = getOption();
        addOptionLabel(option);
        subcommand.addOption(option);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), option.getLongName(), expected});

        String actual = subcommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__short_name__string__subcommand() {
        String expected = "option value";

        FrameworkBaseCommand subcommand = getSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        addOptionLabel(option);
        subcommand.addOption(option);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), option.getShortName(), expected});

        String actual = subcommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__positional_parameter__nested_subcommand() {
        String expected = "first positional parameter value";

        FrameworkBaseCommand nestedSubcommand = getNestedSubcommand();
        nestedSubcommand.addPositionalParameter(getPositionalParameter());

        FrameworkBaseCommand subcommand = getSubcommand();
        subcommand.addFrameworkSubcommand(nestedSubcommand);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), expected});

        String actual = nestedSubcommand.getPositionalParameters().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__long_name__boolean__nested_subcommand() {
        boolean expected = true;

        FrameworkBaseCommand nestedSubcommand = getNestedSubcommand();
        Option option = getOption();
        nestedSubcommand.addOption(option);

        FrameworkBaseCommand subcommand = getSubcommand();
        subcommand.addFrameworkSubcommand(nestedSubcommand);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getLongName()});

        boolean actual = (boolean) nestedSubcommand.getOptions().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__short_name__boolean__nested_subcommand() {
        boolean expected = true;

        FrameworkBaseCommand nestedSubcommand = getNestedSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        nestedSubcommand.addOption(option);

        FrameworkBaseCommand subcommand = getSubcommand();
        subcommand.addFrameworkSubcommand(nestedSubcommand);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getShortName()});

        boolean actual = (boolean) nestedSubcommand.getOptions().get(0).getValue();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__long_name__string__nested_subcommand() {
        String expected = "option value";

        FrameworkBaseCommand nestedSubcommand = getNestedSubcommand();
        Option option = getOption();
        addOptionLabel(option);
        nestedSubcommand.addOption(option);

        FrameworkBaseCommand subcommand = getSubcommand();
        subcommand.addFrameworkSubcommand(nestedSubcommand);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getLongName(), expected});

        String actual = nestedSubcommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }

    @Test
    void COMMAND_FRAMEWORK__parse__option__short_name__string__nested_subcommand() {
        String expected = "option value";

        FrameworkBaseCommand nestedSubcommand = getNestedSubcommand();
        Option option = getOption();
        addOptionShortName(option);
        addOptionLabel(option);
        nestedSubcommand.addOption(option);

        FrameworkBaseCommand subcommand = getSubcommand();
        subcommand.addFrameworkSubcommand(nestedSubcommand);
        frameworkBaseCommand.addFrameworkSubcommand(subcommand);

        CommandFramework commandFramework = new CommandFramework(frameworkBaseCommand);
        commandFramework.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getShortName(), expected});

        String actual = nestedSubcommand.getOptions().get(0).getValue().toString();
        assertEquals(expected, actual);
    }
}
