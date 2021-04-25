package com.ceiba.pruebaceiba.controllers.RestModels;

import com.ceiba.pruebaceiba.persistence.entity.PagoPK;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

public class PagoModelPost {

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
