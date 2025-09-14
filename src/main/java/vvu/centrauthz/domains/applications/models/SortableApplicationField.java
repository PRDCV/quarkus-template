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
    UPDATED_DATE("updated_at"),
    OWNER_ID("owner_id"),
    MANAGEMENT_GROUP_ID("management_group_id");

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
            case "ownerId" -> OWNER_ID;
            case "managementGroupId" -> MANAGEMENT_GROUP_ID;
            default ->
                    throw new InvalidSortFieldError(String.format("Invalid sort field: %s", value));
        };
    }

    public String toString() {
        return value;
    }
}