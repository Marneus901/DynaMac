/*******************************************************
* Created by Marneus901                                *
* © 2013 Dynamac.org                                   *
********************************************************
* Dynamac's custom log system.                         *
********************************************************/
package org.dynamac.enviroment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class provides functionality for basic logging throughout DynaMac's
 * scripts, classes, loaders, etc. Developers can instantiate their own logger
 * for use within their programs.
 *
 * A logger can be instantiated with a program name and <tt>level</tt>:
 *
 * <pre>
 * {@code
 * mylogger = new Logger("My App", DEBUG);
 * }
 * </pre>
 *
 * The provided level can be one of {@link #DEBUG}, {@link #INFO},
 * {@link #WARN}, {@link #ERROR}, or {@link #FATAL}. Anything less severe than
 * the provided level will be suppressed. For example, when distributing your
 * application, you may choose to raise the threshold to <tt>WARN</tt>.
 *
 * Logging is handled through calling the method for the level of your log entry
 * (e.g. {@link #debug(String, Object...) #debug}). For more complex operations,
 * the logging operations also support the same format as
 * <tt>java.lang.String.format</tt>:
 *
 * <pre>
 * @{@code
 * mylogger.info("%s is %s for a $%.2f method...", "Sprintf", "underrated", 5);
 * }
 * </pre>
 *
 * The above example would print a result in DynaMac's console similar to:
 *
 * <pre>
 * {@code
 * [My App] 2013-03-01 23:54:02 --- INFO: Sprintf is underrated for a $5.00 method...
 * }
 * </pre>
 */
public class Logger {

    /**
     * Unrecoverable error conditions.
     */
    public final static int FATAL = 4;

    /**
     * Recoverable error conditions.
     */
    public final static int ERROR = 3;

    /**
     * Warnings.
     */
    public final static int WARN = 2;

    /**
     * Generic (useful) information.
     */
    public final static int INFO = 1;

    /**
     * Low-level information for developers.
     */
    public final static int DEBUG = 0;

    /**
     * Mapping of <tt>int</tt> levels to <tt>String</tt>s.
     */
    public final static String[] LEVELS = { "DEBUG", "INFO", "WARN", "ERROR", "FATAL" };

    /**
     * The global logger.
     */
    public static Logger global = new Logger("DynaMac", INFO);

    /**
     * The name of the program.
     */
    private String progname;

    /**
     * The logger level. Logs with a lower level are suppressed.
     */
    private int level = INFO;

    /**
     * Collection of log entries.
     */
    private static ArrayList<String> entries = new ArrayList<String>();

    /**
     * Instantiates a new <tt>Logger</tt> with a default <tt>progname</tt> and
     * level.
     */
    public Logger() {
        this.progname = "DynaMac";
	}

    /**
     * Instantiates a new <tt>Logger</tt> with provided <tt>progname</tt> and a
     * default level.
     *
     * @param progname Name of the program.
     */
    public Logger(String progname) {
        this.progname = progname;
    }

    /**
     * Instantiates a new <tt>Logger</tt> with provided <tt>progname</tt> and
     * <tt>level</tt>.
     *
     * @param progname Name of the program.
     * @param level    Logging threshold.
     */
    public Logger(String progname, int level) {
        this.progname = progname;
        this.level = level;
    }

    /**
     * Adds a new <tt>DEBUG</tt> log entry.
     *
     * @param message The log message.
     * @param args     Optional format specifiers. Similar to
     *                 <tt>String.format</tt>.
     */
    public void debug(String message, Object... args) {
        log(DEBUG, message, args);
    }

    /**
     * Adds a new <tt>INFO</tt> log entry.
     *
     * @param message The log message.
     * @param args     Optional format specifiers. Similar to
     *                 <tt>String.format</tt>.
     */
    public void info(String message, Object... args) {
        log(INFO, message, args);
    }

    /**
     * Adds a new <tt>WARN</tt> log entry.
     *
     * @param message The log message.
     * @param args     Optional format specifiers. Similar to
     *                 <tt>String.format</tt>.
     */
    public void warn(String message, Object... args) {
        log(WARN, message, args);
    }

    /**
     * Adds a new <tt>ERROR</tt> log entry.
     *
     * @param message The log message.
     * @param args     Optional format specifiers. Similar to
     *                 <tt>String.format</tt>.
     */
    public void error(String message, Object... args) {
        log(ERROR, message, args);
    }

    /**
     * Adds a new <tt>FATAL</tt> log entry.
     *
     * @param message The log message.
     * @param args     Optional format specifiers. Similar to
     *                 <tt>String.format</tt>.
     */
    public void fatal(String message, Object... args) {
        log(FATAL, message, args);
    }

    /**
     * Adds a new log entry with provided <tt>severity</tt>
     * and <tt>message</tt>.
     *
     * @param severity Severity of the message.
     * @param message  The message.
     * @param args     Optional format specifiers. Similar to
     *                 <tt>String.format</tt>.
     */
    public void log(int severity, String message, Object... args) {
        if (severity < level)
            return;

        String entry = format(progname, severity, message, args);
        add(entry);
    }

    /**
     * Adds a new log entry with provided <tt>message</tt> and a default
     * severity of <tt>INFO</tt>.
     *
     * @param message The message.
     * @param args    Optional format specifiers. Similar to
     *                <tt>String.format</tt>
     */
    public void log(String message, Object...args) {
        log(INFO, message, args);
    }

    /**
     * Returns all stored log {@link #entries entries} as an <tt>array</tt> of
     * <tt>String</tt>s.
     *
     * @return Log entries.
     */
    public static String[] getLogs() {
		return entries.toArray(new String[]{});
	}

    /**
     * Adds provided <tt>String</tt> to {@link #entries entries}, prints the
     * message, and updates the GUI. When the number of stored entries exceeds
     * <tt>100</tt>, older entries are pruned.
     *
     * @param entry The log entry to add.
     */
    private void add(String entry) {
        if (entries.size() > 100)
            entries.remove(0);

        entries.add(entry);
        Data.clientGUI.updateLog();
        System.out.println(entry);
    }

    /**
     * Formats provided <tt>progname</tt>, <tt>severity</tt>, and
     * <tt>message</tt> into a readable log entry, prior to insertion. Returns
     * <tt>String</tt> similar to:
     *
     * <pre>
     * {@code
     * [DynaMac] 2013-02-25 22:54:15 --- INFO: Something amazing just happened!
     * }
     * </pre>
     *
     * @return The formatted log entry.
     */
    private static String format(String progname, int severity, String message, Object... args) {
        String formattedMessage = String.format(message, args);
        return String.format("[%s] %s --- %s: %s",
                timestamp(), progname, LEVELS[severity], formattedMessage);
    }

    /**
     * Returns a formatted timestamp for the current date and time.
     *
     * @return A <tt>String</tt> formatted as <tt>yyyy-MM-dd HH:mm:ss</tt>.
     */
    private static String timestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

}
