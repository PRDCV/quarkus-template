package vvu.centrauthz.utilities;

import io.quarkus.runtime.util.StringUtil;
import java.util.Locale;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocaleTools {
    public static Locale toLocale(String lang) {
        if (StringUtil.isNullOrEmpty(lang)) {
            return null;
        }

        var locale = Locale.forLanguageTag(lang);

        if (Objects.isNull(locale) || locale.toString().isBlank()) {
            return null;
        }

        return locale;
    }
}
