package dev.struchkov.haiti.filter.jooq.page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableSeek {

    private final Object lastId;
    private int pageSize = 30;

    public static PageableSeek empty() {
        return new PageableSeek(null);
    }

    public static PageableSeek of(int pageSize) {
        return new PageableSeek(null, pageSize);
    }

    public static PageableSeek of(@NonNull Object lastId) {
        return new PageableSeek(lastId);
    }

    public static PageableSeek of(@NonNull Object lastId, int pageSize) {
        return new PageableSeek(lastId, pageSize);
    }

}
