package vvu.centrauthz.domains.emails.models;

public enum EmailPart {
    BODY,
    SUBJECT;

    public String toPartName() {
        return name().toLowerCase();
    }
}
