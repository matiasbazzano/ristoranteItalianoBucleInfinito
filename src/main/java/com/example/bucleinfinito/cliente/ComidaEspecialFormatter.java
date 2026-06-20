package com.example.bucleinfinito.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

public class ComidaEspecialFormatter implements Formatter<ComidaEspecial> {

    private final ComidaEspecialRepository comidaEspecialRepository;

    @Autowired
    public ComidaEspecialFormatter(ComidaEspecialRepository comidaEspecialRepository) {
        this.comidaEspecialRepository = comidaEspecialRepository;
    }

    @Override
    public String print(ComidaEspecial comidaEspecial, Locale locale) {
        return comidaEspecial.getNombre();
    }

    @Override
    public ComidaEspecial parse(String text, Locale locale) throws ParseException {
        Collection<ComidaEspecial> comidas = this.comidaEspecialRepository.findAll();
        for (ComidaEspecial comida : comidas) {
            if (comida.getNombre().equals(text)) {
                return comida;
            }
        }
        throw new ParseException("Comida especial no encontrada: " + text, 0);
    }
}
