package com.blckroot.sdk.command.execution;

import picocli.CommandLine;

enum CommandExecutionState {
    INITIAL {
        @Override
        public CommandExecutionState transitionToNextState() {
            return PARSE_COMMAND_LINE;
        }

        @Override
        public void execute(CommandExecution commandExecution) {}
    },
    PARSE_COMMAND_LINE {
        @Override
        public CommandExecutionState transitionToNextState() {
            return CALL_COMMAND;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            CommandLine commandLine = PicocliCommandLineGenerator.generate(commandExecution.getRootCommand());
            PicocliCommandLineParser.parse(commandLine, commandExecution);
        }
    },
    CALL_COMMAND {
        @Override
        public CommandExecutionState transitionToNextState() {
            return FINISHED;
        }

        @Override
        public void execute(CommandExecution commandExecution) {
            if (commandExecution.getCalledCommand() != null) {
                try {
                    int exitCode = commandExecution.getCalledCommand().call();
                    commandExecution.setExitCode(exitCode);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    },
    FINISHED {
        @Override
        public CommandExecutionState transitionToNextState() {
            return this;
        }

        @Override
        public void execute(CommandExecution commandExecution) {}
    };

    public abstract CommandExecutionState transitionToNextState();

    public abstract void execute(CommandExecution commandExecution);
}
