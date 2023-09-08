package service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String logFilePath = "C:\\Users\\fifle\\Desktop\\YouCode\\SAS JAVA\\logs\\"; // Change this to your desired log file path

    public static void info(String message) {
        log("INFO", message);
    }

    public static void warning(String message) {
        log("WARNING", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    private static void log(String level, String message) {
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        String logMessage = "[" + formattedDate + "] [" + level + "] " + message;

        // Print to console
        // System.out.println(logMessage);

        // Append to the log file
        try (FileWriter fileWriter = new FileWriter(logFilePath + LocalDate.now() + ".text", true)) {
            fileWriter.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
