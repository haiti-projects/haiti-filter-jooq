package dev.struchkov.haiti.filter.jooq.join;

import dev.struchkov.haiti.utils.Inspector;

import java.util.Set;

import static dev.struchkov.haiti.filter.jooq.exception.FilterJooqHaitiException.filterJooqException;

/**
 * Контейнер для соединения с таблицами.
 *
 * @author upagge 15.04.2021
 */
public class JoinTable {

    private final String tableName;
    private final Set<JoinFieldReference> fieldReferences;
    private final JoinTypeOperation joinTypeOperation;
    private String alias;

    private JoinTable(String tableName, JoinTypeOperation joinTypeOperation, Set<JoinFieldReference> fieldReferences, String alias) {
        this.tableName = tableName;
        this.joinTypeOperation = joinTypeOperation;
        this.fieldReferences = fieldReferences;
        this.alias = alias;
    }

    public static JoinTable ofLeft(String tableName, String fieldBase, String fieldReference) {
        Inspector.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, JoinTypeOperation.LEFT, JoinFieldReference.ofSingletonSet(fieldBase, fieldReference), null);
    }

    public static JoinTable ofLeft(String tableName, Set<JoinFieldReference> fieldReferences) {
        Inspector.isNotNull(tableName, fieldReferences);
        Inspector.isNotEmpty(fieldReferences, filterJooqException("fieldReferences is empty: {0}", fieldReferences));
        return new JoinTable(tableName, JoinTypeOperation.LEFT, fieldReferences, null);
    }

    public static JoinTable ofLeft(String tableName, String fieldBase, String fieldReference, String alias) {
        Inspector.isNotNull(tableName, fieldBase, fieldReference, alias);
        return new JoinTable(tableName, JoinTypeOperation.LEFT, JoinFieldReference.ofSingletonSet(fieldBase, fieldReference), alias);
    }

    public static JoinTable ofLeft(String tableName, Set<JoinFieldReference> fieldReferences, String alias) {
        Inspector.isNotNull(tableName, fieldReferences, alias);
        Inspector.isNotEmpty(fieldReferences, filterJooqException("fieldReferences is empty: {0}", fieldReferences));
        return new JoinTable(tableName, JoinTypeOperation.LEFT, fieldReferences, alias);
    }

    public static JoinTable onRight(String tableName, String fieldBase, String fieldReference) {
        Inspector.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, JoinTypeOperation.RIGHT, JoinFieldReference.ofSingletonSet(fieldBase, fieldReference), null);
    }

    public static JoinTable onRight(String tableName, Set<JoinFieldReference> fieldReferences) {
        Inspector.isNotNull(tableName, fieldReferences);
        Inspector.isNotEmpty(fieldReferences, filterJooqException("fieldReferences is empty: {0}", fieldReferences));
        return new JoinTable(tableName, JoinTypeOperation.RIGHT, fieldReferences, null);
    }

    public static JoinTable onRight(String tableName, String fieldBase, String fieldReference, String alias) {
        Inspector.isNotNull(tableName, fieldBase, fieldReference, alias);
        return new JoinTable(tableName, JoinTypeOperation.RIGHT, JoinFieldReference.ofSingletonSet(fieldBase, fieldReference), alias);
    }

    public static JoinTable onRight(String tableName, Set<JoinFieldReference> fieldReferences, String alias) {
        Inspector.isNotNull(tableName, fieldReferences, alias);
        Inspector.isFalse(fieldReferences.isEmpty(), filterJooqException("fieldReferences is empty: {0}", fieldReferences));
        return new JoinTable(tableName, JoinTypeOperation.RIGHT, fieldReferences, alias);
    }

    public static JoinTable of(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinType) {
        Inspector.isNotNull(tableName, fieldBase, fieldReference);
        return new JoinTable(tableName, joinType, JoinFieldReference.ofSingletonSet(fieldBase, fieldReference), null);
    }

    public static JoinTable of(String tableName, Set<JoinFieldReference> fieldReferences, JoinTypeOperation joinType) {
        Inspector.isNotNull(tableName, fieldReferences);
        return new JoinTable(tableName, joinType, fieldReferences, null);
    }

    public static JoinTable of(String tableName, String fieldBase, String fieldReference, JoinTypeOperation joinType, String alias) {
        Inspector.isNotNull(tableName, fieldBase, fieldReference, alias);
        return new JoinTable(tableName, joinType, JoinFieldReference.ofSingletonSet(fieldBase, fieldReference), alias);
    }

    public static JoinTable of(String tableName, Set<JoinFieldReference> fieldReferences, JoinTypeOperation joinType, String alias) {
        Inspector.isNotNull(tableName, fieldReferences, alias);
        return new JoinTable(tableName, joinType, fieldReferences, alias);
    }

    public String getTableName() {
        return tableName;
    }

    public Set<JoinFieldReference> getFieldReferences() {
        return fieldReferences;
    }

    public JoinTypeOperation getJoinTypeOperation() {
        return joinTypeOperation;
    }

    public String getAlias() {
        return alias;
    }

}
