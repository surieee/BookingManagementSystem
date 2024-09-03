package com.wg.bookmyshow.util;



import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class  LoggingHelper {
    private static final Logger logger = Logger.getLogger(LoggingHelper.class.getName());

    static {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.ALL); 

        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL); 
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}