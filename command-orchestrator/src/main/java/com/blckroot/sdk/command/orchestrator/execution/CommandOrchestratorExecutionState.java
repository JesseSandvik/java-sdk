package com.blckroot.sdk.command.orchestrator.execution;

import com.blckroot.sdk.command.properties.SetCommandProperties;
import picocli.CommandLine;

public enum CommandOrchestratorExecutionState {
    INITIAL {
        @Override
        public CommandOrchestratorExecutionState transitionToNextState() {
            return SET_COMMAND_PROPERTIES;
        }

        @Override
        public Integer execute(CommandOrchestratorExecution commandExecution) {
            return 0;
        }
    },
    SET_COMMAND_PROPERTIES {
        @Override
        public CommandOrchestratorExecutionState transitionToNextState() {
            return PARSE_COMMAND_LINE;
        }

        @Override
        public Integer execute(CommandOrchestratorExecution commandExecution) {
            return SetCommandProperties.setProperties(commandExecution.getRootCommand());
        }
    },
    PARSE_COMMAND_LINE {
        @Override
        public CommandOrchestratorExecutionState transitionToNextState() {
            return CALL_COMMAND;
        }

        @Override
        public Integer execute(CommandOrchestratorExecution commandExecution) {
            CommandLine commandLine = PicocliCommandLineGenerator.generate(commandExecution.getRootCommand());
            PicocliCommandLineParser.parse(commandLine, commandExecution);
//            TODO: update parsed to return int
            return 0;
        }
    },
    CALL_COMMAND {
        @Override
        public CommandOrchestratorExecutionState transitionToNextState() {
            return FINISHED;
        }

        @Override
        public Integer execute(CommandOrchestratorExecution commandExecution) {
            if (commandExecution.getCalledCommand() != null) {
                try {
                    return commandExecution.getCalledCommand().call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return 0;
        }
    },
    FINISHED {
        @Override
        public CommandOrchestratorExecutionState transitionToNextState() {
            return this;
        }

        @Override
        public Integer execute(CommandOrchestratorExecution commandExecution) {
            return 0;
        }
    };

    public abstract CommandOrchestratorExecutionState transitionToNextState();

    public abstract Integer execute(CommandOrchestratorExecution commandExecution);
}
