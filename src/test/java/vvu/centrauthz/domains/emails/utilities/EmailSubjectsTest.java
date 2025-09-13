package vvu.centrauthz.domains.emails.utilities;

import io.quarkus.mailer.MailTemplate;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.MessageBundles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import vvu.centrauthz.profiles.NoHttpTestProfile;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@QuarkusTest
@TestProfile(NoHttpTestProfile.class)
class EmailSubjectsTest {

    @Inject
    EmailSubjects emailSubjects;


    @Test
    void testLoadSubject() {
        var name = "Viet";
        var subject =
            MessageBundles
                .get(EmailSubjects.class, Localized.Literal.of("ja"))
                    .emailSubjectVerify(name);

        log.info("{}", subject);

        Map<String, Object> map = new HashMap<>();

        map.put("name", name);
        map.put("temporaryPassword", "XXX");
        map.put("resetLink", "resetLink");
        map.put("expiresAt", "expiresAt");
        map.put("supportEmail", "supportEmail");

        var mail = EmailTemplates.tempPassword(map).setLocale(Locale.JAPAN).render();

        log.info("mail {}", mail);

    }
}