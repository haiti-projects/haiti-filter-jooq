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
    private String alias;

    private JoinTable(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinTypeOperation, String alias) {
        this.tableName = tableName;
        this.fieldBase = fieldBase;
        this.fieldReference = fieldReference;
        this.joinTypeOperation = joinTypeOperation;
        this.alias = alias;
    }

    public static JoinTable ofLeft(String tableName, String fieldBase, String fieldReference) {
        Assert.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.LEFT, null);
    }

    public static JoinTable ofLeft(String tableName, String fieldBase, String fieldReference, String alias) {
        Assert.isNotNull(tableName, fieldBase, fieldReference, alias);
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.LEFT, alias);
    }

    public static JoinTable onRight(String tableName, String fieldBase, String fieldReference) {
        Assert.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.RIGHT, null);
    }

    public static JoinTable onRight(String tableName, String fieldBase, String fieldReference, String alias) {
        Assert.isNotNull(tableName, fieldBase, fieldReference, alias);
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.RIGHT, alias);
    }

    public static JoinTable of(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinType) {
        Assert.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, fieldBase, fieldReference, joinType, null);
    }

    public static JoinTable of(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinType, String alias) {
        Assert.isNotNull(tableName, fieldBase, fieldReference, alias);
        return new JoinTable(tableName, fieldBase, fieldReference, joinType, alias);
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

    public String getAlias() {
        return alias;
    }

}
