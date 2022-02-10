package dev.struchkov.haiti.filter.jooq.exception;

import dev.struchkov.haiti.filter.exception.FilterException;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class FilterJooqHaitiException extends FilterException {

    public FilterJooqHaitiException(String message) {
        super(message);
    }

    public static Supplier<FilterJooqHaitiException> filterJooqException(String message, Object... objects) {
        return () -> {
            return new FilterJooqHaitiException(MessageFormat.format(message, objects));
        };
    }

}
