package com.blckroot.cmd.positionalParameter;

public class PositionalParameter implements PositionalParameterContract {
    private final String label;
    private final String description;
    private Object value;

    public PositionalParameter(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }
}
