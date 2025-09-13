package vvu.centrauthz.domains.emails.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record EmailContent(
    @NotBlank
    String subject,

    @NotBlank
    String content,

    @NotEmpty
    List<Receiver> to,

    List<Receiver> cc,

    List<Receiver> bcc
) {
    public static EmailContent from(EmailRequest request) {
        return EmailContent.builder().to(request.to()).cc(request.cc()).bcc(request.bcc()).build();
    }
}
