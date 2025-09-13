package vvu.centrauthz.domains.emails.utilities;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

import java.util.Map;

@CheckedTemplate(basePath = "emails")
public class EmailTemplates {
    static native TemplateInstance tempPassword(Map<String, Object> params);
}
