package org.health.imperialhealthapp.models;

import org.health.imperialhealthapp.exceptions.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Executor {

    public <T> ResponseEntity<T> execute(Executable<T> executable) {
        try {
            return executable.execute();
        } catch (InvalidRequestException e) {
            return new ResponseEntity(
                    GeneralResult.<T>builder().data(null).status(Status.FAILURE).build(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    GeneralResult.<T>builder().data(null).status(Status.ERROR).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}