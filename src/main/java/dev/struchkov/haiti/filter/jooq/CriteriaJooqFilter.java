package dev.struchkov.haiti.filter.jooq;

import dev.struchkov.haiti.filter.Filter;
import dev.struchkov.haiti.filter.FilterQuery;
import dev.struchkov.haiti.filter.jooq.exception.FilterJooqHaitiException;
import dev.struchkov.haiti.filter.jooq.page.PageableOffset;
import dev.struchkov.haiti.filter.jooq.page.PageableSeek;
import dev.struchkov.haiti.utils.Assert;
import lombok.NonNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Operator;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSeekStepN;
import org.jooq.SortField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.jooq.impl.DSL.condition;
import static org.jooq.impl.DSL.field;

public class CriteriaJooqFilter implements Filter {

    private final List<Condition> andConditions = new ArrayList<>();
    private final List<Condition> orConditions = new ArrayList<>();
    private final List<Condition> notConditions = new ArrayList<>();
    private final List<JoinTable> joinTables = new ArrayList<>();

    private final String table;
    private final DSLContext dsl;

    private PageableOffset offset;
    private PageableSeek seek;
    private List<SortContainer> sorts = new ArrayList<>();

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
        // FIXME: Добавить поддержку
        throw new IllegalStateException("Операция отрицания пока не поддерживается");
    }

    @Override
    public Filter not(Consumer<FilterQuery> query) {
        // FIXME: Добавить поддержку
        throw new IllegalStateException("Операция отрицания пока не поддерживается");
    }

    public Filter page(@NonNull PageableOffset offset) {
        Assert.isNull(seek, () -> new FilterJooqHaitiException("Нельзя установить два типа пагинации одновременно"));
        this.offset = offset;
        return this;
    }

    public Filter page(@NonNull PageableSeek seek) {
        Assert.isNull(offset, () -> new FilterJooqHaitiException("Нельзя установить два типа пагинации одновременно"));
        this.seek = seek;
        return this;
    }

    public Filter sort(@NonNull SortContainer container) {
        if (container.getFieldName() != null) {
            this.sorts.add(container);
        }
        return this;
    }

    public Filter sort(String field, SortType sortType) {
        if (field != null) {
            this.sorts.add(SortContainer.of(field, sortType));
        }
        return this;
    }

    public Filter sort(String field) {
        if (field != null) {
            this.sorts.add(SortContainer.of(field));
        }
        return this;
    }

    public Filter join(@NonNull JoinTable... joinTables) {
        // FIXME: Добавить поддержку
        throw new IllegalStateException("Операция пока не поддерживается");
//        this.joinTables.addAll(Arrays.stream(joinTables).collect(Collectors.toList()));
//        return this;
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
        final SelectSeekStepN<Record> sort = setSort(where);
        setPaginationOffset(where);
        setPaginationSeek(sort);
        return where;
    }

    private SelectSeekStepN<Record> setSort(SelectConditionStep<Record> where) {
        if (!sorts.isEmpty()) {
            final List<SortField<Object>> newSorts = new ArrayList<>();
            for (SortContainer sort : sorts) {
                final SortType sortType = sort.getType();
                final String fieldName = sort.getFieldName();
                if (SortType.ASC.equals(sortType)) {
                    newSorts.add(field(fieldName).asc());
                } else {
                    newSorts.add(field(fieldName).desc());
                }
            }
            return where.orderBy(newSorts);
        }
        return null;
    }

    private void setPaginationSeek(SelectSeekStepN<Record> sort) {
        if (seek != null) {
            Assert.isNotNull(sort, () -> new FilterJooqHaitiException("При использовании пагинации типа seek необходимо указать сортировку"));
            final Integer pageSize = seek.getPageSize();
            final Object lastId = seek.getLastId();
            if (pageSize != null) {
                if (lastId != null) {
                    sort
                            .seek(lastId)
                            .limit(pageSize);
                } else {
                    sort
                            .limit(pageSize);
                }
            }
        }
    }

    private void setPaginationOffset(SelectConditionStep<Record> where) {
        if (offset != null) {
            final int pageNumber = offset.getPageNumber();
            final int pageSize = offset.getPageSize();
            final int offsetNumber = (pageNumber + 1) * pageSize - pageSize;
            where.limit(pageSize).offset(offsetNumber);
        }
    }
}
