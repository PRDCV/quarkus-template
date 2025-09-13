package vvu.centrauthz.utilities;

import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LocaleToolsTest {

    @Test
    void toLocale_ja_ja() {
        String lang = "ja";
        Locale locale = LocaleTools.toLocale(lang);
        assertEquals(Locale.JAPANESE, locale);
    }

    @Test
    void toLocale_invalid_null() {
        String lang = UUID.randomUUID().toString();
        Locale locale = LocaleTools.toLocale(lang);
        assertNull(locale);
    }
}