package vvu.centrauthz.domains.emails.utilities;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.models.Recipient;

import jakarta.inject.Inject;
import vvu.centrauthz.domains.emails.services.renders.EmailTemplateRender;
import vvu.centrauthz.profiles.NoHttpTestProfile;

import java.util.*;

@QuarkusTest
@TestProfile(NoHttpTestProfile.class)
class EmailTemplateTest {

    @Inject
    EmailTemplateRender emailTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void render() {
        var recipient = Recipient.builder()
                .email("hoangviet.vu@vn.panasonic.com")
                .name("Hoang Viet Vu")
                .build();

        var attributes = Map.of(
                "name", "Hoang Viet Vu",
                "code", "123456",
                "link", "https://example.com"
        );

        var request = EmailRequest
                .builder()
                .to(List.of(recipient))
                .template("verify")
                .lang("ja")
                .attributes(attributes)
                .build();

        String result = emailTemplate.render(request, attributes);
        System.out.println(result);

    }
}