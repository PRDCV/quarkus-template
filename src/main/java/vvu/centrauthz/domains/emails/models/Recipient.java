package vvu.centrauthz.domains.emails.models;

import io.quarkus.runtime.util.StringUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder(toBuilder = true)
public record Recipient(
        @NotBlank
        @Email
        String email,

        String name
) {
    @Override
    public String toString() {

        if (StringUtil.isNullOrEmpty(name)) {
            return email;
        }

        return String.format("%s <%s>", name, email);
    }

    public static String toString(List<Recipient> recipients) {
        return recipients.stream().map(Recipient::toString).collect(Collectors.joining(","));
    }
}
