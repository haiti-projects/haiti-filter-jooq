package dev.struchkov.haiti.filter.jooq.join;

import dev.struchkov.haiti.utils.Assert;

/**
 * Контейнер для соединения с таблицами.
 *
 * @author upagge 15.04.2021
 */
public class JoinTable {

    private final String tableName;
    private final String fieldBase;
    private final String fieldReference;
    private final JoinTypeOperation joinTypeOperation;

    private JoinTable(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinTypeOperation) {
        this.tableName = tableName;
        this.fieldBase = fieldBase;
        this.fieldReference = fieldReference;
        this.joinTypeOperation = joinTypeOperation;
    }

    public static JoinTable ofLeft(String tableName, String fieldBase, String fieldReference) {
        Assert.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.LEFT);
    }

    public static JoinTable onRight(String tableName, String fieldBase, String fieldReference) {
        Assert.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.RIGHT);
    }

    public static JoinTable of(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinType) {
        Assert.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, fieldBase, fieldReference, joinType);
    }

    public String getTableName() {
        return tableName;
    }

    public String getFieldBase() {
        return fieldBase;
    }

    public String getFieldReference() {
        return fieldReference;
    }

    public JoinTypeOperation getJoinTypeOperation() {
        return joinTypeOperation;
    }

}
