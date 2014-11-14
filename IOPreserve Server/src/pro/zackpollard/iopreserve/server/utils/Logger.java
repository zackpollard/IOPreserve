package pro.zackpollard.iopreserve.server.utils;

public class Logger {

    private static boolean debug = false;

    /**
     * Log with the required level and can provide a message.
     *
     * @param level   The LoggerLevel of that message, will be reflected in the logger output.
     * @param message The message that should accompany the logger level in the output.
     */
    public static void log(LoggerLevel level, String message) {

        Logger.log(level, message, null);
    }

    /**
     * Log with the required level and can provide a message.
     *
     * @param level   The LoggerLevel of that message, will be reflected in the logger output.
     * @param message The message that should accompany the logger level in the output.
     * @param e       The exception that was provided with that error, is not required.
     */
    public static void log(LoggerLevel level, String message, Exception e) {

        switch (level) {
            case INFO:
                System.out.println("INFO: " + message);
            case WARNING:
                System.out.println("WARNING: " + message);
            case ALERT:
                System.out.println("ALERT: " + message);
            case ERROR:
                Logger.printException(e);
                System.out.println("ERROR: " + message);
            case FATAL:
                Logger.printException(e);
                System.out.println("FATAL: " + message);
            case DEBUG:
                if (Logger.getDebug()) {
                    Logger.printException(e);
                    System.out.println("DEBUG: " + message);
                }
        }
    }

    /**
     * Prints a stacktrace for the provided exception if that exception object is not null.
     *
     * @param e The exception that is to be handled and printed, is nullable.
     */
    private static void printException(Exception e) {

        if (e != null) {

            e.printStackTrace();
        }
    }

    /**
     * Toggles the debug state in the logger.
     *
     * @return the new debug state.
     */
    public static boolean toggleDebug() {

        return Logger.debug = !Logger.debug;
    }

    /**
     * Used to get whether the logger is in debug state or not.
     *
     * @return the current debug state.
     */
    public static boolean getDebug() {

        return debug;
    }

    public static void setDebug(boolean debug) {

        Logger.debug = debug;
    }

    /**
     * Enum of the various levels the logs can be sent at.
     */
    public enum LoggerLevel {

        INFO,
        WARNING,
        ALERT,
        ERROR,
        FATAL,
        DEBUG
    }
}