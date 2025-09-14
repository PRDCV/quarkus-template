package vvu.centrauthz.domains.emails.models;

public enum EmailFormat {
        HTML,
        TEXT;
    public String toExtension() {
        if (this == TEXT) {
            return "txt";
        } else {
            return "html";
        }
    }
}
