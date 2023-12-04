package com.blckroot.sdk.command.orchestrator.state;

import com.blckroot.sdk.command.decorator.ExecuteCommandAsPlugin;
import com.blckroot.sdk.command.decorator.SetAttributesFromPropertiesFile;
import com.blckroot.sdk.command.orchestrator.model.CommandExecution;
import com.blckroot.sdk.command.orchestrator.state.picocli.ParseCommandLineArguments;
import com.blckroot.sdk.operating.system.OperatingSystem;

import static java.lang.System.Logger.Level.*;

public enum CommandExecutionState {
    START {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + SET_COMMAND_ATTRIBUTES_FROM_PROPERTIES_FILES.name());
            return SET_COMMAND_ATTRIBUTES_FROM_PROPERTIES_FILES;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + START.name());
        }
    },
    SET_COMMAND_ATTRIBUTES_FROM_PROPERTIES_FILES {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + PARSE_COMMAND_LINE_ARGUMENTS.name());
            return PARSE_COMMAND_LINE_ARGUMENTS;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            operatingSystem = new OperatingSystem();
            String commandPropertiesDir = System.getProperty("command.orchestrator.config.dir");

            if (commandPropertiesDir == null) {
                String userHomeDir = operatingSystem.getOperatingSystemProperties().getProperty("user.home");
                String fileSeparator = operatingSystem.getOperatingSystemProperties().getProperty("file.separator");
                String appResourcesDir = userHomeDir + fileSeparator + ".blckroot" + fileSeparator;
                commandPropertiesDir = appResourcesDir + "commands" + fileSeparator + "properties" + fileSeparator;
            }
            commandExecution.setCommand(
                    new SetAttributesFromPropertiesFile(commandExecution.getCommand(), commandPropertiesDir));
        }
    },
    PARSE_COMMAND_LINE_ARGUMENTS {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + SET_OPERATING_SYSTEM.name());
            return SET_OPERATING_SYSTEM;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + PARSE_COMMAND_LINE_ARGUMENTS.name());
            new ParseCommandLineArguments().run(commandExecution);
        }
    },
    SET_OPERATING_SYSTEM {
        @Override
        public CommandExecutionState transitionToNextState() {
            return SET_COMMAND_TO_EXECUTE_PLUGIN;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            commandExecution.getCommand().setOperatingSystem(operatingSystem);
        }
    },
    SET_COMMAND_TO_EXECUTE_PLUGIN {
        @Override
        public CommandExecutionState transitionToNextState() {
            return CALL_COMMAND;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            commandExecution.setCommand(new ExecuteCommandAsPlugin(commandExecution.getCommand()));
        }
    },
    CALL_COMMAND {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + EXIT.name());
            return EXIT;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + CALL_COMMAND.name());
            try {
                int exitCode = commandExecution.getCommand().call();
                commandExecution.setExitCode(exitCode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    },
    EXIT {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "command execution state: " + EXIT.name() + " reached.");
            return null;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + EXIT.name());
            System.exit(commandExecution.getExitCode());
        }
    };

    private static final System.Logger LOGGER = System.getLogger(CommandExecutionState.class.getName());
    private static OperatingSystem operatingSystem;

    public abstract CommandExecutionState transitionToNextState();
    public abstract void execute(CommandExecution commandExecution) throws Exception;
}
