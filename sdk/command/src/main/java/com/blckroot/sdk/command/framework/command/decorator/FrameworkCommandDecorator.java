package com.blckroot.sdk.command.framework.command.decorator;

import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;

import java.util.List;
import java.util.Properties;

public abstract class FrameworkCommandDecorator implements FrameworkBaseCommand {
    protected final FrameworkBaseCommand frameworkCommand;

    protected FrameworkCommandDecorator(FrameworkBaseCommand frameworkCommand) {
        this.frameworkCommand = frameworkCommand;
    }

    @Override
    public Properties getProperties() {
        return this.frameworkCommand.getProperties();
    }

    @Override
    public void setProperties(Properties properties) {
        this.frameworkCommand.setProperties(properties);
    }

    @Override
    public String getName() {
        return this.frameworkCommand.getName();
    }

    @Override
    public String getVersion() {
        return this.frameworkCommand.getVersion();
    }

    @Override
    public void setVersion(String version) {
        this.frameworkCommand.setVersion(version);
    }

    @Override
    public String getSynopsis() {
        return this.frameworkCommand.getSynopsis();
    }

    @Override
    public void setSynopsis(String synopsis) {
        this.frameworkCommand.setSynopsis(synopsis);
    }

    @Override
    public String getDescription() {
        return this.frameworkCommand.getDescription();
    }

    @Override
    public void setDescription(String description) {
        this.frameworkCommand.setDescription(description);
    }

    @Override
    public boolean isExecutesWithoutArguments() {
        return this.frameworkCommand.isExecutesWithoutArguments();
    }

    @Override
    public void setExecutesWithoutArguments(boolean executesWithoutArguments) {
        this.frameworkCommand.setExecutesWithoutArguments(executesWithoutArguments);
    }

    @Override
    public List<PositionalParameter> getPositionalParameters() {
        return this.frameworkCommand.getPositionalParameters();
    }

    @Override
    public void addPositionalParameter(PositionalParameter positionalParameter) {
        this.frameworkCommand.addPositionalParameter(positionalParameter);
    }

    @Override
    public List<Option> getOptions() {
        return this.frameworkCommand.getOptions();
    }

    @Override
    public void addOption(Option option) {
        this.frameworkCommand.addOption(option);
    }

    @Override
    public List<FrameworkBaseCommand> getFrameworkSubcommands() {
        return this.frameworkCommand.getFrameworkSubcommands();
    }

    @Override
    public void addFrameworkSubcommand(FrameworkBaseCommand subcommand) {
        this.frameworkCommand.getFrameworkSubcommands().add(subcommand);
    }

    @Override
    public List<String> getArguments() {
        return this.frameworkCommand.getArguments();
    }

    @Override
    public void addArgument(String argument) {
        this.frameworkCommand.addArgument(argument);
    }

    @Override
    public Integer call() throws Exception {
        return this.frameworkCommand.call();
    }
}