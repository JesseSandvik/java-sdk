package com.blckroot.sdk.command.orchestrator.state;

import com.blckroot.sdk.command.orchestrator.model.CommandExecution;

import static java.lang.System.Logger.Level.*;

public enum CommandExecutionState {
    BUILD_COMMAND_LINE {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + PARSE_ARGUMENTS.name());
            return PARSE_ARGUMENTS;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + BUILD_COMMAND_LINE.name());
            BuildCommandLine.run(commandExecution);
        }
    },
    PARSE_ARGUMENTS {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + CALL_COMMAND.name());
            return CALL_COMMAND;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + PARSE_ARGUMENTS.name());
            ParseArguments.run(commandExecution);
        }
    },
    CALL_COMMAND {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + EXIT.name());
            return EXIT;
        }

        @Override
        public void execute(CommandExecution commandExecution) throws Exception {
            LOGGER.log(DEBUG, "command execution state: " + CALL_COMMAND.name());
            int exitCode = commandExecution.getCommand().call();
            commandExecution.setExitCode(exitCode);
        }
    },
    EXIT {
        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.log(DEBUG, "transitioning to command execution state: " + null);
            return null;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            LOGGER.log(DEBUG, "command execution state: " + EXIT.name());
            System.exit(commandExecution.getExitCode());
        }
    };

    private static final System.Logger LOGGER = System.getLogger(CommandExecutionState.class.getName());

    public abstract CommandExecutionState transitionToNextState();
    public abstract void execute(CommandExecution commandOrchestrator) throws Exception;
}
