package com.blckroot.cmd.positionalParameter;

interface PositionalParameterContract {
    String getLabel();
    String getDescription();
    Object getValue();
    void setValue(Object value);
}
