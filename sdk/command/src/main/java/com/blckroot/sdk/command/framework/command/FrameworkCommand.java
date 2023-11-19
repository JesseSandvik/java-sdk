package com.blckroot.sdk.command.framework.command;

import com.blckroot.sdk.command.model.Command;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

public class FrameworkCommand extends Command implements FrameworkBaseCommand, Callable<Integer> {
    private Properties properties;
    private final List<PositionalParameter> positionalParameters = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();
    private final List<FrameworkBaseCommand> frameworkSubcommands = new ArrayList<>();
    private final List<String> arguments = new ArrayList<>();

    public FrameworkCommand(String name) {
        super(name);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public List<PositionalParameter> getPositionalParameters() {
        return positionalParameters;
    }

    public void addPositionalParameter(PositionalParameter positionalParameter) {
        this.positionalParameters.add(positionalParameter);
    }

    @Override
    public List<Option> getOptions() {
        return options;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    @Override
    public List<FrameworkBaseCommand> getFrameworkSubcommands() {
        return frameworkSubcommands;
    }

    @Override
    public void addFrameworkSubcommand(FrameworkBaseCommand subcommand) {
        frameworkSubcommands.add(subcommand);
    }

    @Override
    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public void addArgument(String argument) {
        arguments.add(argument);
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
