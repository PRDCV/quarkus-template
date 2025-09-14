package vvu.centrauthz.domains.emails.models;

import io.quarkus.mailer.Mail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import lombok.Builder;

@Builder(toBuilder = true)
public record EmailContent(
        @NotBlank
        String subject,

        @NotBlank
        String content,

        EmailFormat format,

        @NotEmpty
        List<Recipient> to,

        List<Recipient> cc,

        List<Recipient> bcc
) {
    public static EmailContent from(EmailRequest request) {
        return EmailContent.builder()
                .to(request.to())
                .cc(request.cc())
                .bcc(request.bcc())
                .format(request.format())
                .build();
    }

    public Mail toMail() {
        var mail = EmailFormat.TEXT.equals(format)
                ? Mail.withText(Recipient.toString(to), subject, content)
                : Mail.withHtml(Recipient.toString(to), subject, content);
        if (Objects.nonNull(cc)) {
            mail.setCc(cc.stream().map(Recipient::email).toList());
        }

        if (Objects.nonNull(bcc)) {
            mail.setCc(bcc.stream().map(Recipient::email).toList());
        }

        return mail;
    }
}
