package vvu.centrauthz.domains.emails.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Builder(toBuilder = true)
public record EmailRequest(
        @NotBlank
        String template,

        String lang,

        @NotEmpty
        List<Receiver> to,

        List<Receiver> cc,

        List<Receiver> bcc,

        Map<String, String> attributes
) {
    public EmailRequest {
        if (Objects.isNull(to)) {
            to = List.of();
        }

        if (Objects.isNull(attributes)) {
            attributes = Map.of();
        }

        if (Objects.isNull(lang)) {
            lang = "en";
        }

    }
}
