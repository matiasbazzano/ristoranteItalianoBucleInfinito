package com.example.bucleinfinito.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComidaEspecialRepository extends JpaRepository<ComidaEspecial, Integer> {
    ComidaEspecial findByNombre(String nombre);
}
