package com.goddrinksjava.prep.util;

import java.util.Arrays;

public class Validator {
    public static boolean anyNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean allEqual(Object... objects) {
        return Arrays.stream(objects).distinct().count() == 1;
    }
}
