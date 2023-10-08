package com.blckroot.cmd.positionalParameter;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class PositionalParameterTest {

    //  ***** Set Positional Parameter Attributes **********************************************************************

    @Test
    void POSITIONAL_PARAMETER_SET_label() {
        String expected = "test";
        PositionalParameter positionalParameter = new PositionalParameter(expected, "");

        String actual = positionalParameter.getLabel();
        assertEquals(actual, expected);
    }

    @Test
    void POSITIONAL_PARAMETER_SET_description() {
        String expected = "Test description.";
        PositionalParameter positionalParameter = new PositionalParameter("", expected);

        String actual = positionalParameter.getDescription();
        assertEquals(actual, expected);
    }

    @Test
    void POSITIONAL_PARAMETER_SET_value() {
        PositionalParameter positionalParameter = new PositionalParameter("", "");
        boolean expected = true;
        positionalParameter.setValue(expected);

        Object actual = positionalParameter.getValue();
        assertEquals(actual, expected);
    }
}
