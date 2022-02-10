package dev.struchkov.haiti.filter.jooq.page;

public class PageableOffset {

    private final int pageNumber;
    private int pageSize = 30;

    private PageableOffset(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    private PageableOffset(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public static PageableOffset of(int pageNumber) {
        return new PageableOffset(pageNumber);
    }

    public static PageableOffset of(int pageNumber, int pageSize) {
        return new PageableOffset(pageNumber, pageSize);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
