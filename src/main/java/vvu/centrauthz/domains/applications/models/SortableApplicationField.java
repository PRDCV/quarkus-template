package vvu.centrauthz.domains.applications.models;

import vvu.centrauthz.errors.InvalidSortFieldError;

/**
 * Enum representing sortable fields for application queries.
 * Maps camelCase input values to corresponding database column names.
 */
public enum SortableApplicationField {
    APPLICATION_KEY("application_key"),
    NAME("name"),
    CREATED_DATE("created_at"),
    UPDATED_DATE("updated_at");

    private final String value;

    SortableApplicationField(String value) {
        this.value = value;
    }

    public static SortableApplicationField from(String value) {
        return switch (value) {
            case "applicationKey" -> APPLICATION_KEY;
            case "name" -> NAME;
            case "createdDate" -> CREATED_DATE;
            case "updatedDate" -> UPDATED_DATE;
            default ->
                    throw new InvalidSortFieldError(String.format("Invalid sort field: %s", value));
        };
    }

    @Override
    public String toString() {
        return value;
    }
}