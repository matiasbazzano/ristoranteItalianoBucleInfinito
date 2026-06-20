package com.example.bucleinfinito.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrashController {

    @GetMapping("/oops")
    public String triggerException() {
        throw new RuntimeException("Error de demostración: El Bucle Infinito ha entrado en un bucle.");
    }
}
