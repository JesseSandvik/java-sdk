package com.blckroot.sdk.command.framework;

interface CommandFrameworkContract {
    Integer execute(String[] arguments) throws Exception;
}
