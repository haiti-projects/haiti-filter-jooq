package dev.struchkov.haiti.filter.jooq;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Контейнер для соединения с таблицами.
 *
 * @author upagge 15.04.2021
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinTable {

    private final String tableName;
    private final String fieldBase;
    private final String fieldReference;
    private final JoinTypeOperation joinTypeOperation;

    public static JoinTable ofLeft(@NonNull String tableName, @NonNull String fieldBase, @NonNull String fieldReference) {
        return new JoinTable(tableName, fieldBase, fieldReference, JoinTypeOperation.LEFT);
    }

    public static JoinTable of(@NonNull String tableName, @NonNull String fieldBase, @NonNull String fieldReference, JoinTypeOperation joinType) {
        return new JoinTable(tableName, fieldBase, fieldReference, joinType);
    }

}
