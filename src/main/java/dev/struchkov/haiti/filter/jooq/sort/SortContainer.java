package dev.struchkov.haiti.filter.jooq.sort;


import dev.struchkov.haiti.utils.Inspector;

public class SortContainer {

    private final String fieldName;
    private SortType type = SortType.ASC;
    private NullOrderType nullOrderType = NullOrderType.LAST;

    private SortContainer(String fieldName) {
        this.fieldName = fieldName;
    }

    private SortContainer(String fieldName, SortType type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    private SortContainer(String fieldName, SortType type, NullOrderType nullsOrder) {
        this.fieldName = fieldName;
        this.type = type;
        this.nullOrderType = nullsOrder;
    }

    public static SortContainer of(String fieldName) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(fieldName);
    }

    public static SortContainer of(String fieldName, SortType sortType) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(fieldName, sortType == null ? SortType.ASC : sortType);
    }

    public static SortContainer of(String fieldName, SortType sortType, NullOrderType nullOrderType) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(
                fieldName,
                sortType == null ? SortType.ASC : sortType,
                nullOrderType == null ? NullOrderType.LAST : nullOrderType
        );
    }

    public String getFieldName() {
        return fieldName;
    }

    public SortType getType() {
        return type;
    }

    public NullOrderType getNullOrderType() {
        return nullOrderType;
    }

}
