package service;

import configuration.Const;

public class ConsoleMessageService {
    public static void success (String message) {
        System.out.println(Const.GREEN + message + Const.WHITE);
    }

    public static void error (String message) {
        System.out.println(Const.RED + message + Const.WHITE);
    }

    public static void warning (String message) {
        System.out.println(Const.WARNING + message + Const.WHITE);
    }
}
