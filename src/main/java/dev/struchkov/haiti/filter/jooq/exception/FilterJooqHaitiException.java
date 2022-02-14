package dev.struchkov.haiti.filter.jooq.exception;

import dev.struchkov.haiti.context.exception.BasicException;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class FilterJooqHaitiException extends BasicException {

    public FilterJooqHaitiException(String message) {
        super(message);
    }

    public static Supplier<FilterJooqHaitiException> filterJooqException(String message, Object... objects) {
        return () -> new FilterJooqHaitiException(MessageFormat.format(message, objects));
    }

}
