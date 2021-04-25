package com.ceiba.pruebaceiba.models.interfaces;

import com.ceiba.pruebaceiba.controllers.RestModels.PagoModelPost;
import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;

import java.util.Calendar;
import java.util.Map;

public interface IValidatorFormatService {

    public Calendar fechaFormatInput(String fecha);

    public String fechaFormatOutput(Calendar fecha);

    public Boolean fechaFormatValid(String fecha);

    public Boolean limitPago(Double pago);

    public Boolean alphaNumeric(String cadena);

    public Boolean isDayPar(String fecha);

    public String allValidations(PagoModelPost pago);



}
