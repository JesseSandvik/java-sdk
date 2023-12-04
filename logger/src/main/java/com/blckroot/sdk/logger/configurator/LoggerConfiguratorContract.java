package com.blckroot.sdk.logger.configurator;

interface LoggerConfiguratorContract {
    void setLevel(System.Logger.Level level);
    void initializeRootLogger();
}
