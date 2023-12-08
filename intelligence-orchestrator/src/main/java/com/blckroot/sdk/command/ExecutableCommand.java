package com.blckroot.sdk.command;

class ExecutableCommand extends Command {
    @Override
    public Integer call() throws Exception {
        System.out.println(" [ EXECUTABLE COMMAND ] ");
        return 0;
    }
}
