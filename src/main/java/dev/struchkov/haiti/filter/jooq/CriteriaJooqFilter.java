package dev.struchkov.haiti.filter.jooq;

import dev.struchkov.haiti.filter.Filter;
import dev.struchkov.haiti.filter.FilterQuery;
import lombok.NonNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Operator;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.condition;
import static org.jooq.impl.DSL.field;

public class CriteriaJooqFilter implements Filter {

    private final List<Condition> andConditions = new ArrayList<>();
    private final List<Condition> orConditions = new ArrayList<>();
    private final List<Condition> notConditions = new ArrayList<>();
    private final List<JoinTable> joinTables = new ArrayList<>();

    private String fieldOrder;
    private Object lastId;
    private Integer pageSize;

    private final String table;
    private final DSLContext dsl;

    protected CriteriaJooqFilter(String table, DSLContext dsl) {
        this.table = table;
        this.dsl = dsl;
    }

    public static CriteriaJooqFilter create(String table, DSLContext dsl) {
        return new CriteriaJooqFilter(table, dsl);
    }

    @Override
    public Filter and(FilterQuery filterQuery) {
        generateAnd((CriteriaJooqQuery) filterQuery);
        return this;
    }

    @Override
    public Filter and(Consumer<FilterQuery> query) {
        final CriteriaJooqQuery criteriaQuery = CriteriaJooqQuery.create();
        query.accept(criteriaQuery);
        generateAnd(criteriaQuery);
        return this;
    }

    @Override
    public Filter or(FilterQuery filterQuery) {
        generateOr((CriteriaJooqQuery) filterQuery);
        return this;
    }

    public Filter or(Consumer<FilterQuery> query) {
        final CriteriaJooqQuery criteriaQuery = CriteriaJooqQuery.create();
        query.accept(criteriaQuery);
        generateOr(criteriaQuery);
        return this;
    }

    @Override
    public Filter not(FilterQuery filterQuery) {
        return null;
    }

    @Override
    public Filter not(Consumer<FilterQuery> query) {
        return null;
    }

    public Filter page(@NonNull String fieldOrder, Object id, int pageSize) {
        this.fieldOrder = fieldOrder;
        this.lastId = id;
        this.pageSize = pageSize;
        return this;
    }

    public Filter join(@NonNull JoinTable... joinTables) {
        this.joinTables.addAll(Arrays.stream(joinTables).collect(Collectors.toList()));
        return this;
    }

    private void generateAnd(CriteriaJooqQuery criteriaQuery) {
        andConditions.addAll(criteriaQuery.getConditions());
    }

    private void generateOr(CriteriaJooqQuery criteriaQuery) {
        orConditions.addAll(criteriaQuery.getConditions());
    }

    @Override
    public Query build() {
        final List<Condition> conditions = new ArrayList<>();
        if (!andConditions.isEmpty()) {
            conditions.add(condition(Operator.AND, andConditions));
        }
        if (!orConditions.isEmpty()) {
            conditions.add(condition(Operator.OR, orConditions));
        }
        SelectJoinStep<Record> from = dsl.select().from(table);
        if (!joinTables.isEmpty()) {
            for (JoinTable joinTable : joinTables) {
                final String tableName = joinTable.getTableName();
                final JoinTypeOperation joinType = joinTable.getJoinTypeOperation();
                final String fieldReference = joinTable.getFieldReference();
                final String fieldBase = joinTable.getFieldBase();
                final Condition on = field(fieldReference).eq(field(fieldBase));
                switch (joinType) {
                    case LEFT:
                        from = from.leftJoin(tableName).on(on);
                        break;
                    case INNER:
                        from = from.innerJoin(tableName).on(on);
                        break;
                    case RIGHT:
                        from = from.rightJoin(tableName).on(on);
                        break;
                }
            }
        }
        final SelectConditionStep<Record> where = from.where(conditions);
        if (pageSize != null && fieldOrder != null) {
            if (lastId != null) {
                where.orderBy(field(fieldOrder))
                        .seek(lastId)
                        .limit(pageSize);
            } else {
                where.orderBy(field(fieldOrder))
                        .limit(pageSize);
            }
        }
        return where;
    }

}
