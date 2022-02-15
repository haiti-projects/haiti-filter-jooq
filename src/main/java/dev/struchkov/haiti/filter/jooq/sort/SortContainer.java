package dev.struchkov.haiti.filter.jooq.sort;


import dev.struchkov.haiti.utils.Assert;

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

    public static SortContainer empty() {
        return new SortContainer(null);
    }

    public static SortContainer of(String fieldName) {
        Assert.isNotNull(fieldName);
        return new SortContainer(fieldName);
    }

    public static SortContainer of(String fieldName, SortType sortType) {
        Assert.isNotNull(fieldName);
        return new SortContainer(fieldName, sortType == null ? SortType.ASC : sortType);
    }

    public String getFieldName() {
        return fieldName;
    }

    public SortType getType() {
        return type;
    }

}
