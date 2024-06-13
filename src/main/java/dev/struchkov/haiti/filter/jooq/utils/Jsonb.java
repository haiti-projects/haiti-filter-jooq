package dev.struchkov.haiti.filter.jooq.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Jsonb {

    public static String jsonbField(String field, String jsonField) {
        String sql;
        if (jsonField.contains(".")) {
            final List<String> hierarchyFields = Arrays.stream(jsonField.split("\\."))
                    .map(el -> "'" + el + "'")
                    .collect(Collectors.toList());
            final String lastElement = hierarchyFields.remove(hierarchyFields.size() - 1);
            final String fieldPath = String.join(" -> ", hierarchyFields) + " ->> " + lastElement;
            sql = "(%s #>> '{}')::jsonb -> %s".formatted(field, fieldPath);
        } else {
            final String fieldPath = "'" + jsonField + "'";
            sql = "(%s #>> '{}')::jsonb ->> %s".formatted(field, fieldPath);
        }
        return sql;
    }

}