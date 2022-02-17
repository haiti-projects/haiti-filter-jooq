package dev.struchkov.haiti.filter.jooq;

import dev.struchkov.haiti.utils.Assert;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.jooq.impl.DSL.condition;
import static org.jooq.impl.DSL.field;

public class CriteriaJooqQuery {

    private final List<Condition> conditions = new ArrayList<>();

    public static CriteriaJooqQuery create() {
        return new CriteriaJooqQuery();
    }

    List<Condition> getConditions() {
        return conditions;
    }

    public <Y extends Comparable<? super Y>> CriteriaJooqQuery between(String field, Y from, Y to) {
        Assert.isNotNull(field);
        if (from != null && to != null) {
            Condition condition = DSL.field(field).between(from, to);
            conditions.add(condition);
        }
        return this;
    }

    public CriteriaJooqQuery matchPhrase(String field, Object value) {
        Assert.isNotNull(field);
        if (value != null) {
            final Condition condition = condition(Map.of(field(field), value));
            conditions.add(condition);
        }
        return this;
    }

    public <U> CriteriaJooqQuery matchPhrase(String field, Set<U> values) {
        Assert.isNotNull(field);
        if (values != null && !values.isEmpty()) {
            conditions.add(DSL.field(field).in(values));
        }
        return this;
    }

    public CriteriaJooqQuery exists(String field) {
        if (field != null) {
            conditions.add(DSL.field(field).isNotNull());
        }
        return this;
    }

    public CriteriaJooqQuery like(String field, String value) {
        like(field, value, false);
        return this;
    }

    public CriteriaJooqQuery likeIgnoreCase(String field, String value) {
        like(field, value, true);
        return this;
    }

    public CriteriaJooqQuery like(String field, String value, boolean ignoreCase) {
        Assert.isNotNull(field);
        final Field<Object> query = field(field);
        if (value != null) {
            if (ignoreCase) {
                conditions.add(query.likeIgnoreCase(value));
            } else {
                conditions.add(query.like(value));
            }
        }
        return this;
    }

}
