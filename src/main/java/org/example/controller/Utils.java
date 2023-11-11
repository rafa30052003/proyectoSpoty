package org.example.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean validateTimeFormat(String timeString) {
        // Expresión regular para validar el formato de tiempo: 00:00
        String regex = "^([0-5]\\d):([0-5]\\d)$";

        return timeString.matches(regex);
    }
    public static boolean validateEmail(String email) {
        // Expresión regular para validar el formato de correo electrónico
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}
