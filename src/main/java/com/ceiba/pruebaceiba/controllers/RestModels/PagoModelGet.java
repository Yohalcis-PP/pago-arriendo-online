package com.ceiba.pruebaceiba.controllers.RestModels;

import com.ceiba.pruebaceiba.models.interfaces.IValidatorFormatService;
import com.ceiba.pruebaceiba.models.services.ValidatorFormatServiceImpl;
import com.ceiba.pruebaceiba.persistence.entity.Pago;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class PagoModelGet {

    @Autowired
    private IValidatorFormatService validatorFormats = new ValidatorFormatServiceImpl();

    public PagoModelGet(Pago pago) {
        this.documentoIdentificacionArrendatario = pago.getId().getDocumentoIdentificacionArrendatario();
        this.codigoInmueble = pago.getId().getCodigoInmueble();
        this.fechaPago = validatorFormats.fechaFormatOutput(pago.getFechaPago());
        this.valorPagado = pago.getValorPagado();
    }

    private Integer documentoIdentificacionArrendatario;

    private String codigoInmueble;

    private String fechaPago;

    private Double valorPagado;

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Integer getDocumentoIdentificacionArrendatario() {
        return documentoIdentificacionArrendatario;
    }

    public void setDocumentoIdentificacionArrendatario(Integer documentoIdentificacionArrendatario) {
        this.documentoIdentificacionArrendatario = documentoIdentificacionArrendatario;
    }

    public String getCodigoInmueble() {
        return codigoInmueble;
    }

    public void setCodigoInmueble(String codigoInmueble) {
        this.codigoInmueble = codigoInmueble;
    }


}
