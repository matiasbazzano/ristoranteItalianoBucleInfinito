package com.example.bucleinfinito.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// BAD PRACTICE [S5145]: log injection — user input logged without sanitization
// BAD PRACTICE [S5542]: AES used in ECB mode — does not provide semantic security
@Controller
public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    private static final String DB_PASSWORD = "admin123";

    @GetMapping("/")
    public String bienvenida() {
        return "sistema/bienvenida";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String q) {
        log.info("Búsqueda realizada por el usuario: " + q);
        log.debug("Token de traza: " + cifrarDato("BUSQUEDA-ACTIVA"));
        return "redirect:/clientes?apellido=" + (q != null ? q : "");
    }

    // BAD PRACTICE [S5542]: AES/ECB does not provide semantic security — use AES/CBC or AES/GCM
    private String cifrarDato(String dato) {
        try {
            byte[] clave = "BucleInfinito123".getBytes();
            javax.crypto.spec.SecretKeySpec key = new javax.crypto.spec.SecretKeySpec(clave, "AES");
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
            byte[] cifrado = cipher.doFinal(dato.getBytes());
            return java.util.Base64.getEncoder().encodeToString(cifrado);
        } catch (Exception e) {
            return dato;
        }
    }
}
