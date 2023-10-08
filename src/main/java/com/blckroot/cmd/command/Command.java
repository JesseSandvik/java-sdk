package com.blckroot.cmd.command;

import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Command implements CommandContract, Callable<Integer> {
    private final String name;
    private String version;
    private String usageDescriptionSynopsis;
    private String usageDescriptionFull;
    private Boolean executesWithoutArguments = false;
    private final List<PositionalParameter> positionalParameters = new ArrayList<>();
    private final List<Option> options = new ArrayList<>();
    private final List<Command> subcommands = new ArrayList<>();

    public Command(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getUsageDescriptionSynopsis() {
        return this.usageDescriptionSynopsis;
    }

    @Override
    public void setUsageDescriptionSynopsis(String usageDescriptionSynopsis) {
        this.usageDescriptionSynopsis = usageDescriptionSynopsis;
    }

    @Override
    public String getUsageDescriptionFull() {
        return this.usageDescriptionFull;
    }

    @Override
    public void setUsageDescriptionFull(String usageDescriptionFull) {
        this.usageDescriptionFull = usageDescriptionFull;
    }

    @Override
    public Boolean executesWithoutArguments() {
        return this.executesWithoutArguments;
    }

    @Override
    public void executesWithoutArguments(Boolean executesWithoutArguments) {
        this.executesWithoutArguments = executesWithoutArguments;
    }

    @Override
    public List<PositionalParameter> getPositionalParameters() {
        return this.positionalParameters;
    }

    @Override
    public void addPositionalParameter(PositionalParameter positionalParameter) {
        this.positionalParameters.add(positionalParameter);
    }

    @Override
    public List<Option> getOptions() {
        return this.options;
    }

    @Override
    public void addOption(Option option) {
        this.options.add(option);
    }

    @Override
    public List<Command> getSubcommands() {
        return this.subcommands;
    }

    @Override
    public void addSubcommand(Command subcommand) {
        this.subcommands.add(subcommand);
    }

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
