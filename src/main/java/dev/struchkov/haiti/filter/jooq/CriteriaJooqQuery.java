package dev.struchkov.haiti.filter.jooq;

import dev.struchkov.haiti.filter.FilterQuery;
import lombok.NonNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.jooq.impl.DSL.condition;
import static org.jooq.impl.DSL.field;

public class CriteriaJooqQuery implements FilterQuery {

    private final List<Condition> conditions = new ArrayList<>();

    public static CriteriaJooqQuery create() {
        return new CriteriaJooqQuery();
    }

    protected List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public <Y extends Comparable<? super Y>> FilterQuery between(@NonNull String field, Y from, Y to) {
        if (from != null && to != null) {
            Condition condition = DSL.field(field).between(from, to);
            conditions.add(condition);
        }
        return this;
    }

    @Override
    public <Y extends Comparable<? super Y>> FilterQuery greaterThan(@NonNull String field, Y value) {
        // FIXME: Добавить поддержку
        throw new IllegalStateException("Операция пока не поддерживается");
    }

    @Override
    public <Y extends Comparable<? super Y>> FilterQuery lessThan(@NonNull String field, Y value) {
        // FIXME: Добавить поддержку
        throw new IllegalStateException("Операция пока не поддерживается");
    }

    @Override
    public FilterQuery matchPhrase(@NonNull String field, Object value) {
        if (value != null) {
            final Condition condition = condition(Map.of(field(field), value));
            conditions.add(condition);
        }
        return this;
    }

    @Override
    public <U> FilterQuery matchPhrase(@NonNull String field, Set<U> values) {
        if (values != null && !values.isEmpty()) {
            conditions.add(DSL.field(field).in(values));
        }
        return this;
    }

    @Override
    public FilterQuery exists(String field) {
        if (field != null) {
            conditions.add(DSL.field(field).isNotNull());
        }
        return this;
    }

    @Override
    public FilterQuery like(@NonNull String field, String value, boolean ignoreCase) {
        final Field<Object> query = field(field);
        if (value != null) {
            if (ignoreCase) {
                conditions.add(query.like(value));
            } else {
                conditions.add(query.likeIgnoreCase(value));
            }
        }
        return this;
    }

    @Override
    public FilterQuery checkBoolInt(@NonNull String field, Boolean flag) {
        // FIXME: Добавить поддержку
        throw new IllegalStateException("Операция пока не поддерживается");
    }

    @Override
    public List<Condition> build() {
        return conditions;
    }

}
