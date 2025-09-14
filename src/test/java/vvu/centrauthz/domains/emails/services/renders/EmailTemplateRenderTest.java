package vvu.centrauthz.domains.emails.services.renders;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import vvu.centrauthz.domains.emails.models.EmailFormat;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.models.Recipient;
import vvu.centrauthz.errors.BadRequestError;
import vvu.centrauthz.profiles.NoHttpTestProfile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestProfile(NoHttpTestProfile.class)
class EmailTemplateRenderTest {

    @Inject
    EmailTemplateRender emailTemplateRender;

    @Test
    void renderBody_templateExists_loadCorrectBody() {
        var name = "Vu Hoang Viet";

        var recipient = Recipient
                .builder()
                .email("hoangviet1.vu@vn.panasonic.com")
                .name(name)
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
                .format(EmailFormat.HTML)
                .attributes(attributes)
                .build();

        assertDoesNotThrow(() -> emailTemplateRender.renderBody(request));

    }

    @Test
    void renderBody_templateNotExists_BadRequestError() {
        var name = "Vu Hoang Viet";

        var recipient = Recipient
                .builder()
                .email("hoangviet1.vu@vn.panasonic.com")
                .name(name)
                .build();
        var attributes = Map.of(
                "name", recipient.name(),
                "code", "123456",
                "link", "https://example.com"
        );

        var request = EmailRequest
                .builder()
                .to(List.of(recipient))
                .template(UUID.randomUUID().toString())
                .format(EmailFormat.HTML)
                .attributes(attributes)
                .build();

        assertThrowsExactly(BadRequestError.class, () -> {
            emailTemplateRender.renderBody(request);
        });

    }

    @Test
    void renderBody_illegalAttributes_BadRequestError() {
        var name = "Vu Hoang Viet";

        var recipient = Recipient
                .builder()
                .email("hoangviet1.vu@vn.panasonic.com")
                .name(name)
                .build();
        var attributes = Map.of(
                "code", "123456",
                "link", "https://example.com"
        );

        var request = EmailRequest
                .builder()
                .to(List.of(recipient))
                .template(UUID.randomUUID().toString())
                .format(EmailFormat.HTML)
                .attributes(attributes)
                .build();

        assertThrowsExactly(BadRequestError.class, () -> {
            emailTemplateRender.renderBody(request);
        });

    }

    @Test
    void renderSubject_templateExists_subject() {
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

        String result = emailTemplateRender.renderSubject(request);
        assertEquals("Email Verification for " + recipient.name(), result);
    }
}