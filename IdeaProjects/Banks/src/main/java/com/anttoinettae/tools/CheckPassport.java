package com.anttoinettae.tools;

import java.util.regex.Pattern;

public abstract class CheckPassport {
    public static Boolean check(String passport){
        String pattern = "\\d{4}\\s\\d{6}";
        return Pattern.matches(pattern, passport);
    }
}
