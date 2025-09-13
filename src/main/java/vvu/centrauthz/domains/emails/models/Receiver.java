package vvu.centrauthz.domains.emails.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder(toBuilder = true)
public record Receiver(
    @NotBlank
    @Email
    String email,

    @NotBlank
    String name
) {
}
