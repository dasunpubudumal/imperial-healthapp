package org.health.imperialhealthapp.config;

import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface Executable<T> {

    ResponseEntity<T> execute();

}