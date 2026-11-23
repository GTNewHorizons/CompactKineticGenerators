package com.lulan.compactkineticgenerators.utility;

import org.apache.logging.log4j.Level;

import com.lulan.compactkineticgenerators.handler.ConfigHandler;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {

    public static void log(Level logLevel, Object object) {
        FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
    }

    // off: Disable logging.
    public static void off(Object object) {
        log(Level.OFF, object);
    }

    // fatal: Log critical failures that stop the program.
    public static void fatal(Object object) {
        log(Level.FATAL, object);
    }

    // error: Log problems that may stop the program.
    public static void error(Object object) {
        log(Level.ERROR, object);
    }

    // warn: Log problems that may lead to errors.
    public static void warn(Object object) {
        log(Level.WARN, object);
    }

    // log: Log messages even when debug mode is off.
    public static void log(Object object) {
        log(Level.INFO, object);
    }

    // info: Log basic diagnostic messages.
    public static void info(Object object) {
        if (ConfigHandler.debugMode) log(Level.INFO, object);
    }

    // debug: Log debugging details.
    public static void debug(Object object) {
        if (ConfigHandler.debugMode) log(Level.DEBUG, object);
    }

    // trace: Log details used to follow program execution.
    public static void trace(Object object) {
        log(Level.TRACE, object);
    }

    // all: Log all messages.
    public static void all(Object object) {
        log(Level.ALL, object);
    }

}
