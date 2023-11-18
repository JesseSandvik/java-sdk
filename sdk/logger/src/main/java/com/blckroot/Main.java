package com.blckroot;

import com.blckroot.sdk.logger.configurator.LoggerConfigurator;

import static java.lang.System.Logger.Level.*;

public class Main {
    public static void main(String[] args) {
        LoggerConfigurator loggerConfigurator = new LoggerConfigurator();
        loggerConfigurator.setLevel(TRACE);
        loggerConfigurator.initializeRootLogger();
        System.Logger logger = System.getLogger(Main.class.getName());
        logger.log(ERROR, "ERROR LOG TEST");
        logger.log(WARNING, "WARNING LOG TEST");
        logger.log(DEBUG, "DEBUG LOG TEST");
        logger.log(INFO, "INFO LOG TEST");
        logger.log(TRACE, "TRACE LOG TEST");
    }
}