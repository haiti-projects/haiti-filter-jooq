package dev.struchkov.haiti.filter.jooq.sort;


import dev.struchkov.haiti.utils.Inspector;

public class SortContainer {

    private final String fieldName;
    private SortType type = SortType.ASC;

    private SortContainer(String fieldName) {
        this.fieldName = fieldName;
    }

    private SortContainer(String fieldName, SortType type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    public static SortContainer of(String fieldName) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(fieldName);
    }

    public static SortContainer of(String fieldName, SortType sortType) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(fieldName, sortType == null ? SortType.ASC : sortType);
    }

    public String getFieldName() {
        return fieldName;
    }

    public SortType getType() {
        return type;
    }

}
