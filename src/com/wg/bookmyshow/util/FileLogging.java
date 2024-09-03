package com.wg.bookmyshow.util;
 
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
 
public class FileLogging {
    private static final String LOG_FILE_NAME = "C:\\Users\\ssuri\\eclipse-workspace1\\BookingManagementSystemworkingview2\\logs\\app.log";
    private static final Logger logger = Logger.getLogger(FileLogging.class.getName());
 
    static {
        try {
            configureInternalLoggers();
 
            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
 
            Logger rootLogger = Logger.getLogger("");
            for (java.util.logging.Handler handler : rootLogger.getHandlers()) {
                rootLogger.removeHandler(handler);
            }
 
            rootLogger.addHandler(fileHandler);
 
            rootLogger.setLevel(Level.INFO);
 
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize file handler", e);
        }
    }
 
    private static void configureInternalLoggers() {
        Logger.getLogger("sun.security").setLevel(Level.WARNING);
        Logger.getLogger("javax.net.ssl").setLevel(Level.WARNING);
        Logger.getLogger("jdk.internal.event").setLevel(Level.WARNING);
    }
 
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getName());
    }
}

