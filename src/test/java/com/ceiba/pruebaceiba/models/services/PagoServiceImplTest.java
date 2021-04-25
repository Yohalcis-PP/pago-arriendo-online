package com.ceiba.pruebaceiba.models.services;

import com.ceiba.pruebaceiba.models.interfaces.IPagoService;
import com.ceiba.pruebaceiba.models.interfaces.IValidatorFormatService;
import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PagoServiceImplTest {

    @Autowired
    private IValidatorFormatService validatorFormats;

    @Autowired
    private IPagoService pagoService;

    //Mockito si da el tiempo

    //Caso Reglas de negocio - 7
    @Test
    void pagoValidationsCuotaCompleto() {
        Pago newPago =  new Pago();
        PagoPK newIdPago = new PagoPK();

        newIdPago.setDocumentoIdentificacionArrendatario(1245);
        newIdPago.setCodigoInmueble("ab1232");
        Calendar fecha =  validatorFormats.fechaFormatInput("05/03/2021");
        newPago.setFechaPago(fecha);
        newPago.setId(newIdPago);
        newPago.setValorPagado(1000000.0);

        String respuestaEsperada = "Gracias por pagar todo tu arriendo";
        String respuestaRecibida = pagoService.pagoValidations(newPago, newIdPago);


        assertEquals(respuestaEsperada, respuestaRecibida);
    }

    //Caso Reglas de negocio - 8
    @Test
    void pagoValidationPagoParcial() {
        Pago newPago =  new Pago();
        PagoPK newIdPago = new PagoPK();


        newIdPago.setDocumentoIdentificacionArrendatario(45);
        newIdPago.setCodigoInmueble("ab1232");
        Calendar fecha =  validatorFormats.fechaFormatInput("05/03/2021");
        newPago.setFechaPago(fecha);
        newPago.setId(newIdPago);
        newPago.setValorPagado(450000.0);
        Double montonRestante = 1000000.0 - 450000.0;

        String respuestaEsperada = "Gracias por tu abono, sin embargo recuerda que te hace falta pagar $".concat(montonRestante.toString());
        String respuestaRecibida = pagoService.pagoValidations(newPago, newIdPago);


        assertEquals(respuestaEsperada, respuestaRecibida);
    }

    //Caso Reglas de negocio - 9
    @Test
    void pagoValidationPagoRestanteCompleto() {
        Pago newPago =  new Pago();
        PagoPK newIdPago = new PagoPK();
        Map<String, Object> response = new HashMap<>();

        newIdPago.setDocumentoIdentificacionArrendatario(2245);
        newIdPago.setCodigoInmueble("ab1232");
        Calendar fecha =  validatorFormats.fechaFormatInput("05/03/2021");
        newPago.setFechaPago(fecha);
        newPago.setId(newIdPago);
        newPago.setValorPagado(450000.0);
        Double montonRestante = 1000000.0 - 450000.0;

        pagoService.pagoValidations(newPago, newIdPago);

        newPago.setValorPagado(montonRestante);

        String respuestaEsperada = "Gracias por pagar todo tu arriendo";
        String respuestaRecibida = pagoService.pagoValidations(newPago, newIdPago);


        assertEquals(respuestaEsperada, respuestaRecibida);
    }

}