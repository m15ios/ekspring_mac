package ru.m15.ekspring.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    public static String getKeyByUrl( String input ){
        // Это //комментарий/ с текстом
        Pattern pattern = Pattern.compile("(?<=\\/\\/)(.*?)(?=\\/)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
            //System.out.println(result); // выводит "комментарий"
        }
        return "";
    }
}
