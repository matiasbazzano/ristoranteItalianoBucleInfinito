package com.example.bucleinfinito.cliente;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BAD PRACTICE [S112]: throws Exception genérico en la firma del test
@SpringBootTest
@AutoConfigureMockMvc
class ReservaTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFormularioReservaAccesible() throws Exception {
        mockMvc.perform(get("/clientes/1/reservas/new")).andExpect(status().isOk());
    }
}
