package dev.struchkov.haiti.filter.jooq;

import dev.struchkov.haiti.utils.Inspector;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static dev.struchkov.haiti.utils.Checker.checkNotEmpty;
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
        Inspector.isNotNull(field);
        if (from != null && to != null) {
            Condition condition = DSL.field(field).between(from, to);
            conditions.add(condition);
        }
        return this;
    }

    public <Y extends Comparable<? super Y>> CriteriaJooqQuery notBetween(String field, Y from, Y to) {
        Inspector.isNotNull(field);
        if (from != null && to != null) {
            Condition condition = DSL.field(field).notBetween(from, to);
            conditions.add(condition);
        }
        return this;
    }

    public CriteriaJooqQuery matchPhrase(String field, Object value) {
        Inspector.isNotNull(field);
        if (value != null) {
            final Condition condition = condition(Map.of(field(field), value));
            conditions.add(condition);
        }
        return this;
    }

    public CriteriaJooqQuery matchPhrase(String field, Object... values) {
        Inspector.isNotNull(field);
        if (checkNotEmpty(values)) {
            conditions.add(DSL.field(field).in(values));
        }
        return this;
    }

    public <U> CriteriaJooqQuery matchPhrase(String field, Set<U> values) {
        Inspector.isNotNull(field);
        if (values != null) {
            if (values.isEmpty()) {
                conditions.add(DSL.field(field).isNull());
            } else {
                conditions.add(DSL.field(field).in(values));
            }
        }
        return this;
    }

    public CriteriaJooqQuery notMatchPhrase(String field, Object value) {
        Inspector.isNotNull(field);
        if (value != null) {
            final Condition condition = condition(Map.of(field(field), value)).not();
            conditions.add(condition);
        }
        return this;
    }

    public <U> CriteriaJooqQuery notMatchPhrase(String field, Set<U> values) {
        Inspector.isNotNull(field);
        if (values != null) {
            if (values.isEmpty()) {
                conditions.add(DSL.field(field).isNull().not());
            } else {
                conditions.add(DSL.field(field).in(values).not());
            }
        }
        return this;
    }

    public CriteriaJooqQuery notMatchPhrase(String field, Object... values) {
        Inspector.isNotNull(field);
        if (checkNotEmpty(values)) {
            conditions.add(DSL.field(field).in(values).not());
        }
        return this;
    }

    public CriteriaJooqQuery exists(String field) {
        if (field != null) {
            conditions.add(DSL.field(field).isNotNull());
        }
        return this;
    }

    public CriteriaJooqQuery notExists(String field) {
        if (field != null) {
            conditions.add(DSL.field(field).isNull());
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

    public CriteriaJooqQuery notLike(String field, String value) {
        notLike(field, value, false);
        return this;
    }

    public CriteriaJooqQuery notLikeIgnoreCase(String field, String value) {
        notLike(field, value, true);
        return this;
    }

    public CriteriaJooqQuery like(String field, String value, boolean ignoreCase) {
        Inspector.isNotNull(field);
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

    public CriteriaJooqQuery notLike(String field, String value, boolean ignoreCase) {
        Inspector.isNotNull(field);
        final Field<Object> query = field(field);
        if (value != null) {
            if (ignoreCase) {
                conditions.add(query.likeIgnoreCase(value).not());
            } else {
                conditions.add(query.like(value).not());
            }
        }
        return this;
    }

}
