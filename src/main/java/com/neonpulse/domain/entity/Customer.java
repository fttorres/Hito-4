package com.neonpulse.domain.entity;

import com.neonpulse.domain.valueobject.Email;

/**
 * Entidad Customer con Identidad Única (Sin anotaciones de JPA/Spring).
 */
public class Customer {

    private final String id;
    private String name;
    private Email email;

    public Customer(String id, String name, Email email) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID del cliente no puede ser nulo.");
        }
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public void updateEmail(Email newEmail) {
        if (newEmail == null) {
            throw new IllegalArgumentException("El correo no puede ser nulo");
        }
        this.email = newEmail;
    }
}
