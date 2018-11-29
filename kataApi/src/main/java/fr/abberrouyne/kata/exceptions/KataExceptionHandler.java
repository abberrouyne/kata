package fr.abberrouyne.kata.exceptions;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import fr.abberrouyne.kata.exception.KataCommonsException;
import fr.abberrouyne.kata.rest.resources.ErrorResource;

/**
 * Maps exceptions to HTTP codes
 */

@RestControllerAdvice
public class KataExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(KataCommonsException.class)
    @ResponseBody
    public ErrorResource handleServlogCommonsException(Exception ex, WebRequest request) {
        return new ErrorResource("INTERNAL_ERROR", ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(KataException.class)
    @ResponseBody
    public ErrorResource handleServlogException(KataException ex) {
        return ex.getErrorResource();
    }

    @ExceptionHandler({ DataFormatException.class, TransactionSystemException.class, ConstraintViolationException.class, RuntimeException.class, MethodArgumentNotValidException.class,
            NoHandlerFoundException.class, Exception.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResource processValidationError(Exception ex, WebRequest request) {
        return new ErrorResource("INCORRECT PARAMETERS / INVALID OPERATION", ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ErrorResource handleResourceNotFoundException(Exception ex, WebRequest request) {
        return new ErrorResource("RESOURCES NOT FOUND", ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}