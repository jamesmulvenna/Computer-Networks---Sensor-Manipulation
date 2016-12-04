package io;

/**
 * Created by chris on 2016-12-03.
 */
public class IO {
    public static boolean isInteger(String someValue) {
        try {
            Integer.valueOf(someValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
