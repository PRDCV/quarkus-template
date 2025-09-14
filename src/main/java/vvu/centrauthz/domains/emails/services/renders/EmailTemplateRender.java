package vvu.centrauthz.domains.emails.services.renders;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateException;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Locale;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import vvu.centrauthz.domains.emails.models.EmailPart;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.models.TemplatePath;
import vvu.centrauthz.errors.BadRequestError;

@ApplicationScoped
@Slf4j
public class EmailTemplateRender {

    private static final String TEMPLATE_FMT = "Template %s with language %s not supported";

    private final Engine quteEngine;

    public EmailTemplateRender(Engine quteEngine) {
        this.quteEngine = quteEngine;
    }

    private Template loadDefaultTemplate(TemplatePath templatePath) {
        templatePath = templatePath.toBuilder().locale(Locale.getDefault()).build();
        return quteEngine.getTemplate(templatePath.toTemplatePath());
    }

    private Template loadTemplate(TemplatePath templatePath) {
        String id = templatePath.toTemplatePath();

        log.info("loadTemplate {}", id);

        var template = quteEngine.getTemplate(id);

        if (Objects.isNull(template)) {
            return loadDefaultTemplate(templatePath);
        }

        return template;
    }

    private String render(EmailRequest request, EmailPart part) {
        try {
            var templatePath = TemplatePath.from(request, part);
            var template = loadTemplate(templatePath);

            if (Objects.isNull(template)) {
                var message = String.format(TEMPLATE_FMT, request.template(), request.lang());
                throw new BadRequestError("EMAIL_TEMPLATE_NOT_SUPPORTED", message);
            }

            return template.render(request.attributes());
        } catch (TemplateException e) {
            throw new BadRequestError(e.getMessage());
        }
    }

    public String renderBody(EmailRequest request) {
        return render(request, EmailPart.BODY);
    }

    public String renderSubject(EmailRequest request) {
        return render(request, EmailPart.SUBJECT);
    }
}
