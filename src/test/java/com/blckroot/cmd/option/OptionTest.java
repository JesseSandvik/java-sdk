package com.blckroot.cmd.option;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class OptionTest {

    //  ***** Set Option Attributes - WITHOUT SHORTEST NAME ************************************************************

    @Test
    void OPTION_WITHOUT_SHORTEST_NAME_SET_longest_name() {
        String expected = "--test";
        Option option = new Option(expected, "");

        String actual = option.getLongestName();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITHOUT_SHORTEST_NAME_SET_description() {
        String expected = "Test option description.";
        Option option = new Option("--test", expected);

        String actual = option.getDescription();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITHOUT_SHORTEST_NAME_SET_parameter_label() {
        String expected = "labelA";
        Option option = new Option("--test", "", expected);

        String actual = option.getParameterLabel();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITHOUT_SHORTEST_NAME_SET_value() {
        Option option = new Option("--test", "");
        boolean expected = true;
        option.setValue(expected);

        Object actual = option.getValue();
        assertEquals(actual, expected);
    }

    //  ***** Set Option Attributes - WITH SHORTEST NAME ***************************************************************

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_longest_name_at_first_index() {
        String expected = "--test";
        Option option = new Option(new String[]{expected, "-t"}, "");

        String actual = option.getLongestName();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_longest_name_at_last_index() {
        String expected = "--test";
        Option option = new Option(new String[]{"-t", expected}, "");

        String actual = option.getLongestName();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_shortest_name_at_first_index() {
        String expected = "-t";
        Option option = new Option(new String[]{expected, "--test"}, "");

        String actual = option.getShortestName();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_shortest_name_at_last_index() {
        String expected = "-t";
        Option option = new Option(new String[]{"--test", expected}, "");

        String actual = option.getShortestName();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_description() {
        String expected = "Test option description.";
        Option option = new Option(new String[]{"-t", "--test"}, expected);

        String actual = option.getDescription();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_parameter_label() {
        String expected = "labelA";
        Option option = new Option(new String[]{"-t", "--test"}, "", expected);

        String actual = option.getParameterLabel();
        assertEquals(actual, expected);
    }

    @Test
    void OPTION_WITH_SHORTEST_NAME_SET_value() {
        Option option = new Option(new String[]{"-t", "--test"}, "");
        boolean expected = true;
        option.setValue(expected);

        Object actual = option.getValue();
        assertEquals(actual, expected);
    }
}
