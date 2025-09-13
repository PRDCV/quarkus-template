package vvu.centrauthz.domains.emails.utilities;

import io.quarkus.qute.i18n.MessageBundle;

@MessageBundle("email_subjects")
public interface EmailSubjects {
    String emailSubjectVerify(String name);
    String emailSubjectTempPassword(String name);
}
