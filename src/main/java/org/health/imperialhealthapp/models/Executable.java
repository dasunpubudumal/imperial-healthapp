package org.health.imperialhealthapp.models;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface Executable<T> {

    ResponseEntity<T> execute();

}