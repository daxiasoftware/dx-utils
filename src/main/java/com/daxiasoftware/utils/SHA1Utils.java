package com.daxiasoftware.utils;

import java.security.MessageDigest;

public class SHA1Utils {

    public static String sha1(String data) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes("utf-8"));
            StringBuffer buf = new StringBuffer();
            byte[] bits = md.digest();
            for (int i = 0; i < bits.length; i++) {
                int a = bits[i];
                if (a < 0) {
                    a += 256;
                }
                if (a < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(sha1("hello"));
    }
}
