package com.example.bucleinfinito.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

public class TipoMesaFormatter implements Formatter<TipoMesa> {

    private final TipoMesaRepository tipoMesaRepository;

    @Autowired
    public TipoMesaFormatter(TipoMesaRepository tipoMesaRepository) {
        this.tipoMesaRepository = tipoMesaRepository;
    }

    @Override
    public String print(TipoMesa tipoMesa, Locale locale) {
        return tipoMesa.getNombre();
    }

    @Override
    public TipoMesa parse(String text, Locale locale) throws ParseException {
        Collection<TipoMesa> tipos = this.tipoMesaRepository.findAll();
        for (TipoMesa tipo : tipos) {
            if (tipo.getNombre().equals(text)) {
                return tipo;
            }
        }
        throw new ParseException("Tipo de mesa no encontrado: " + text, 0);
    }
}
