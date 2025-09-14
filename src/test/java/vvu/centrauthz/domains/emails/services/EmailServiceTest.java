package vvu.centrauthz.domains.emails.services;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.models.Recipient;
import vvu.centrauthz.domains.emails.services.renders.EmailTemplateRender;
import vvu.centrauthz.utilities.Context;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

class EmailServiceTest {

    @Test
    void send_anEmail_success() {
        EmailTemplateRender templateRender = Mockito.mock(EmailTemplateRender.class);
        Mailer mailer = Mockito.mock(Mailer.class);
        EmailService emailService = new EmailService(mailer, templateRender);
        var subject = UUID.randomUUID().toString();
        var body = UUID.randomUUID().toString();

        ArgumentCaptor<EmailRequest> requestCaptor = ArgumentCaptor.forClass(EmailRequest.class);

        var context = Context.of(UUID.randomUUID());
        var recipient = Recipient.builder()
                .email("hoangviet.vu@vn.panasonic.com")
                .name("Hoang Viet Vu")
                .build();

        var attributes = Map.of(
                "name", recipient.name(),
                "code", "123456",
                "link", "https://example.com"
        );

        var request = EmailRequest
                .builder()
                .to(List.of(recipient))
                .template("verify")
                .lang("en")
                .attributes(attributes)
                .build();

        Mockito.when(templateRender.renderSubject(requestCaptor.capture())).thenReturn(subject);
        Mockito.when(templateRender.renderBody(requestCaptor.capture())).thenReturn(body);

        emailService.send(request, context);

        Mockito.verify(templateRender, Mockito.times(1)).renderSubject(request);
        Mockito.verify(templateRender, Mockito.times(1)).renderBody(request);
        Mockito.verify(mailer, Mockito.times(1)).send(any(Mail.class));
        Assertions.assertSame(request, requestCaptor.getAllValues().getFirst());
        Assertions.assertSame(request, requestCaptor.getAllValues().getLast());

    }
}