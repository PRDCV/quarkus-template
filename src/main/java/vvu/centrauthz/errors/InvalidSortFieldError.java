package vvu.centrauthz.errors;

import vvu.centrauthz.models.Error;

/**
 * InvalidSortFieldError.
 */
public class InvalidSortFieldError extends AppError {
    public InvalidSortFieldError(Error e) {
        super(e);
    }

    public InvalidSortFieldError(String message) {
        super("INVALID_SORT_FIELD", message);
    }

    public InvalidSortFieldError(Throwable e) {
        super("INVALID_SORT_FIELD", e);
    }
}
