package com.example.bucleinfinito.cliente;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

public class MesaFormatter implements Formatter<Mesa> {

    private final MesaRepository mesaRepository;

    @Autowired
    public MesaFormatter(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public String print(Mesa mesa, Locale locale) {
        return mesa.getId() != null ? mesa.getId().toString() : "";
    }

    @Override
    public Mesa parse(String text, Locale locale) throws ParseException {
        if (text == null || text.isBlank()) return null;
        try {
            Optional<Mesa> mesa = mesaRepository.findById(Integer.parseInt(text));
            if (mesa.isPresent()) return mesa.get();
        } catch (NumberFormatException ignored) {
        }
        throw new ParseException("Mesa no encontrada con id: " + text, 0);
    }
}
