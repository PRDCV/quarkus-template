package vvu.centrauthz.domains.emails.models;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TemplatePathTest {

    @Test
    void toTemplatePath_withLocale_fullPath() {
        Locale locale = Locale.JAPANESE;
        TemplatePath templatePath = TemplatePath.builder()
                .path("emails")
                .name("test")
                .format(EmailFormat.html)
                .locale(locale)
                .build();
        assertEquals("emails/test_ja.html", templatePath.toTemplatePath());
    }

    @Test
    void toTemplatePath_noLocale_noLocale() {
        TemplatePath templatePath = TemplatePath.builder()
                .path("emails")
                .name("test")
                .format(EmailFormat.html)
                .build();
        assertEquals("emails/test.html", templatePath.toTemplatePath());
    }
}