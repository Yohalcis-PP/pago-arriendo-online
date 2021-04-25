package com.ceiba.pruebaceiba.models.services;

import com.ceiba.pruebaceiba.models.interfaces.IPagoService;
import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.persistence.Dao.IPagoDao;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoDao pagoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> findAll() {
        return (List<Pago>) pagoDao.findAll();
    }

    @Override
    public Pago save(Pago pago) {
        return pagoDao.save(pago);
    }

    @Override
    public Optional<Pago> findById(PagoPK id) {

        return pagoDao.findById(id);
    }

    @Override
    public Pago update(Pago pago) {
        Double montoAcumulado = 0.0;
        Pago pagoUpdate = this.findById(pago.getId()).get();

        pagoUpdate.setFechaPago(pago.getFechaPago());
        montoAcumulado = pagoUpdate.getValorPagado();
        pagoUpdate.setValorPagado(montoAcumulado + pago.getValorPagado());

        return this.save(pagoUpdate);
    }

    @Override
    public Double montoRestante(PagoPK id) {
        Double pagoMaximo = 1000000.0;
        Optional<Pago> pago = pagoDao.findById(id);

        if(pago.isEmpty()){
            return pagoMaximo;
        }else{
            if((pagoMaximo-pago.get().getValorPagado())==0){
                return 0.0;
            } else{
                return pagoMaximo - pago.get().getValorPagado();
            }
        }
    }

    @Override
    public String pagoValidations(Pago newPago, PagoPK newIdPago) {
        String response = "ok";

        Double montoRestante = montoRestante(newIdPago);

        if(montoRestante==1000000){
            if(newPago.getValorPagado()<1000000){
                try{
                    newPago = save(newPago);
                }catch (DataAccessException e){
                    response = "Error al insertar pago en base de datos";
                    return response;
                }
                montoRestante = 1000000-newPago.getValorPagado();
                response = "Gracias por tu abono, sin embargo recuerda que te hace falta pagar $".concat((montoRestante.toString()));
                return response;
            }else{
                try{
                    newPago = save(newPago);
                }catch (DataAccessException e){
                    response = "Error al insertar pago en base de datos";
                    return response;
                }
                response = "Gracias por pagar todo tu arriendo";
                return response;

            }
        }else if(montoRestante==0) {
            response = "Usted ya pagó toda su deuda";
            return response;
        }else if(montoRestante<newPago.getValorPagado()){
            response = "El valor debe ser menor a su deuda, la cual es de: ".concat(montoRestante.toString());
            return response;
        }else if(montoRestante>newPago.getValorPagado()){
            try{
                newPago = update(newPago);
            }catch (DataAccessException e){
                response = "Error al insertar pago en base de datos";
                return response;
            }
            montoRestante = 1000000-newPago.getValorPagado();
            response = "Gracias por tu abono, sin embargo recuerda que te hace falta pagar $".concat((montoRestante.toString()));
            return response;
        }else{
            try{
                newPago = update(newPago);
            }catch (DataAccessException e){
                response = "Error al insertar pago en base de datos";
                return response;
            }
            response = "Gracias por pagar todo tu arriendo";
            return response;
        }
    }
}
