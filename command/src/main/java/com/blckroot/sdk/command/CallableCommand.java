package com.blckroot.sdk.command;

import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;
import com.blckroot.sdk.operating.system.OperatingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

public class CallableCommand extends com.blckroot.sdk.command.model.Command implements Command, Callable<Integer> {
    private OperatingSystem operatingSystem;
    private Properties properties;
    private final List<PositionalParameter> positionalParameters = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();
    private final List<Command> subcommands = new ArrayList<>();
    private final List<String> arguments = new ArrayList<>();

    public CallableCommand(String name) {
        super(name);
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
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
    public List<Command> getSubcommands() {
        return subcommands;
    }

    @Override
    public void addSubcommand(Command subcommand) {
        subcommands.add(subcommand);
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
