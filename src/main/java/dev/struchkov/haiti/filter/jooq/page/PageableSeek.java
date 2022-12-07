package dev.struchkov.haiti.filter.jooq.page;

import dev.struchkov.haiti.utils.Inspector;

import static dev.struchkov.haiti.filter.jooq.exception.FilterJooqHaitiException.filterJooqException;

public class PageableSeek {

    private final Object[] values;
    private int pageSize = 30;

    private PageableSeek(Object[] values) {
        this.values = values;
    }

    private PageableSeek(Object[] values, int pageSize) {
        this.values = values;
        this.pageSize = pageSize;
    }

    public static PageableSeek empty() {
        return new PageableSeek(null);
    }

    public static PageableSeek ofPageSize(int pageSize) {
        return new PageableSeek(null, pageSize);
    }

    public static PageableSeek ofValues(Object... values) {
        Inspector.isNotEmpty(filterJooqException("Переданы пустой набор данных для PageableSeek"), values);
        return new PageableSeek(values);
    }

    public static PageableSeek ofPageSizeAndValues(int pageSize, Object... values) {
        Inspector.isNotEmpty(filterJooqException("Переданы пустой набор данных для PageableSeek"), values);
        return new PageableSeek(values, pageSize);
    }

    public Object[] getValues() {
        return values;
    }

    public int getPageSize() {
        return pageSize;
    }

}
