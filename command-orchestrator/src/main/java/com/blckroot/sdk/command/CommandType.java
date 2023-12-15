package com.blckroot.sdk.command;

enum CommandType {
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
