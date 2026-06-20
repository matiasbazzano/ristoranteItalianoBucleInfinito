package com.example.bucleinfinito.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// BAD PRACTICE [S2259]: potential NullPointerException — orElse(null) dereferenced without null check
@Controller
public class MesaController {

    private final MesaRepository mesaRepository;

    @Autowired
    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @GetMapping("/mesas")
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaRepository.findAll());
        // BAD PRACTICE [S125]: commented-out code
        // model.addAttribute("mesasInactivas", mesaRepository.findAll().stream()
        //     .filter(m -> !m.isActiva()).collect(java.util.stream.Collectors.toList()));
        return "mesas/lista";
    }

    @GetMapping("/mesas/libre")
    public String mesaLibre(Model model) {
        Mesa primera = mesaRepository.findAll().stream()
                .filter(Mesa::isActiva)
                .findFirst()
                .orElse(null);
        // BAD PRACTICE [S2259]: 'primera' puede ser null si no hay mesas activas
        model.addAttribute("numero", primera.getNumero());
        model.addAttribute("capacidad", primera.getCapacidad());
        model.addAttribute("mesas", mesaRepository.findAll());
        return "mesas/lista";
    }
}
