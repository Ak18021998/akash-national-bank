package akash_national_bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;
import akash_national_bank.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public String handleConstraintViolationException(
        ConstraintViolationException exception) {

    return exception.getConstraintViolations()
            .iterator()
            .next()
            .getMessage();
}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
   public ErrorResponse handleResourceNotFoundException(
        ResourceNotFoundException exception) {

    return new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage()
    );
}

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateResourceException(
        DuplicateResourceException exception) {

    return new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            exception.getMessage()
    );
}

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(
        BadRequestException exception) {

    return new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            exception.getMessage()
    );
}
    @ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ErrorResponse handleValidationException(
        MethodArgumentNotValidException exception) {

    return new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            exception.getBindingResult()
                    .getFieldErrors()
                    .get(0)
                    .getDefaultMessage()
    );
}
}