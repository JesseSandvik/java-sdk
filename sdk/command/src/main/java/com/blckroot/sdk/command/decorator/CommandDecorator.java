package com.blckroot.sdk.command.decorator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import com.blckroot.sdk.operating.system.OperatingSystem;

import java.util.List;
import java.util.Properties;

public abstract class CommandDecorator implements Command {
    protected final Command command;

    protected CommandDecorator(Command command) {
        this.command = command;
    }

    @Override
    public OperatingSystem getOperatingSystem() {
        return this.command.getOperatingSystem();
    }

    @Override
    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.command.setOperatingSystem(operatingSystem);
    }

    @Override
    public Properties getProperties() {
        return this.command.getProperties();
    }

    @Override
    public void setProperties(Properties properties) {
        this.command.setProperties(properties);
    }

    @Override
    public String getName() {
        return this.command.getName();
    }

    @Override
    public String getVersion() {
        return this.command.getVersion();
    }

    @Override
    public void setVersion(String version) {
        this.command.setVersion(version);
    }

    @Override
    public String getSynopsis() {
        return this.command.getSynopsis();
    }

    @Override
    public void setSynopsis(String synopsis) {
        this.command.setSynopsis(synopsis);
    }

    @Override
    public String getDescription() {
        return this.command.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.command.setDescription(description);
    }

    @Override
    public boolean isExecutesWithoutArguments() {
        return this.command.isExecutesWithoutArguments();
    }

    @Override
    public void setExecutesWithoutArguments(boolean executesWithoutArguments) {
        this.command.setExecutesWithoutArguments(executesWithoutArguments);
    }

    @Override
    public List<PositionalParameter> getPositionalParameters() {
        return this.command.getPositionalParameters();
    }

    @Override
    public void addPositionalParameter(PositionalParameter positionalParameter) {
        this.command.addPositionalParameter(positionalParameter);
    }

    @Override
    public List<Option> getOptions() {
        return this.command.getOptions();
    }

    @Override
    public void addOption(Option option) {
        this.command.addOption(option);
    }

    @Override
    public List<Command> getSubcommands() {
        return this.command.getSubcommands();
    }

    @Override
    public void addSubcommand(Command subcommand) {
        this.command.addSubcommand(subcommand);
    }

    @Override
    public List<String> getArguments() {
        return this.command.getArguments();
    }

    @Override
    public void addArgument(String argument) {
        this.command.addArgument(argument);
    }

    @Override
    public Integer call() throws Exception {
        return this.command.call();
    }
}