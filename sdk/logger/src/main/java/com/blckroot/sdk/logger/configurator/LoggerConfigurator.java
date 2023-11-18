package com.blckroot.sdk.logger.configurator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import static org.apache.logging.log4j.core.config.Configurator.initialize;

public class LoggerConfigurator implements LoggerConfiguratorContract {
    private final ConfigurationBuilder<BuiltConfiguration> builder;
    private final RootLoggerComponentBuilder rootLogger;

    public LoggerConfigurator() {
        this.builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        this.rootLogger = builder.newRootLogger();
    }

    private LayoutComponentBuilder getStandardPatternLayout(ConfigurationBuilder<BuiltConfiguration> builder) {
        LayoutComponentBuilder standardPatternLayout = builder.newLayout("PatternLayout");
        standardPatternLayout.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");
        return standardPatternLayout;
    }

    private AppenderComponentBuilder getConsoleAppender(ConfigurationBuilder<BuiltConfiguration> builder) {
        AppenderComponentBuilder consoleAppender = builder.newAppender("console", "Console");
        consoleAppender.add(getStandardPatternLayout(builder));
        consoleAppender.addAttribute("target", "SYSTEM_OUT");
        return consoleAppender;
    }

    public void setLevel(System.Logger.Level level) {
        org.apache.logging.log4j.core.config.Configurator.setLevel(
                LogManager.getRootLogger(), Level.valueOf(level.getName()));
    }

    public void initializeRootLogger() {
        builder.add(getConsoleAppender(builder));
        rootLogger.add(builder.newAppenderRef("console"));
        builder.add(rootLogger);
        initialize(builder.build());
    }
}
