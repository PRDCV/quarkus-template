package vvu.centrauthz.errors;

import vvu.centrauthz.models.Error;

public class PageTokenEncodingError extends AppError {
    public PageTokenEncodingError(Error e) {
        super(e);
    }

    public PageTokenEncodingError(String message) {
        super("ENCODING_ERROR", message);
    }

    public PageTokenEncodingError() {
        super("ENCODING_ERROR");
    }
}
