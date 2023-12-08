package com.blckroot.sdk.command;

enum CommandType {
    Executable {
        @Override
        public Command create(String name) {
            Command command = new ExecutableCommand();
            command.setName(name);
            return command;
        }
    },
    Executor {
        @Override
        public Command create(String name) {
            Command command = new ExecutorCommand();
            command.setName(name);
            return command;
        }
    };

    public abstract Command create(String name);
}
