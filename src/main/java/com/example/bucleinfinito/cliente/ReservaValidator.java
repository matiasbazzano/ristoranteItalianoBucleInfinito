package com.example.bucleinfinito.cliente;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// BAD PRACTICE [S1186]: validate() method has an empty body
public class ReservaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Reserva.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}
