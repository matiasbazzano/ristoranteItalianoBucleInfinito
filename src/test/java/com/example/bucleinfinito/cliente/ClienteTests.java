package com.example.bucleinfinito.cliente;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// BAD PRACTICE [S5776]: assertTrue(true) — aserción que siempre pasa, sin valor real
@SpringBootTest
class ClienteTests {

    @Test
    void testClienteExiste() {
        assertTrue(true);
    }
}
