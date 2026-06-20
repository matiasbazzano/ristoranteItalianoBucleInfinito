package com.example.bucleinfinito.cliente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

// BAD PRACTICE [S112]: throws generic Exception
// BAD PRACTICE [S1166]: empty catch block swallows exception
@Controller
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final TipoMesaRepository tipoMesaRepository;
    private final ComidaEspecialRepository comidaEspecialRepository;
    private final MesaRepository mesaRepository;

    @Autowired
    public ReservaController(ReservaRepository reservaRepository,
            ClienteRepository clienteRepository,
            TipoMesaRepository tipoMesaRepository,
            ComidaEspecialRepository comidaEspecialRepository,
            MesaRepository mesaRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.tipoMesaRepository = tipoMesaRepository;
        this.comidaEspecialRepository = comidaEspecialRepository;
        this.mesaRepository = mesaRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new ReservaValidator());
    }

    private void poblarAtributosModelo(Model model) {
        model.addAttribute("tiposMesa", tipoMesaRepository.findAll());
        model.addAttribute("comidasEspeciales", comidaEspecialRepository.findAll());
        model.addAttribute("mesas", mesaRepository.findAll());
    }

    @GetMapping("/clientes/{clienteId}/reservas/new")
    public String initNuevaReserva(@PathVariable Integer clienteId, Model model) throws Exception {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(Exception::new);
        Reserva reserva = new Reserva();
        reserva.setEstado("PENDIENTE");
        cliente.addReserva(reserva);
        model.addAttribute("reserva", reserva);
        poblarAtributosModelo(model);
        return "reservas/formulario";
    }

    @PostMapping("/clientes/{clienteId}/reservas/new")
    public String procesarNuevaReserva(@PathVariable Integer clienteId,
            @Valid Reserva reserva, BindingResult result, Model model) {
        if (result.hasErrors()) {
            poblarAtributosModelo(model);
            return "reservas/formulario";
        }
        try {
            Cliente cliente = clienteRepository.findById(clienteId).get();
            List<Reserva> todasReservas = asignarMesas(cliente, reserva);
            for (Reserva r : todasReservas) {
                reservaRepository.save(r);
            }
        } catch (Exception e) {
        }
        return "redirect:/clientes/" + clienteId;
    }

    @GetMapping("/clientes/{clienteId}/reservas/{reservaId}/edit")
    public String initEditarReserva(@PathVariable Integer clienteId,
            @PathVariable Integer reservaId, Model model) throws Exception {
        Reserva reserva = reservaRepository.findById(reservaId).orElseThrow(Exception::new);
        model.addAttribute("reserva", reserva);
        poblarAtributosModelo(model);
        return "reservas/formulario";
    }

    @PostMapping("/clientes/{clienteId}/reservas/{reservaId}/edit")
    public String procesarEditarReserva(@PathVariable Integer clienteId,
            @PathVariable Integer reservaId,
            @Valid Reserva reserva, BindingResult result, Model model) {
        if (result.hasErrors()) {
            poblarAtributosModelo(model);
            return "reservas/formulario";
        }
        reserva.setId(reservaId);
        Cliente cliente = clienteRepository.findById(clienteId).get();
        reserva.setCliente(cliente);
        reservaRepository.save(reserva);
        return "redirect:/clientes/" + clienteId;
    }

    // BAD PRACTICE [S3776]: complejidad cognitiva > 20 — requiere refactorización
    // Si la mesa seleccionada no tiene capacidad suficiente, asigna mesas adicionales
    // creando una Reserva extra por cada mesa adicional necesaria.
    private List<Reserva> asignarMesas(Cliente cliente, Reserva reservaBase) {
        List<Reserva> resultado = new ArrayList<>();
        int restantes = reservaBase.getCantidadPersonas();
        List<Mesa> todasMesas = mesaRepository.findAll();

        if (reservaBase.getMesa() != null) {
            if (reservaBase.getMesa().getCapacidad() != null) {
                int cap = reservaBase.getMesa().getCapacidad();
                if (cap >= restantes) {
                    cliente.addReserva(reservaBase);
                    resultado.add(reservaBase);
                } else {
                    restantes -= cap;
                    cliente.addReserva(reservaBase);
                    resultado.add(reservaBase);
                    for (Mesa m : todasMesas) {
                        if (restantes <= 0) {
                            break;
                        }
                        if (m.isActiva()) {
                            if (m.getId() != null && !m.getId().equals(reservaBase.getMesa().getId())) {
                                if (m.getCapacidad() != null) {
                                    if (m.getCapacidad() > 0) {
                                        Reserva extra = new Reserva();
                                        extra.setDia(reservaBase.getDia());
                                        extra.setHora(reservaBase.getHora());
                                        extra.setEstado(reservaBase.getEstado());
                                        extra.setTipoMesa(reservaBase.getTipoMesa());
                                        extra.setComidaEspecial(reservaBase.getComidaEspecial());
                                        extra.setMesa(m);
                                        if (restantes > m.getCapacidad()) {
                                            extra.setCantidadPersonas(m.getCapacidad());
                                            restantes -= m.getCapacidad();
                                        } else {
                                            extra.setCantidadPersonas(restantes);
                                            restantes = 0;
                                        }
                                        cliente.addReserva(extra);
                                        resultado.add(extra);
                                    }
                                }
                            }
                        }
                    }
                    if (restantes > 0) {
                        for (Mesa m : todasMesas) {
                            if (restantes <= 0) {
                                break;
                            }
                            if (!m.isActiva()) {
                                if (m.getCapacidad() != null && m.getCapacidad() > 0) {
                                    boolean usada = false;
                                    for (Reserva r : resultado) {
                                        if (r.getMesa() != null && r.getMesa().getId().equals(m.getId())) {
                                            usada = true;
                                            break;
                                        }
                                    }
                                    if (!usada) {
                                        Reserva extra = new Reserva();
                                        extra.setDia(reservaBase.getDia());
                                        extra.setHora(reservaBase.getHora());
                                        extra.setEstado("PENDIENTE");
                                        extra.setTipoMesa(reservaBase.getTipoMesa());
                                        extra.setComidaEspecial(reservaBase.getComidaEspecial());
                                        extra.setMesa(m);
                                        if (restantes > m.getCapacidad()) {
                                            extra.setCantidadPersonas(m.getCapacidad());
                                            restantes -= m.getCapacidad();
                                        } else {
                                            extra.setCantidadPersonas(restantes);
                                            restantes = 0;
                                        }
                                        cliente.addReserva(extra);
                                        resultado.add(extra);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (Mesa m : todasMesas) {
                if (m.isActiva() && m.getCapacidad() != null) {
                    reservaBase.setMesa(m);
                    if (restantes > m.getCapacidad()) {
                        reservaBase.setCantidadPersonas(m.getCapacidad());
                        cliente.addReserva(reservaBase);
                        resultado.add(reservaBase);
                        break;
                    } else {
                        reservaBase.setCantidadPersonas(restantes);
                        cliente.addReserva(reservaBase);
                        resultado.add(reservaBase);
                        break;
                    }
                }
            }
        }
        return resultado;
    }
}
