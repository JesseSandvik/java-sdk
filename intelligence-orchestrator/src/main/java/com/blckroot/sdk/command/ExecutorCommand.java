package com.blckroot.sdk.command;

class ExecutorCommand extends Command {
    @Override
    public Integer call() throws Exception {
        System.out.println(" [ EXECUTOR COMMAND ] ");
        return 0;
    }
}
