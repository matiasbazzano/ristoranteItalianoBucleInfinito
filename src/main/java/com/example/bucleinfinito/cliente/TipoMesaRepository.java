package com.example.bucleinfinito.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMesaRepository extends JpaRepository<TipoMesa, Integer> {
    TipoMesa findByNombre(String nombre);
}
