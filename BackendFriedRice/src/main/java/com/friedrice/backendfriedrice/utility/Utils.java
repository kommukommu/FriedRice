package com.friedrice.backendfriedrice.utility;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Utils {
    public boolean validatePassword(String str) {

        Pattern pattern = Pattern.compile("^[A-Z|a-z|\\d|_]*$");
        boolean result = pattern.matcher(str).matches();
        System.out.println(str + result);
        return result;
    }
}
