package com.blckroot.sdk.command.decorator;

import com.blckroot.sdk.command.Command;
import com.blckroot.sdk.file.system.service.FileSystemService;
import com.blckroot.sdk.file.system.validator.FileSystemValidator;
import com.blckroot.sdk.command.CallableCommand;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;

import java.util.Properties;

import static java.lang.System.Logger.Level.*;

public class SetAttributesFromPropertiesFile extends CommandDecorator {
    private static final System.Logger LOGGER = System.getLogger(SetAttributesFromPropertiesFile.class.getName());
    private final String propertiesFileDirectory;

    public SetAttributesFromPropertiesFile(Command command, String propertiesFileDirectory) {
        super(command);
        LOGGER.log(TRACE, SetAttributesFromPropertiesFile.class.getName());
        this.propertiesFileDirectory = propertiesFileDirectory;
        setAttributes(super.command);
    }

    private String getFormattedKey(Integer keyIndex, String key) {
        return keyIndex + "." + key;
    }

    private Boolean propertyIsValid(Command command, String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        return command.getProperties().getProperty(key) != null;
    }

    private Boolean propertiesAreValid(Properties properties) {
        return properties != null && !properties.isEmpty();
    }

    private void setPositionalParameters(Command command, Integer positionalParameterCount) {
        LOGGER.log(DEBUG, "positional parameters found for framework command: " +
                command.getName());
        int index = 1;
        while (index <= positionalParameterCount) {
            final String POSITIONAL_PARAMETER_LABEL_KEY = getFormattedKey(index, "positional.parameter.label");
            final String POSITIONAL_PARAMETER_SYNOPSIS_kEY = getFormattedKey(index, "positional.parameter.synopsis");
            final String POSITIONAL_PARAMETER_VALUE_KEY = getFormattedKey(index, "positional.parameter.value");

            LOGGER.log(DEBUG, "creating new positional parameter...");
            PositionalParameter positionalParameter = new PositionalParameter();
            if (propertyIsValid(command, POSITIONAL_PARAMETER_LABEL_KEY)) {
                LOGGER.log(DEBUG, "setting positional parameter label for framework command: " +
                        command.getName());
                positionalParameter.setLabel(
                        command.getProperties().getProperty(POSITIONAL_PARAMETER_LABEL_KEY));
                LOGGER.log(DEBUG, "positional parameter label: " +
                        positionalParameter.getLabel());
            }

            if (propertyIsValid(command, POSITIONAL_PARAMETER_SYNOPSIS_kEY)) {
                LOGGER.log(DEBUG, "setting positional parameter synopsis for framework command: " +
                        command.getName());
                positionalParameter.setSynopsis(
                        command.getProperties().getProperty(POSITIONAL_PARAMETER_SYNOPSIS_kEY));
                LOGGER.log(DEBUG, "positional parameter description: " +
                        positionalParameter.getSynopsis());
            }

            if (propertyIsValid(command, POSITIONAL_PARAMETER_VALUE_KEY)) {
                LOGGER.log(DEBUG, "setting positional parameter value for framework command: " +
                        command.getName());
                positionalParameter.setValue(
                        command.getProperties().getProperty(POSITIONAL_PARAMETER_VALUE_KEY));
                LOGGER.log(DEBUG, "positional parameter value: " +
                        positionalParameter.getValue());
            }

            command.addPositionalParameter(positionalParameter);
            LOGGER.log(DEBUG, "positional parameter added for framework command: " +
                    command.getName());
            index = index + 1;
        }
    }

    private void setOptions(Command command, Integer optionCount) {
        LOGGER.log(DEBUG, "options found for framework command: " + command.getName());

        int index = 1;
        while (index <= optionCount) {
            final String OPTION_LONG_NAME_KEY = getFormattedKey(index, "option.long.name");
            final String OPTION_SHORT_NAME_KEY = getFormattedKey(index, "option.short.name");
            final String OPTION_SYNOPSIS_KEY = getFormattedKey(index, "option.synopsis");
            final String OPTION_LABEL_KEY = getFormattedKey(index, "option.label");
            final String OPTION_VALUE_KEY = getFormattedKey(index, "option.value");

            Option option = new Option();
            if (propertyIsValid(command, OPTION_LONG_NAME_KEY)) {
                LOGGER.log(DEBUG, "setting option long name for framework command: " +
                        command.getName());
                option.setLongName(command.getProperties().getProperty(OPTION_LONG_NAME_KEY));
                LOGGER.log(DEBUG, "option long name: " + option.getLongName());
            }

            if (propertyIsValid(command, OPTION_SHORT_NAME_KEY)) {
                LOGGER.log(DEBUG, "setting option short name for framework command: " +
                        command.getName());
                option.setShortName(command.getProperties().getProperty(OPTION_SHORT_NAME_KEY));
                LOGGER.log(DEBUG, "option short name: " + option.getShortName());
            }

            if (propertyIsValid(command, OPTION_SYNOPSIS_KEY)) {
                LOGGER.log(DEBUG, "setting option synopsis for framework command: " +
                        command.getName());
                option.setSynopsis(command.getProperties().getProperty(OPTION_SYNOPSIS_KEY));
                LOGGER.log(DEBUG, "option synopsis: " + option.getSynopsis());
            }

            if (propertyIsValid(command, OPTION_LABEL_KEY)) {
                LOGGER.log(DEBUG, "setting option label for framework command: " +
                        command.getName());
                option.setLabel(command.getProperties().getProperty(OPTION_LABEL_KEY));
                LOGGER.log(DEBUG, "option label: " + option.getLabel());
            }

            if (propertyIsValid(command, OPTION_VALUE_KEY)) {
                LOGGER.log(DEBUG, "setting option value for framework command: " +
                        command.getName());
                option.setValue(command.getProperties().getProperty(OPTION_VALUE_KEY));
                LOGGER.log(DEBUG, "option value: " + option.getValue());
            }

            command.addOption(option);
            LOGGER.log(DEBUG, "option added for framework command: " +
                    command.getName());
            index = index + 1;
        }
    }

    private void setSubcommands(Command command, String[] subcommandNames) {
        LOGGER.log(DEBUG, "subcommands found for framework command: " + command.getName());
        for (String subcommandName : subcommandNames) {
            LOGGER.log(DEBUG, "creating subcommand: " + subcommandName);
            Command subcommand =
                    new SetAttributesFromPropertiesFile(new CallableCommand(subcommandName), propertiesFileDirectory);
            command.addSubcommand(subcommand);
        }
    }

    private void setAttributes(Command command) {
        LOGGER.log(DEBUG, "setting attributes for framework command: " + command.getName());
        String propertiesFilePath = propertiesFileDirectory + command.getName() + ".properties";

        FileSystemValidator fileValidator = new FileSystemValidator();
        if (!fileValidator.fileExists(propertiesFilePath)) {
            LOGGER.log(ERROR, "properties file does not exist: " + propertiesFilePath);
//            TODO: improve handling behavior
            return;
        }

        FileSystemService fileSystemService = new FileSystemService();
        LOGGER.log(DEBUG, "setting properties from properties file path: " + propertiesFilePath);
        command.setProperties(fileSystemService.getPropertiesFromFile(propertiesFilePath));

        if (!propertiesAreValid(command.getProperties())) {
            LOGGER.log(ERROR, "failed to read properties from properties file: " + propertiesFilePath);
//            TODO: improve handling behavior
            return;
        }

        final String VERSION_PROPERTY_KEY = "version";
        final String SYNOPSIS_PROPERTY_KEY = "synopsis";
        final String DESCRIPTION_PROPERTY_KEY = "description";
        final String EXECUTES_WITHOUT_ARGUMENTS_KEY = "executes.without.arguments";
        final String POSITIONAL_PARAMETER_COUNT_PROPERTY_KEY = "positional.parameter.count";
        final String OPTION_COUNT_PROPERTY_KEY = "option.count";
        final String SUBCOMMANDS_PROPERTY_KEY = "subcommands";

        if (propertyIsValid(command, VERSION_PROPERTY_KEY)) {
            LOGGER.log(DEBUG, "setting version for framework command: " + command.getName());
            command.setVersion(command.getProperties().getProperty(VERSION_PROPERTY_KEY));
            LOGGER.log(DEBUG, "version for framework command: " + command.getVersion());
        }

        if (propertyIsValid(command, SYNOPSIS_PROPERTY_KEY)) {
            LOGGER.log(DEBUG, "setting synopsis for framework command: " + command.getName());
            command.setSynopsis(command.getProperties().getProperty(SYNOPSIS_PROPERTY_KEY));
            LOGGER.log(DEBUG, "synopsis for framework command: " + command.getSynopsis());
        }

        if (propertyIsValid(command, DESCRIPTION_PROPERTY_KEY)) {
            LOGGER.log(DEBUG, "setting description for framework command: " + command.getName());
            command.setDescription
                    (command.getProperties().getProperty(DESCRIPTION_PROPERTY_KEY));
            LOGGER.log(DEBUG, "description for framework command: " + command.getDescription());
        }

        if (propertyIsValid(command, EXECUTES_WITHOUT_ARGUMENTS_KEY)) {
            LOGGER.log(DEBUG, "setting executes without arguments for framework command: " + command.getName());
            command.setExecutesWithoutArguments(Boolean.parseBoolean(
                    command.getProperties().getProperty(EXECUTES_WITHOUT_ARGUMENTS_KEY)));
            LOGGER.log(DEBUG, "executes without arguments for framework command: " +
                    command.isExecutesWithoutArguments());
        }

        if (propertyIsValid(command, POSITIONAL_PARAMETER_COUNT_PROPERTY_KEY)) {
            setPositionalParameters(command, Integer.valueOf(
                    command.getProperties().getProperty(POSITIONAL_PARAMETER_COUNT_PROPERTY_KEY)));
        }

        if (propertyIsValid(command, OPTION_COUNT_PROPERTY_KEY)) {
            setOptions(command, Integer.valueOf(
                    command.getProperties().getProperty(OPTION_COUNT_PROPERTY_KEY)));
        }

        if (propertyIsValid(command, SUBCOMMANDS_PROPERTY_KEY)) {
            setSubcommands(command,
                    command.getProperties().getProperty(SUBCOMMANDS_PROPERTY_KEY).split(","));
        }
    }

    @Override
    public Integer call() throws Exception {
        LOGGER.log(TRACE, "execute call method: " + SetAttributesFromPropertiesFile.class.getName());
        return super.call();
    }
}