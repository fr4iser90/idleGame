package com.idlegame.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    private LoggerUtil() {
        // Private constructor to prevent instantiation
    }

    public static void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    public static void info(String message, Object... args) {
        logger.info(message, args);
    }

    public static void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public static void error(String message, Object... args) {
        logger.error(message, args);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static Logger getLogger() {
        return logger;
    }
}
