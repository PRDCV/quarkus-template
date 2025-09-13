package vvu.centrauthz.domains.emails.services;

import io.quarkus.mailer.Mailer;
import jakarta.inject.Singleton;
import vvu.centrauthz.domains.emails.models.EmailContent;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.services.renders.EmailTemplateRender;
import vvu.centrauthz.utilities.Context;

@Singleton
public class EmailService {

    private final EmailTemplateRender templateRender;
    private final Mailer mailer;

    public EmailService(Mailer mailer, EmailTemplateRender templateRender) {
        this.templateRender = templateRender;
        this.mailer = mailer;
    }

    private EmailContent compose(EmailRequest request) {
        var subject = templateRender.renderSubject(request);
        var body = templateRender.renderBody(request);
        return EmailContent
                .from(request)
                .toBuilder()
                .subject(subject)
                .content(body)
                .build();
    }

    public void send(EmailRequest request, Context ctx) {
        var content = compose(request);
        mailer.send(content.toMail());
    }
}
