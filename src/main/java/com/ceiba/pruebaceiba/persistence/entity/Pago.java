package com.ceiba.pruebaceiba.persistence.entity;


import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "pagos")
public class Pago {

    @EmbeddedId
    private PagoPK id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar fechaPago;

    @Column(nullable = false)
    private Double valorPagado;

    public Calendar getFechaPago() {
        return fechaPago;
    }

    public PagoPK getId() {
        return id;
    }

    public void setId(PagoPK id) {
        this.id = id;
    }

    public void setFechaPago(Calendar fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
    }
}
