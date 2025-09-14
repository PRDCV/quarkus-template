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
                .format(EmailFormat.HTML)
                .locale(locale)
                .part(EmailPart.SUBJECT)
                .build();
        assertEquals("emails/test/subject_ja.txt", templatePath.toTemplatePath());
    }

    @Test
    void toTemplatePath_noLocale_noLocale() {
        TemplatePath templatePath = TemplatePath.builder()
                .path("emails")
                .name("test")
                .format(EmailFormat.HTML)
                .part(EmailPart.BODY)
                .build();
        assertEquals("emails/test/body.html", templatePath.toTemplatePath());
    }
}