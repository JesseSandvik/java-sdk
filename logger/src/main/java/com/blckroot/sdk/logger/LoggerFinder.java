package com.blckroot.sdk.logger;

public class LoggerFinder extends System.LoggerFinder {
    @Override
    public System.Logger getLogger(String name, Module module) {
        return new Logger(name);
    }
}
