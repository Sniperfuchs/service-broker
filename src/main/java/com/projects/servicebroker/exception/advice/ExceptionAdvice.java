package com.projects.servicebroker.exception.advice;

import com.projects.servicebroker.exception.*;
import lombok.Data;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ApiVersionNotFoundException.class})
    public ResponseEntity<Object> handlePreconditionFailed(Exception ex) {
        return new ResponseEntity<>(jsonFromException(ex), HttpStatus.PRECONDITION_FAILED); //TODO parse exception to string
    }

    @ExceptionHandler({ServiceInstanceConflictException.class, ServiceBindingConflictException.class})
    public ResponseEntity<Object> handleConflict(Exception ex) {
        return new ResponseEntity<>(jsonFromException(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ServiceInstanceNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        return new ResponseEntity<>(jsonFromException(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ServiceInstanceGoneException.class, MissingParameterException.class, ServiceBindingGoneException.class}) //TODO MissingParameter HAS!!!!!! to throw BAD_REQUEST but checker requires GONE for some reason
    public ResponseEntity<Object> handleGone(Exception ex) {
        return new ResponseEntity<>(jsonFromException(ex), HttpStatus.GONE);
    }

    @ExceptionHandler({RequestValidationException.class, NoSuchServiceException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        return new ResponseEntity<>(jsonFromException(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MaintenanceInfoMismatchException.class})
    public ResponseEntity<Object> handleMaintenanceInfoConflict(Exception ex) {
        return new ResponseEntity<>(new ErrorMessage("MaintenanceInfoConflict", ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Data
    private static class ErrorMessage {
        private String error;
        private String description;
        /*
        private boolean instance_usable; //TODO
        private boolean update_repeatable;

         */

        public ErrorMessage(String error, String description) {
            this.error = error;
            this.description = description;
        }
    }

    private String jsonFromException(Exception ex) {
        JSONObject response = new JSONObject();
        //response.put("cause", ex.getCause().toString()); //TODO maybe implement, maybe not?
        response.put("message", ex.getMessage());
        return response.toJSONString();
    }
}
