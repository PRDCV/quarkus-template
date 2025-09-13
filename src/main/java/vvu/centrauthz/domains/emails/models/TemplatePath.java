package vvu.centrauthz.domains.emails.models;

import io.quarkus.runtime.util.StringUtil;
import lombok.Builder;
import vvu.centrauthz.domains.emails.utilities.Values;
import vvu.centrauthz.utilities.LocaleTools;

import java.util.Locale;
import java.util.Objects;

@Builder(toBuilder = true)
public record TemplatePath(
        String path,
        String name,
        EmailFormat format,
        Locale locale,
        EmailPart part
) {
    public TemplatePath {
        if (StringUtil.isNullOrEmpty(path)) {
            path = Values.BASE_PATH;
        }

        if (Objects.isNull(part)) {
            throw new IllegalArgumentException("need to specify body or subject");
        }

        if (StringUtil.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("name is required");
        }

        if (Objects.equals(part, EmailPart.subject)) {
            format = EmailFormat.text;
        }

        if (Objects.isNull(format)) {
            format = EmailFormat.html;
        }

    }

    public String toTemplatePath() {
        if (Objects.isNull(locale)) {
            return String.format(Values.DEF_TEMP_NAME_FMT, path, name, part.name(), format.name());
        } else {
            return String.format(Values.TEMP_NAME_FMT, path, name, part.name(), locale.getLanguage(),format.name());
        }
    }

    public static TemplatePath from(EmailRequest emailRequest, EmailPart part, String path, boolean ignoreLocale) {
        return TemplatePath
                .builder()
                .path(path)
                .name(emailRequest.template())
                .locale(ignoreLocale ? null : LocaleTools.toLocale(emailRequest.lang()))
                .format(emailRequest.format())
                .part(part)
                .build();
    }

    public static TemplatePath from(EmailRequest emailRequest, EmailPart part, boolean ignoreLocale) {
        return from(emailRequest, part, Values.BASE_PATH, ignoreLocale);
    }

    public static TemplatePath from(EmailRequest emailRequest, EmailPart part) {
        return from(emailRequest, part, Values.BASE_PATH, false);
    }
}