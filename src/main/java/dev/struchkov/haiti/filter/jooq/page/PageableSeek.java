package dev.struchkov.haiti.filter.jooq.page;

import dev.struchkov.haiti.utils.Inspector;

public class PageableSeek {

    private final Object lastId;
    private int pageSize = 30;

    private PageableSeek(Object lastId) {
        this.lastId = lastId;
    }

    private PageableSeek(Object lastId, int pageSize) {
        this.lastId = lastId;
        this.pageSize = pageSize;
    }

    public static PageableSeek empty() {
        return new PageableSeek(null);
    }

    public static PageableSeek of(int pageSize) {
        return new PageableSeek(null, pageSize);
    }

    public static PageableSeek of(Object lastId) {
        Inspector.isNotNull(lastId);
        return new PageableSeek(lastId);
    }

    public static PageableSeek of(Object lastId, int pageSize) {
        Inspector.isNotNull(lastId);
        return new PageableSeek(lastId, pageSize);
    }

    public Object getLastId() {
        return lastId;
    }

    public int getPageSize() {
        return pageSize;
    }

}
