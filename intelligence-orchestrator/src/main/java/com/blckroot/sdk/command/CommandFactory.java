package com.blckroot.sdk.command;

public class CommandFactory {
    public static Command create(String name) {
        return CommandType.Executable.create(name);
    }

    public static Command create(String name, String type) {
        if (type.equalsIgnoreCase(CommandType.Executor.name())) {
            return CommandType.Executor.create(name);
        }
        return CommandType.Executable.create(name);
    }
}
