package com.blckroot.sdk.logger;

import org.apache.logging.log4j.LogManager;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class Logger implements System.Logger {
    private final String name;
    private final org.apache.logging.log4j.Logger logger;

    public Logger(String name) {
        this.name = name;
        this.logger = LogManager.getLogger(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLoggable(Level level) {
        return switch (level) {
            case OFF -> false;
            case TRACE -> logger.isTraceEnabled();
            case DEBUG -> logger.isDebugEnabled();
            case INFO -> logger.isInfoEnabled();
            case WARNING -> logger.isWarnEnabled();
            case ERROR -> logger.isErrorEnabled();
            default -> true;
        };
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        if (!isLoggable(level)) {
            return;
        }

        switch (level) {
            case TRACE -> logger.trace(msg, thrown);
            case DEBUG -> logger.debug(msg, thrown);
            case WARNING -> logger.warn(msg, thrown);
            case ERROR -> logger.error(msg, thrown);
            default -> logger.info(msg, thrown);
        }
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        if (!isLoggable(level)) {
            return;
        }
        String message = MessageFormat.format(format, params);

        switch (level) {
            case TRACE -> logger.trace(message);
            case DEBUG -> logger.debug(message);
            case WARNING -> logger.warn(message);
            case ERROR -> logger.error(message);
            default -> logger.info(message);
        }
    }
}
