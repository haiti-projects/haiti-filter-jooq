package dev.struchkov.haiti.filter.jooq.sort;


import dev.struchkov.haiti.utils.Inspector;

public class SortContainer {

    private final String fieldName;
    private SortType type = SortType.ASC;
    private NullsOrderType nullsOrder = NullsOrderType.LAST;

    private SortContainer(String fieldName) {
        this.fieldName = fieldName;
    }

    private SortContainer(String fieldName, SortType type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    private SortContainer(String fieldName, SortType type, NullsOrderType nullsOrder) {
        this.fieldName = fieldName;
        this.type = type;
        this.nullsOrder = nullsOrder;
    }

    public static SortContainer of(String fieldName) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(fieldName);
    }

    public static SortContainer of(String fieldName, SortType sortType) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(fieldName, sortType == null ? SortType.ASC : sortType);
    }

    public static SortContainer of(String fieldName, SortType sortType, NullsOrderType nullsOrder) {
        Inspector.isNotNull(fieldName);
        return new SortContainer(
                fieldName,
                sortType == null ? SortType.ASC : sortType,
                nullsOrder == null ? NullsOrderType.LAST : nullsOrder
        );
    }

    public String getFieldName() {
        return fieldName;
    }

    public SortType getType() {
        return type;
    }

    public NullsOrderType getNullsOrder() {
        return nullsOrder;
    }
}
