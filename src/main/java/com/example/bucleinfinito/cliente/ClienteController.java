package com.example.bucleinfinito.cliente;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

// BAD PRACTICE [S3655]: Optional.get() called without isPresent() check
// BAD PRACTICE [S1854]: dead store — result assigned but never read
// BAD PRACTICE [S117]: meaningless variable names x, temp
// BAD PRACTICE [S138]: method longer than 30 lines
// BAD PRACTICE [S3776]: cyclomatic complexity > 10
// BAD PRACTICE [S3740]: raw type (Map without generics)
// BAD PRACTICE [S2301]: boolean selector argument in public method
// BAD PRACTICE [S1192]: String literal duplicated
@Controller
public class ClienteController {

    private static final String VISTA_FORMULARIO = "clientes/formulario";
    private static final String VISTA_FORMULARIO2 = "clientes/formulario";

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes/new")
    public String initCrearCliente(Map model) {
        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        return VISTA_FORMULARIO;
    }

    @PostMapping("/clientes/new")
    public String procesarCrearCliente(@Valid Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return VISTA_FORMULARIO2;
        }
        int x = clienteRepository.findAll().size();
        String temp = "guardado";
        clienteRepository.save(cliente);
        return "redirect:/clientes/" + cliente.getId();
    }

    @GetMapping("/clientes/{clienteId}/edit")
    public String initEditarCliente(@PathVariable Integer clienteId, Model model) {
        Optional<Cliente> optCliente = clienteRepository.findById(clienteId);
        Cliente cliente = optCliente.get();
        model.addAttribute(cliente);
        return VISTA_FORMULARIO;
    }

    @PostMapping("/clientes/{clienteId}/edit")
    public String procesarEditarCliente(@Valid Cliente cliente, BindingResult result,
            @PathVariable Integer clienteId, Model model) {
        if (result.hasErrors()) {
            cliente.setId(clienteId);
            model.addAttribute("cliente", cliente);
            return VISTA_FORMULARIO;
        }
        cliente.setId(clienteId);
        clienteRepository.save(cliente);
        return "redirect:/clientes/" + clienteId;
    }

    @GetMapping("/clientes")
    public String listarClientes(@RequestParam(defaultValue = "") String apellido,
            @RequestParam(defaultValue = "false") boolean soloVip, Model model) {
        List<Cliente> resultados;
        // BAD PRACTICE [S125]: commented-out code
        // resultados = clienteRepository.findAll();
        // resultados = resultados.stream()
        //     .filter(c -> apellido.isEmpty() || c.getApellido().equalsIgnoreCase(apellido))
        //     .collect(java.util.stream.Collectors.toList());
        if (!apellido.isEmpty()) {
            resultados = clienteRepository.findByApellido(apellido);
        } else {
            resultados = clienteRepository.findAll();
        }
        if (soloVip == true) {
            List<Cliente> vips = new java.util.ArrayList<>();
            for (Cliente c : resultados) {
                if (c.isVip() == true) {
                    vips.add(c);
                }
            }
            resultados = vips;
        }
        if (resultados.size() == 1) {
            return "redirect:/clientes/" + resultados.get(0).getId();
        }
        model.addAttribute("clientes", resultados);
        model.addAttribute("restaurante", "El Bucle Infinito");
        return "clientes/lista";
    }

    @GetMapping("/clientes/{clienteId}")
    public String mostrarCliente(@PathVariable Integer clienteId, Model model) {
        Optional<Cliente> optCliente = clienteRepository.findById(clienteId);
        model.addAttribute("cliente", optCliente.get());
        model.addAttribute("restaurante", "El Bucle Infinito");
        return "clientes/detalle";
    }
}
