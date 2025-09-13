package vvu.centrauthz.domains.emails.services.renders;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import vvu.centrauthz.domains.emails.models.EmailPart;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.models.TemplatePath;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
@Slf4j
public class EmailTemplateRender {

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
        var template = quteEngine.getTemplate(id);
        if (Objects.isNull(template)) {
            return loadDefaultTemplate(templatePath);
        }

        return template;
    }

    private String render(EmailRequest request, EmailPart part) {
        var templatePath = TemplatePath.from(request, part);
        var template = loadTemplate(templatePath);
        return template.render(request.attributes());
    }

    public String renderBody(EmailRequest request) {
        return render(request, EmailPart.body);
    }

    public String renderSubject(EmailRequest request) {
        return render(request, EmailPart.subject);
    }

}
