package com.blckroot.sdk.command.framework.command;

import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;

import java.util.List;
import java.util.Properties;

public interface FrameworkBaseCommand {
    Properties getProperties();
    void setProperties(Properties properties);
    String getName();
    String getVersion();
    void setVersion(String version);
    String getSynopsis();
    void setSynopsis(String synopsis);
    String getDescription();
    void setDescription(String description);
    boolean isExecutesWithoutArguments();
    void setExecutesWithoutArguments(boolean executesWithoutArguments);
    List<PositionalParameter> getPositionalParameters();
    void addPositionalParameter(PositionalParameter positionalParameter);
    List<Option> getOptions();
    void addOption(Option option);
    List<FrameworkBaseCommand> getFrameworkSubcommands();
    void addFrameworkSubcommand(FrameworkBaseCommand subcommand);
    List<String> getArguments();
    void addArgument(String argument);
    Integer call() throws Exception;
}
