package com.neonpulse.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.neonpulse.domain.entity.Customer;
import com.neonpulse.domain.exception.InvalidEmailException;
import com.neonpulse.domain.valueobject.Email;

class CustomerTest {

    @Test //Verifica que `Email` rechace nulos/formatos inválidos sin `@` y normalice el texto a minúsculas.
    @DisplayName("Email auto-valida formato defensivamente en constructor compacto")
    void testEmailValidation() {
        assertThrows(InvalidEmailException.class, () -> new Email("correo-invalido"));
        assertThrows(InvalidEmailException.class, () -> new Email(null));
        assertThrows(InvalidEmailException.class, () -> new Email("  "));

        Email email = new Email("  USER@Domain.COM ");
        assertEquals("user@domain.com", email.value());
    }

    @Test //Verifica que la entidad `Customer` mantenga su Identidad Única (`id`) inmutable tras actualizar datos.
    @DisplayName("Customer mantiene su Identidad Única")
    void testCustomerEntity() {
        Email email = new Email("cliente@neonpulse.com");
        Customer customer = new Customer("CUST-1", "Juan Pérez", email);

        assertEquals("CUST-1", customer.getId());
        assertEquals("Juan Pérez", customer.getName());
        assertEquals(email, customer.getEmail());

        Email newEmail = new Email("juan.perez@neonpulse.com");
        customer.updateEmail(newEmail);
        assertEquals(newEmail, customer.getEmail());
    }
}
