package com.example.bucleinfinito.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.bucleinfinito.cliente.ComidaEspecialFormatter;
import com.example.bucleinfinito.cliente.ComidaEspecialRepository;
import com.example.bucleinfinito.cliente.MesaFormatter;
import com.example.bucleinfinito.cliente.MesaRepository;
import com.example.bucleinfinito.cliente.TipoMesaFormatter;
import com.example.bucleinfinito.cliente.TipoMesaRepository;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TipoMesaRepository tipoMesaRepository;
    private final ComidaEspecialRepository comidaEspecialRepository;
    private final MesaRepository mesaRepository;

    @Autowired
    public WebMvcConfig(TipoMesaRepository tipoMesaRepository,
                        ComidaEspecialRepository comidaEspecialRepository,
                        MesaRepository mesaRepository) {
        this.tipoMesaRepository = tipoMesaRepository;
        this.comidaEspecialRepository = comidaEspecialRepository;
        this.mesaRepository = mesaRepository;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new TipoMesaFormatter(tipoMesaRepository));
        registry.addFormatter(new ComidaEspecialFormatter(comidaEspecialRepository));
        registry.addFormatter(new MesaFormatter(mesaRepository));
    }
}
