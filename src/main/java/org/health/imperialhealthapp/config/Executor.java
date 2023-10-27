package org.health.imperialhealthapp.config;

import lombok.extern.slf4j.Slf4j;
import org.health.imperialhealthapp.exceptions.InternalServerException;
import org.health.imperialhealthapp.exceptions.InvalidRequestException;
import org.health.imperialhealthapp.models.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class Executor {

    public <T> ResponseEntity<T> execute(Executable<T> executable) {
        try {
            return executable.execute();
        } catch (InternalServerException | InvalidRequestException e) {
            log.error(e.getMessage());
            return new ResponseEntity(
                    GeneralResult.<String>builder().data(e.getMessage()).status(Status.FAILURE).build(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(
                    GeneralResult.<T>builder().data(null).status(Status.ERROR).build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}