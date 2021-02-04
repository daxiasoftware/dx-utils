package com.daxiasoftware.utils;

import java.util.Random;

public class RandomUtils {

    public static String getRandomNumber(int length) {
        String result = "";
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            result += rnd.nextInt(10);
        }
        return result;
    }

    public static String getRandomChars(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        char[] chars = str.toCharArray();
        String result = "";
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            result += chars[rnd.nextInt(chars.length)];
        }
        return result;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(getRandomNumber(5));
        System.out.println(getRandomChars(6));
    }
}
