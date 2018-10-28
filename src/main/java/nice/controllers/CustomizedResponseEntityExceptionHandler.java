package nice.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import nice.dto.ErrorDto;
import nice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {InvalidFormatException.class})
    public final ResponseEntity handleIllegalArgumentException(InvalidFormatException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(NotValidParameterException.class)
    public final ResponseEntity<ErrorDto> handleNotValidParameterException(
            NotValidParameterException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameAlreadyTakenException.class)
    public final ResponseEntity<ErrorDto> handleUserNameAlreadyTakenException(
            UserNameAlreadyTakenException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(TaskNameAlreadyTakenException.class)
    public final ResponseEntity<ErrorDto> handleTaskNameAlreadyTakenException(
            TaskNameAlreadyTakenException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleTaskNotFoundException(
            TaskNotFoundException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TodoListNameAlreadyTakenException.class)
    public final ResponseEntity<ErrorDto> handleTodoListNameAlreadyTakenException(
            TodoListNameAlreadyTakenException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TodoListNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleTodoListNotFoundException(
            TodoListNotFoundException ex, WebRequest request) {

        ErrorDto errorDetails = new ErrorDto(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}
