package dev.struchkov.haiti.filter.jooq.page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableOffset {

    private final int pageNumber;
    private int pageSize = 30;

    public static PageableOffset of(int pageNumber) {
        return new PageableOffset(pageNumber);
    }

    public static PageableOffset of(int pageNumber, int pageSize) {
        return new PageableOffset(pageNumber, pageSize);
    }

}
