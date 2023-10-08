package com.blckroot.cmd.command;

import com.blckroot.cmd.option.Option;
import com.blckroot.cmd.positionalParameter.PositionalParameter;

import java.util.List;

interface CommandContract {
    String getName();
    String getVersion();
    void setVersion(String version);
    String getUsageDescriptionSynopsis();
    void setUsageDescriptionSynopsis(String usageDescriptionSynopsis);
    String getUsageDescriptionFull();
    void setUsageDescriptionFull(String usageDescriptionFull);
    Boolean executesWithoutArguments();
    void executesWithoutArguments(Boolean executesWithoutArguments);
    List<PositionalParameter> getPositionalParameters();
    void addPositionalParameter(PositionalParameter positionalParameter);
    List<Option> getOptions();
    void addOption(Option option);
    List<Command> getSubcommands();
    void addSubcommand(Command subcommand);
    Integer call() throws Exception;
}
