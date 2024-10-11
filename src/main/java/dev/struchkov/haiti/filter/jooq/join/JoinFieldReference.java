package dev.struchkov.haiti.filter.jooq.join;

import java.util.Set;

public class JoinFieldReference {

    private final String baseField;
    private final String referenceField;

    public JoinFieldReference(String baseField, String referenceField) {
        this.baseField = baseField;
        this.referenceField = referenceField;
    }

    public String getBaseField() {
        return baseField;
    }

    public String getReferenceField() {
        return referenceField;
    }

    public static JoinFieldReference of(String baseField, String referenceField) {
        return new JoinFieldReference(baseField, referenceField);
    }

    public static Set<JoinFieldReference> ofSingletonSet(String baseField, String referenceField) {
        return Set.of(new JoinFieldReference(baseField, referenceField));
    }

}