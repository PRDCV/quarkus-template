package vvu.centrauthz.domains.emails.controllers;

import jakarta.validation.Valid;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import vvu.centrauthz.domains.emails.models.EmailRequest;
import vvu.centrauthz.domains.emails.services.EmailService;
import vvu.centrauthz.utilities.Context;

@Path("/v0/emails")
public class EmailController {

    private final EmailService emailService;

    /**
     * EmailController constructor.
     *
     * @param emailService Email service
     */
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Send an email.
     * POST /api/v1/emails/actions/send
     *
     * @param userId  UUID represent request sender
     * @param request the email content to be sent
     * @return HTTP 202 Accepted
     */
    @POST
    @Path("/actions/send")
    public Response sendEmail(
        @Valid @HeaderParam("X-Auth-Request-User-Id") java.util.UUID userId,
        @Valid EmailRequest request) {
        return Context
            .of(userId)
            .execute(ctx -> {
                emailService.send(request, ctx);
                return Response.accepted().build();
            });
    }

}
