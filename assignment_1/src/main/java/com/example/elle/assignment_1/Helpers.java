package com.example.elle.assignment_1;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Elle on 2015-09-02.
 */
public class Helpers {

    static String [] quoteArray;

    public static String getDate(){
        String currentDate = DateFormat.getDateInstance().format(new Date());
        return currentDate;
    }

    public static String getQuote(){
        String randomStr = quoteArray[new Random().nextInt(quoteArray.length)];
        return randomStr;
    }
}
