package vvu.centrauthz.domains.emails.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.models.Recipient;
import vvu.centrauthz.domains.emails.services.EmailService;
import vvu.centrauthz.utilities.Context;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmailControllerTest {

    @Test
    void sendEmail() {
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
        var service = Mockito.mock(EmailService.class);
        var controller = new EmailController(service);
        var uuid = UUID.randomUUID();

        ArgumentCaptor<EmailRequest> requestCaptor = ArgumentCaptor.forClass(EmailRequest.class);
        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);

        Mockito.doNothing().when(service).send(requestCaptor.capture(), contextCaptor.capture());

        controller.sendEmail(uuid, request);

        Mockito.verify(service, Mockito.times(1)).send(Mockito.any(EmailRequest.class), Mockito.any(Context.class));
        assertEquals(uuid, contextCaptor.getValue().user().userId());
        assertSame(request, requestCaptor.getValue());
    }
}