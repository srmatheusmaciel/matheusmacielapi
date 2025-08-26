package br.edu.infnet.matheusmacielapi.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ValidacaoNegocioException.class)
    public ResponseEntity<StandardError> validacaoNegocio(ValidacaoNegocioException e, HttpServletRequest request) {
        String error = "Erro de validação";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    private static class StandardError {
        public Instant timestamp;
        public Integer status;
        public String error;
        public String message;
        public String path;

        public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Erro de validação de campos";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ValidationError err = new ValidationError(Instant.now(),
                status.value(),
                error,
                "Um ou mais campos estão inválidos.",
                request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseEntity.status(status).body(err);
    }

    private static class ValidationError extends StandardError {
        private final List<FieldMessage> errors = new ArrayList<>();

        public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
            super(timestamp, status, error, message, path);
        }

        public List<FieldMessage> getErrors() {
            return errors;
        }

        public void addError(String fieldName, String message) {
            errors.add(new FieldMessage(fieldName, message));
        }
    }

    private static class FieldMessage {
        public String fieldName;
        public String message;

        public FieldMessage(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }
    }



}
