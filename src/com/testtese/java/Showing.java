package com.testtese.java;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Showing {

    public static void Blank(int num) {
        for (int i = 0; i <= num; i++) {
            System.out.print(" ");
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }

        Matcher isNum = pattern.matcher(bigStr);
        return isNum.matches();
    }

}
