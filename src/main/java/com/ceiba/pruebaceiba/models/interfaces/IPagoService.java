package com.ceiba.pruebaceiba.models.interfaces;

import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;

import java.util.List;
import java.util.Optional;

public interface IPagoService {

    public List<Pago> findAll();

    public Pago save(Pago pago);

    public Optional<Pago> findById(PagoPK id);

    public Pago update(Pago pago);

    public Double montoRestante(PagoPK id);
    public String pagoValidations(Pago newPago, PagoPK newIdPago);

}
