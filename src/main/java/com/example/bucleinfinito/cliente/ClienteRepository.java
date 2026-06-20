package com.example.bucleinfinito.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

// BAD PRACTICE [S2076]: native query built with String concatenation — SQL injection risk
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = "SELECT * FROM clientes WHERE apellido LIKE '%" + "' || :apellido || '%" + "'", nativeQuery = true)
    List<Cliente> buscarPorApellido(@Param("apellido") String apellido);

    List<Cliente> findByApellido(String apellido);
}
