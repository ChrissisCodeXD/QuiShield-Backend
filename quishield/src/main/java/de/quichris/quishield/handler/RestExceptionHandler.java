package de.quichris.quishield.handler;

import de.quichris.quishield.error.ApiError;
import de.quichris.quishield.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AccountNotFound.class)
    protected ResponseEntity<Object> handleAccountNotFound(
            AccountNotFound ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AccountDuplicate.class)
    protected ResponseEntity<Object> handleAccountDuplicate(
            AccountDuplicate ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(WrongPassword.class)
    protected ResponseEntity<Object> handleWrongPassword(
            WrongPassword ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(NotAuthorized.class)
    protected ResponseEntity<Object> handleNotAuthorized(
            NotAuthorized ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(PasswordNotFound.class)
    protected ResponseEntity<Object> handlePasswordNotFound(
            PasswordNotFound ex
    ) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }


}

