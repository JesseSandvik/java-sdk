package com.blckroot.sdk.command.model;

public class Command {
    private final String name;
    private String version;
    private String synopsis;
    private String description;
    private Boolean executesWithoutArguments = false;

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExecutesWithoutArguments() {
        return executesWithoutArguments;
    }

    public void setExecutesWithoutArguments(boolean executesWithoutArguments) {
        this.executesWithoutArguments = executesWithoutArguments;
    }
}