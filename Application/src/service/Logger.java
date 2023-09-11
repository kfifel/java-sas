package service;

import configuration.Properties;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String logFilePath = "C:\\Users\\fifle\\Desktop\\YouCode\\SAS JAVA\\logs\\";

    public static void info(String message) {
        log("INFO", message, null);
    }

    public static void warning(String message) {
        log("WARNING", message, null);
    }

    public static void error(String message) {
        log("ERROR", message, null);
    }

    public static void error(String message, Throwable throwable) {
        log("ERROR", message, throwable);
    }

    private static void log(String level, String message, Throwable throwable) {
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        String logMessage = "[" + formattedDate + "] [" + level + "] " + message;

        if(Properties.DEVELOPMENT)
            System.out.println(logMessage);

        // Log to file
        try (FileWriter fileWriter = new FileWriter(logFilePath + LocalDate.now() + ".txt", true)) {
            fileWriter.write(logMessage + System.lineSeparator());
            if (throwable != null) {
                PrintWriter pw = new PrintWriter(fileWriter);
                throwable.printStackTrace(pw);
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
