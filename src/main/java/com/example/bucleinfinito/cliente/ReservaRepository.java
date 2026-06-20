package com.example.bucleinfinito.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByClienteId(Integer clienteId);
}
