package com.blckroot.cmd.option;

import com.blckroot.cmd.string.StringService;

public class Option implements OptionContract {
    private final String longestName;
    private final String shortestName;
    private final String description;
    private final String parameterLabel;
    private Object value;

    public Option(String[] names, String description, String parameterLabel) {
        StringService stringService = new StringService();
        this.longestName = stringService.getLongestStringInArray(names);
        this.shortestName = stringService.getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = parameterLabel;
    }

    public Option(String[] names, String description) {
        StringService stringService = new StringService();
        this.longestName = stringService.getLongestStringInArray(names);
        this.shortestName = stringService.getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = null;
    }

    public Option(String name, String description, String parameterLabel) {
        this.longestName = name;
        this.shortestName = null;
        this.description = description;
        this.parameterLabel = parameterLabel;
    }

    public Option(String name, String description) {
        this.longestName = name;
        this.shortestName = null;
        this.description = description;
        this.parameterLabel = null;
    }

    @Override
    public String getLongestName() {
        return longestName;
    }

    @Override
    public String getShortestName() {
        return shortestName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getParameterLabel() {
        return parameterLabel;
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
