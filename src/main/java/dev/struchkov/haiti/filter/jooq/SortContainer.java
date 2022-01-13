package dev.struchkov.haiti.filter.jooq;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SortContainer {

    private final String fieldName;
    private SortType type = SortType.ASC;

    public static SortContainer empty() {
        return new SortContainer(null);
    }

    public static SortContainer of(@NonNull String fieldName) {
        return new SortContainer(fieldName);
    }

    public static SortContainer of(@NonNull String fieldName, SortType sortType) {
        return new SortContainer(fieldName, sortType == null ? SortType.ASC : sortType);
    }

}
