module blckroot.sdk.logger {
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.plugins;

    provides System.LoggerFinder
            with com.blckroot.sdk.logger.LoggerFinder;

    exports com.blckroot.sdk.logger;
}