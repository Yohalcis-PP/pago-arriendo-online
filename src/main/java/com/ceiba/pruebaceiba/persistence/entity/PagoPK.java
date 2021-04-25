package com.ceiba.pruebaceiba.persistence.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PagoPK implements Serializable {


    private Integer documentoIdentificacionArrendatario;


    private String codigoInmueble;

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
