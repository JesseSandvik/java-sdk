//package com.blckroot.sdk.command.orchestrator.decorator;
//
//import com.blckroot.sdk.command.CallableCommand;
//import com.blckroot.sdk.command.Command;
//import com.blckroot.sdk.command.model.Option;
//import com.blckroot.sdk.command.model.PositionalParameter;
//import com.blckroot.sdk.command.orchestrator.CommandOrchestrator;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class OrchestrateTest {
//    private final Command command = new CallableCommand("test");
//    private final String commandVersion = "1.0.0";
//    private final String commandSynopsis = "test synopsis";
//    private final String commandDescription = "test command full description with examples.";
//    final PrintStream originalOut = System.out;
//    final PrintStream originalErr = System.err;
//    final ByteArrayOutputStream out = new ByteArrayOutputStream();
//    final ByteArrayOutputStream err = new ByteArrayOutputStream();
//
//    @BeforeEach
//    void setUpStreams() {
//        out.reset();
//        err.reset();
//        System.setOut(new PrintStream(out));
//        System.setErr(new PrintStream(err));
//    }
//
//    @AfterEach
//    void restoreStreams() {
//        System.setOut(originalOut);
//        System.setErr(originalErr);
//    }
//
//    PositionalParameter getPositionalParameter() {
//        PositionalParameter positionalParameter = new PositionalParameter();
//        positionalParameter.setLabel("parameterA");
//        positionalParameter.setSynopsis("Positional Parameter A synopsis");
//        return positionalParameter;
//    }
//
//    Option getOption() {
//        Option option = new Option();
//        option.setLongName("--optionA");
//        option.setSynopsis("OptionA synopsis");
//        return option;
//    }
//
//    void addOptionShortName(Option option) {
//        option.setShortName("-a");
//    }
//
//    void addOptionLabel(Option option) {
//        option.setLabel("<labelA>");
//    }
//
//    Command getSubcommand() {
//        Command subcommand = new CallableCommand("subtestA");
//        subcommand.setSynopsis("subtestA synopsis");
//        return subcommand;
//    }
//
//    Command getNestedSubcommand() {
//        Command subcommand = new CallableCommand("subtestB");
//        subcommand.setSynopsis("subtestB synopsis");
//        return subcommand;
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__exit_code__success() {
//        int expected = 0;
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_name() {
//        String expected = command.getName();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(expected));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_version__short() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains("-v"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_version__long() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains("--version"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_version__short() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains("-v"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_version__long() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains("--version"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_synopsis() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(commandSynopsis));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_synopsis() {
//        command.setSynopsis(commandSynopsis);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(commandSynopsis));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_description() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(commandDescription));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_description() {
//        command.setDescription(commandDescription);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(commandDescription));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_positional_parameter__label() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(positionalParameter.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_positional_parameter__label() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        command.addPositionalParameter(positionalParameter);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(positionalParameter.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_positional_parameter__synopsis() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_positional_parameter__synopsis() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        command.addPositionalParameter(positionalParameter);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__long_name() {
//        Option option = getOption();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(option.getLongName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__long_name() {
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(option.getLongName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__synopsis() {
//        Option option = getOption();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(option.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__synopsis() {
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(option.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__short_name() {
//        Option option = getOption();
//        addOptionShortName(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(option.getShortName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__short_name() {
//        Option option = getOption();
//        addOptionShortName(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(option.getShortName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_option__label() {
//        Option option = getOption();
//        addOptionLabel(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(option.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_option__label() {
//        Option option = getOption();
//        addOptionLabel(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(option.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_subcommand__name() {
//        Command subcommand = getSubcommand();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(subcommand.getName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_subcommand__name() {
//        Command subcommand = getSubcommand();
//        command.addSubcommand(subcommand);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(subcommand.getName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_no_subcommand__synopsis() {
//        Command subcommand = getSubcommand();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertFalse(out.toString().contains(subcommand.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__short_option__output__command_subcommand__synopsis() {
//        Command subcommand = getSubcommand();
//        command.addSubcommand(subcommand);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-h"});
//
//        assertTrue(out.toString().contains(subcommand.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__exit_code__success() {
//        int expected = 0;
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_name() {
//        String expected = command.getName();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(expected));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_version__short() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains("-v"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_version__long() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains("--version"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_version__short() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains("-v"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_version__long() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains("--version"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_synopsis() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(commandSynopsis));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_synopsis() {
//        command.setSynopsis(commandSynopsis);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(commandSynopsis));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_description() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(commandDescription));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_description() {
//        command.setDescription(commandDescription);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(commandDescription));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_positional_parameter__label() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(positionalParameter.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_positional_parameter__label() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        command.addPositionalParameter(positionalParameter);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(positionalParameter.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_positional_parameter__synopsis() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_positional_parameter__synopsis() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        command.addPositionalParameter(positionalParameter);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__long_name() {
//        Option option = getOption();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(option.getLongName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__long_name() {
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(option.getLongName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__synopsis() {
//        Option option = getOption();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(option.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__synopsis() {
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(option.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__short_name() {
//        Option option = getOption();
//        addOptionShortName(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(option.getShortName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__short_name() {
//        Option option = getOption();
//        addOptionShortName(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(option.getShortName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_option__label() {
//        Option option = getOption();
//        addOptionLabel(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(option.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_option__label() {
//        Option option = getOption();
//        addOptionLabel(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(option.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_subcommand__name() {
//        Command subcommand = getSubcommand();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(subcommand.getName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_subcommand__name() {
//        Command subcommand = getSubcommand();
//        command.addSubcommand(subcommand);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(subcommand.getName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_no_subcommand__synopsis() {
//        Command subcommand = getSubcommand();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains(subcommand.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__long_option__output__command_subcommand__synopsis() {
//        Command subcommand = getSubcommand();
//        command.addSubcommand(subcommand);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertTrue(out.toString().contains(subcommand.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__exit_code__success() {
//        int expected = 0;
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_name() {
//        String expected = command.getName();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(expected));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_version__short() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--help"});
//
//        assertFalse(out.toString().contains("-v"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_version__long() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains("--version"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_version__short() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains("-v"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_version__long() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains("--version"));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_synopsis() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(commandSynopsis));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_synopsis() {
//        command.setSynopsis(commandSynopsis);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(commandSynopsis));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_description() {
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(commandDescription));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_description() {
//        command.setDescription(commandDescription);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(commandDescription));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_positional_parameter__label() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(positionalParameter.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_positional_parameter__label() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        command.addPositionalParameter(positionalParameter);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(positionalParameter.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_positional_parameter__synopsis() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(positionalParameter.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_positional_parameter__synopsis() {
//        PositionalParameter positionalParameter = getPositionalParameter();
//        command.addPositionalParameter(positionalParameter);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(positionalParameter.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__long_name() {
//        Option option = getOption();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(option.getLongName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__long_name() {
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(option.getLongName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__synopsis() {
//        Option option = getOption();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(option.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__synopsis() {
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(option.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__short_name() {
//        Option option = getOption();
//        addOptionShortName(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(option.getShortName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__short_name() {
//        Option option = getOption();
//        addOptionShortName(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(option.getShortName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_option__label() {
//        Option option = getOption();
//        addOptionLabel(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(option.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_option__label() {
//        Option option = getOption();
//        addOptionLabel(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(option.getLabel()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_subcommand__name() {
//        Command subcommand = getSubcommand();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(subcommand.getName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_subcommand__name() {
//        Command subcommand = getSubcommand();
//        command.addSubcommand(subcommand);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(subcommand.getName()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_no_subcommand__synopsis() {
//        Command subcommand = getSubcommand();
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertFalse(out.toString().contains(subcommand.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__usage_help__no_arguments__output__command_subcommand__synopsis() {
//        Command subcommand = getSubcommand();
//        command.addSubcommand(subcommand);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{});
//
//        assertTrue(out.toString().contains(subcommand.getSynopsis()));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__version_help__short_option__exit_code__success() {
//        int expected = 0;
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-v"});
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__version_help__short_option__output() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"-v"});
//        assertTrue(out.toString().contains(commandVersion));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__version_help__long_option__exit_code__success() {
//        int expected = 0;
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--version"});
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__version_help__long_option__output() {
//        command.setVersion(commandVersion);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        int actual = com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{"--version"});
//        assertTrue(out.toString().contains(commandVersion));
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__positional_parameter__base_command() {
//        String expected = "first positional parameter value";
//        command.addPositionalParameter(getPositionalParameter());
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{expected});
//
//        String actual = command.getPositionalParameters().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__long_name__boolean__base_command() {
//        boolean expected = true;
//        Option option = getOption();
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{option.getLongName()});
//
//        boolean actual = (boolean) command.getOptions().get(0).getValue();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__short_name__boolean__base_command() {
//        boolean expected = true;
//        Option option = getOption();
//        addOptionShortName(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{option.getShortName()});
//
//        boolean actual = (boolean) command.getOptions().get(0).getValue();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__long_name__string__base_command() {
//        String expected = "option value";
//        Option option = getOption();
//        addOptionLabel(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{option.getLongName(), expected});
//
//        String actual = command.getOptions().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__short_name__string__base_command() {
//        String expected = "option value";
//        Option option = getOption();
//        addOptionShortName(option);
//        addOptionLabel(option);
//        command.addOption(option);
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{option.getShortName(), expected});
//
//        String actual = command.getOptions().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__positional_parameter__subcommand() {
//        String expected = "first positional parameter value";
//
//        Command subcommand = getSubcommand();
//        subcommand.addPositionalParameter(getPositionalParameter());
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), expected});
//
//        String actual = subcommand.getPositionalParameters().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__long_name__boolean__subcommand() {
//        boolean expected = true;
//
//        Command subcommand = getSubcommand();
//        Option option = getOption();
//        subcommand.addOption(option);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongName()});
//
//        boolean actual = (boolean) subcommand.getOptions().get(0).getValue();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__short_name__boolean__subcommand() {
//        boolean expected = true;
//
//        Command subcommand = getSubcommand();
//        Option option = getOption();
//        addOptionShortName(option);
//        subcommand.addOption(option);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), option.getShortName()});
//
//        boolean actual = (boolean) subcommand.getOptions().get(0).getValue();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__long_name__string__subcommand() {
//        String expected = "option value";
//
//        Command subcommand = getSubcommand();
//        Option option = getOption();
//        addOptionLabel(option);
//        subcommand.addOption(option);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), option.getLongName(), expected});
//
//        String actual = subcommand.getOptions().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__short_name__string__subcommand() {
//        String expected = "option value";
//
//        Command subcommand = getSubcommand();
//        Option option = getOption();
//        addOptionShortName(option);
//        addOptionLabel(option);
//        subcommand.addOption(option);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), option.getShortName(), expected});
//
//        String actual = subcommand.getOptions().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__positional_parameter__nested_subcommand() {
//        String expected = "first positional parameter value";
//
//        Command nestedSubcommand = getNestedSubcommand();
//        nestedSubcommand.addPositionalParameter(getPositionalParameter());
//
//        Command subcommand = getSubcommand();
//        subcommand.addSubcommand(nestedSubcommand);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), expected});
//
//        String actual = nestedSubcommand.getPositionalParameters().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__long_name__boolean__nested_subcommand() {
//        boolean expected = true;
//
//        Command nestedSubcommand = getNestedSubcommand();
//        Option option = getOption();
//        nestedSubcommand.addOption(option);
//
//        Command subcommand = getSubcommand();
//        subcommand.addSubcommand(nestedSubcommand);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getLongName()});
//
//        boolean actual = (boolean) nestedSubcommand.getOptions().get(0).getValue();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__short_name__boolean__nested_subcommand() {
//        boolean expected = true;
//
//        Command nestedSubcommand = getNestedSubcommand();
//        Option option = getOption();
//        addOptionShortName(option);
//        nestedSubcommand.addOption(option);
//
//        Command subcommand = getSubcommand();
//        subcommand.addSubcommand(nestedSubcommand);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getShortName()});
//
//        boolean actual = (boolean) nestedSubcommand.getOptions().get(0).getValue();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__long_name__string__nested_subcommand() {
//        String expected = "option value";
//
//        Command nestedSubcommand = getNestedSubcommand();
//        Option option = getOption();
//        addOptionLabel(option);
//        nestedSubcommand.addOption(option);
//
//        Command subcommand = getSubcommand();
//        subcommand.addSubcommand(nestedSubcommand);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getLongName(), expected});
//
//        String actual = nestedSubcommand.getOptions().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void COMMAND_FRAMEWORK__parse__option__short_name__string__nested_subcommand() {
//        String expected = "option value";
//
//        Command nestedSubcommand = getNestedSubcommand();
//        Option option = getOption();
//        addOptionShortName(option);
//        addOptionLabel(option);
//        nestedSubcommand.addOption(option);
//
//        Command subcommand = getSubcommand();
//        subcommand.addSubcommand(nestedSubcommand);
//        command.addSubcommand(subcommand);
//
//        CommandOrchestrator CommandOrchestrator = new CommandOrchestrator(command);
//        com.blckroot.sdk.command.orchestrator.CommandOrchestrator.execute(new String[]{subcommand.getName(), nestedSubcommand.getName(), option.getShortName(), expected});
//
//        String actual = nestedSubcommand.getOptions().get(0).getValue().toString();
//        assertEquals(expected, actual);
//    }
//}
