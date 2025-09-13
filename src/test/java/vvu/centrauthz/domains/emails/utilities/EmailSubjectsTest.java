package vvu.centrauthz.domains.emails.utilities;

import io.quarkus.qute.i18n.Localized;
import io.quarkus.qute.i18n.MessageBundles;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import vvu.centrauthz.profiles.NoHttpTestProfile;

@Slf4j
@QuarkusTest
@TestProfile(NoHttpTestProfile.class)
class EmailSubjectsTest {

    @Inject
    EmailSubjectBundle emailSubjects;


    @Test
    void testLoadSubject() {
        var name = "Viet";
        var subject =
            MessageBundles.get(EmailSubjectBundle.class, Localized.Literal.of("ja")).verify(name);

        System.out.println(subject);
    }
}