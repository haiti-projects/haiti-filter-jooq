package dev.struchkov.haiti.filter.jooq.utils;

import static dev.struchkov.haiti.utils.Exceptions.utilityClass;

public final class Likes {

    private Likes() {
        utilityClass();
    }

    public static final String PERCENT = "%";

    public static String subString(String text) {
        if (text == null) return null;
        return PERCENT + text + PERCENT;
    }

    public static String beginWith(String text) {
        if (text == null) return null;
        return text + PERCENT;
    }

    public static String endWith(String text) {
        if (text == null) return null;
        return text + PERCENT;
    }

}
