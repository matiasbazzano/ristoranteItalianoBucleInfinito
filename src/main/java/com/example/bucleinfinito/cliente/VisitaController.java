package com.example.bucleinfinito.cliente;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

// BAD PRACTICE [S109]: magic numbers (5, 10, 23)
// BAD PRACTICE [S3655]: NPE risk — Optional used without check
@Controller
public class VisitaController {

    private final VisitaRepository visitaRepository;
    private final ReservaRepository reservaRepository;

    @Autowired
    public VisitaController(VisitaRepository visitaRepository,
            ReservaRepository reservaRepository) {
        this.visitaRepository = visitaRepository;
        this.reservaRepository = reservaRepository;
    }

    @GetMapping("/clientes/{clienteId}/reservas/{reservaId}/visitas/new")
    public String initNuevaVisita(@PathVariable Integer reservaId, Model model) {
        Visita visita = new Visita();
        Reserva reserva = reservaRepository.findById(reservaId).get();
        visita.setReserva(reserva);
        visita.setFechaVisita(LocalDate.now());
        model.addAttribute("visita", visita);
        model.addAttribute("reserva", reserva);
        return "visitas/formulario";
    }

    @PostMapping("/clientes/{clienteId}/reservas/{reservaId}/visitas/new")
    public String procesarNuevaVisita(@PathVariable Integer clienteId,
            @PathVariable Integer reservaId,
            @Valid Visita visita, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("reserva", reservaRepository.findById(reservaId).get());
            return "visitas/formulario";
        }
        Optional<Reserva> optReserva = reservaRepository.findById(reservaId);
        Reserva reserva = optReserva.get();
        if (reserva.getCantidadPersonas() > 10) {
            visita.setNotas("Grupo grande: más de 10 personas");
        } else if (reserva.getCantidadPersonas() > 5) {
            visita.setNotas("Grupo mediano");
        }
        int maxCaracteres = 23;
        if (visita.getNotas() != null && visita.getNotas().length() > maxCaracteres * 10) {
            visita.setNotas(visita.getNotas().substring(0, maxCaracteres * 10));
        }
        visita.setReserva(reserva);
        visitaRepository.save(visita);
        return "redirect:/clientes/" + clienteId;
    }
}
