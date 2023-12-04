package com.blckroot.commands.root;

import com.blckroot.sdk.command.CallableCommand;

public class RootCommand extends CallableCommand {
    public RootCommand(String name) {
        super(name);
    }

    @Override
    public Integer call() {
        return 0;
    }
}
