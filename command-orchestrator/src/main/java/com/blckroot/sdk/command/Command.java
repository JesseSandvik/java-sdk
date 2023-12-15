package com.blckroot.sdk.command;

import com.blckroot.sdk.command.model.CommandAttributes;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class Command extends CommandAttributes implements ICommand {
    private Properties properties;
    private final List<PositionalParameter> positionalParameters = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();
    private final List<Command> subcommands = new ArrayList<>();

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public List<PositionalParameter> getPositionalParameters() {
        return positionalParameters;
    }

    @Override
    public void addPositionalParameter(PositionalParameter positionalParameter) {
        this.positionalParameters.add(positionalParameter);
    }

    @Override
    public List<Option> getOptions() {
        return options;
    }

    @Override
    public void addOption(Option option) {
        this.options.add(option);
    }

    @Override
    public List<Command> getSubcommands() {
        return subcommands;
    }

    @Override
    public void addSubcommand(Command subcommand) {
        this.subcommands.add(subcommand);
    }
}
