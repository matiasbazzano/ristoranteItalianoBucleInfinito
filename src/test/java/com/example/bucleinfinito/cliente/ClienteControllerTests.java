package com.example.bucleinfinito.cliente;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BAD PRACTICE [S2699]: test without any assertion.
// BAD PRACTICE [S2925]: Thread.sleep() in test.
@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testListarClientesNoAserta() throws Exception {
        mockMvc.perform(get("/clientes"));
    }

    @Test
    void testConSleep() throws Exception {
        Thread.sleep(1000);
        mockMvc.perform(get("/clientes")).andExpect(status().isOk());
    }

    // BAD PRACTICE [S1607]: @Disabled without explanation
    @Disabled
    @Test
    void testBuscarPorApellido() throws Exception {
        mockMvc.perform(get("/clientes?apellido=Rossi")).andExpect(status().isOk());
    }
}
