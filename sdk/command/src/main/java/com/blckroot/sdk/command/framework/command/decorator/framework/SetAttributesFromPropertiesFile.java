package com.blckroot.sdk.command.framework.command.decorator.framework;

import com.blckroot.sdk.file.system.service.FileSystemService;
import com.blckroot.sdk.file.system.validator.FileSystemValidator;
import com.blckroot.sdk.command.framework.command.FrameworkBaseCommand;
import com.blckroot.sdk.command.framework.command.FrameworkCommand;
import com.blckroot.sdk.command.framework.command.decorator.FrameworkCommandDecorator;
import com.blckroot.sdk.command.model.Option;
import com.blckroot.sdk.command.model.PositionalParameter;

import java.util.Properties;

import static java.lang.System.Logger.Level.*;

public class SetAttributesFromPropertiesFile extends FrameworkCommandDecorator {
    private static final System.Logger LOGGER = System.getLogger(SetAttributesFromPropertiesFile.class.getName());
    private final String propertiesFileDirectory;

    public SetAttributesFromPropertiesFile(FrameworkBaseCommand frameworkCommand, String propertiesFileDirectory) {
        super(frameworkCommand);
        LOGGER.log(TRACE, SetAttributesFromPropertiesFile.class.getName());
        this.propertiesFileDirectory = propertiesFileDirectory;
        setAttributes(super.frameworkCommand);
    }

    private String getFormattedKey(Integer keyIndex, String key) {
        return keyIndex + "." + key;
    }

    private Boolean propertyIsValid(FrameworkBaseCommand frameworkBaseCommand, String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        return frameworkBaseCommand.getProperties().getProperty(key) != null;
    }

    private Boolean propertiesAreValid(Properties properties) {
        return properties != null && !properties.isEmpty();
    }

    private void setPositionalParameters(FrameworkBaseCommand frameworkBaseCommand, Integer positionalParameterCount) {
        LOGGER.log(DEBUG, "positional parameters found for framework command: " +
                frameworkBaseCommand.getName());
        int index = 1;
        while (index <= positionalParameterCount) {
            final String POSITIONAL_PARAMETER_LABEL_KEY = getFormattedKey(index, "positional.parameter.label");
            final String POSITIONAL_PARAMETER_SYNOPSIS_kEY = getFormattedKey(index, "positional.parameter.synopsis");
            final String POSITIONAL_PARAMETER_VALUE_KEY = getFormattedKey(index, "positional.parameter.value");

            LOGGER.log(DEBUG, "creating new positional parameter...");
            PositionalParameter positionalParameter = new PositionalParameter();
            if (propertyIsValid(frameworkBaseCommand, POSITIONAL_PARAMETER_LABEL_KEY)) {
                LOGGER.log(DEBUG, "setting positional parameter label for framework command: " +
                        frameworkBaseCommand.getName());
                positionalParameter.setLabel(
                        frameworkBaseCommand.getProperties().getProperty(POSITIONAL_PARAMETER_LABEL_KEY));
                LOGGER.log(DEBUG, "positional parameter label: " +
                        positionalParameter.getLabel());
            }

            if (propertyIsValid(frameworkBaseCommand, POSITIONAL_PARAMETER_SYNOPSIS_kEY)) {
                LOGGER.log(DEBUG, "setting positional parameter synopsis for framework command: " +
                        frameworkBaseCommand.getName());
                positionalParameter.setSynopsis(
                        frameworkBaseCommand.getProperties().getProperty(POSITIONAL_PARAMETER_SYNOPSIS_kEY));
                LOGGER.log(DEBUG, "positional parameter description: " +
                        positionalParameter.getSynopsis());
            }

            if (propertyIsValid(frameworkBaseCommand, POSITIONAL_PARAMETER_VALUE_KEY)) {
                LOGGER.log(DEBUG, "setting positional parameter value for framework command: " +
                        frameworkBaseCommand.getName());
                positionalParameter.setValue(
                        frameworkBaseCommand.getProperties().getProperty(POSITIONAL_PARAMETER_VALUE_KEY));
                LOGGER.log(DEBUG, "positional parameter value: " +
                        positionalParameter.getValue());
            }

            frameworkBaseCommand.addPositionalParameter(positionalParameter);
            LOGGER.log(DEBUG, "positional parameter added for framework command: " +
                    frameworkBaseCommand.getName());
            index = index + 1;
        }
    }

    private void setOptions(FrameworkBaseCommand frameworkBaseCommand, Integer optionCount) {
        LOGGER.log(DEBUG, "options found for framework command: " + frameworkBaseCommand.getName());

        int index = 1;
        while (index <= optionCount) {
            final String OPTION_LONG_NAME_KEY = getFormattedKey(index, "option.long.name");
            final String OPTION_SHORT_NAME_KEY = getFormattedKey(index, "option.short.name");
            final String OPTION_SYNOPSIS_KEY = getFormattedKey(index, "option.synopsis");
            final String OPTION_LABEL_KEY = getFormattedKey(index, "option.label");
            final String OPTION_VALUE_KEY = getFormattedKey(index, "option.value");

            Option option = new Option();
            if (propertyIsValid(frameworkBaseCommand, OPTION_LONG_NAME_KEY)) {
                LOGGER.log(DEBUG, "setting option long name for framework command: " +
                        frameworkBaseCommand.getName());
                option.setLongName(frameworkBaseCommand.getProperties().getProperty(OPTION_LONG_NAME_KEY));
                LOGGER.log(DEBUG, "option long name: " + option.getLongName());
            }

            if (propertyIsValid(frameworkBaseCommand, OPTION_SHORT_NAME_KEY)) {
                LOGGER.log(DEBUG, "setting option short name for framework command: " +
                        frameworkBaseCommand.getName());
                option.setShortName(frameworkBaseCommand.getProperties().getProperty(OPTION_SHORT_NAME_KEY));
                LOGGER.log(DEBUG, "option short name: " + option.getShortName());
            }

            if (propertyIsValid(frameworkBaseCommand, OPTION_SYNOPSIS_KEY)) {
                LOGGER.log(DEBUG, "setting option synopsis for framework command: " +
                        frameworkBaseCommand.getName());
                option.setSynopsis(frameworkBaseCommand.getProperties().getProperty(OPTION_SYNOPSIS_KEY));
                LOGGER.log(DEBUG, "option synopsis: " + option.getSynopsis());
            }

            if (propertyIsValid(frameworkBaseCommand, OPTION_LABEL_KEY)) {
                LOGGER.log(DEBUG, "setting option label for framework command: " +
                        frameworkBaseCommand.getName());
                option.setLabel(frameworkBaseCommand.getProperties().getProperty(OPTION_LABEL_KEY));
                LOGGER.log(DEBUG, "option label: " + option.getLabel());
            }

            if (propertyIsValid(frameworkBaseCommand, OPTION_VALUE_KEY)) {
                LOGGER.log(DEBUG, "setting option value for framework command: " +
                        frameworkBaseCommand.getName());
                option.setValue(frameworkBaseCommand.getProperties().getProperty(OPTION_VALUE_KEY));
                LOGGER.log(DEBUG, "option value: " + option.getValue());
            }

            frameworkBaseCommand.addOption(option);
            LOGGER.log(DEBUG, "option added for framework command: " +
                    frameworkBaseCommand.getName());
            index = index + 1;
        }
    }

    private void setSubcommands(FrameworkBaseCommand frameworkBaseCommand, String[] subcommandNames) {
        LOGGER.log(DEBUG, "subcommands found for framework command: " + frameworkBaseCommand.getName());
        for (String subcommandName : subcommandNames) {
            LOGGER.log(DEBUG, "creating subcommand: " + subcommandName);
            FrameworkBaseCommand subcommand =
                    new SetAttributesFromPropertiesFile(new FrameworkCommand(subcommandName), propertiesFileDirectory);
            frameworkBaseCommand.addFrameworkSubcommand(subcommand);
        }
    }

    private void setAttributes(FrameworkBaseCommand frameworkBaseCommand) {
        LOGGER.log(DEBUG, "setting attributes for framework command: " + frameworkBaseCommand.getName());
        String propertiesFilePath = propertiesFileDirectory + frameworkBaseCommand.getName() + ".properties";

        FileSystemValidator fileValidator = new FileSystemValidator();
        if (!fileValidator.fileExists(propertiesFilePath)) {
            LOGGER.log(ERROR, "properties file does not exist: " + propertiesFilePath);
//            TODO: improve handling behavior
            return;
        }

        FileSystemService fileSystemService = new FileSystemService();
        LOGGER.log(DEBUG, "setting properties from properties file path: " + propertiesFilePath);
        frameworkBaseCommand.setProperties(fileSystemService.getPropertiesFromFile(propertiesFilePath));

        if (!propertiesAreValid(frameworkBaseCommand.getProperties())) {
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

        if (propertyIsValid(frameworkBaseCommand, VERSION_PROPERTY_KEY)) {
            LOGGER.log(DEBUG, "setting version for framework command: " + frameworkBaseCommand.getName());
            frameworkBaseCommand.setVersion(frameworkBaseCommand.getProperties().getProperty(VERSION_PROPERTY_KEY));
            LOGGER.log(DEBUG, "version for framework command: " + frameworkBaseCommand.getVersion());
        }

        if (propertyIsValid(frameworkBaseCommand, SYNOPSIS_PROPERTY_KEY)) {
            LOGGER.log(DEBUG, "setting synopsis for framework command: " + frameworkBaseCommand.getName());
            frameworkBaseCommand.setSynopsis(frameworkBaseCommand.getProperties().getProperty(SYNOPSIS_PROPERTY_KEY));
            LOGGER.log(DEBUG, "synopsis for framework command: " + frameworkBaseCommand.getSynopsis());
        }

        if (propertyIsValid(frameworkBaseCommand, DESCRIPTION_PROPERTY_KEY)) {
            LOGGER.log(DEBUG, "setting description for framework command: " + frameworkBaseCommand.getName());
            frameworkBaseCommand.setDescription
                    (frameworkBaseCommand.getProperties().getProperty(DESCRIPTION_PROPERTY_KEY));
            LOGGER.log(DEBUG, "description for framework command: " + frameworkBaseCommand.getDescription());
        }

        if (propertyIsValid(frameworkBaseCommand, EXECUTES_WITHOUT_ARGUMENTS_KEY)) {
            LOGGER.log(DEBUG, "setting executes without arguments for framework command: " + frameworkBaseCommand.getName());
            frameworkBaseCommand.setExecutesWithoutArguments(Boolean.parseBoolean(
                    frameworkBaseCommand.getProperties().getProperty(EXECUTES_WITHOUT_ARGUMENTS_KEY)));
            LOGGER.log(DEBUG, "executes without arguments for framework command: " +
                    frameworkBaseCommand.isExecutesWithoutArguments());
        }

        if (propertyIsValid(frameworkBaseCommand, POSITIONAL_PARAMETER_COUNT_PROPERTY_KEY)) {
            setPositionalParameters(frameworkBaseCommand, Integer.valueOf(
                    frameworkBaseCommand.getProperties().getProperty(POSITIONAL_PARAMETER_COUNT_PROPERTY_KEY)));
        }

        if (propertyIsValid(frameworkBaseCommand, OPTION_COUNT_PROPERTY_KEY)) {
            setOptions(frameworkBaseCommand, Integer.valueOf(
                    frameworkBaseCommand.getProperties().getProperty(OPTION_COUNT_PROPERTY_KEY)));
        }

        if (propertyIsValid(frameworkBaseCommand, SUBCOMMANDS_PROPERTY_KEY)) {
            setSubcommands(frameworkBaseCommand,
                    frameworkBaseCommand.getProperties().getProperty(SUBCOMMANDS_PROPERTY_KEY).split(","));
        }
    }

    @Override
    public Integer call() throws Exception {
        LOGGER.log(TRACE, "execute call method: " + SetAttributesFromPropertiesFile.class.getName());
        return super.call();
    }
}