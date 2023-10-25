package org.health.imperialhealthapp.models;

public enum Status {

    SUCCESS(200), FAILURE(400), ERROR(500);

    Status(int code) {
        this.code = code;
    }

    private int code;

}
