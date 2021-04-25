package com.ceiba.pruebaceiba.controllers;

import com.ceiba.pruebaceiba.controllers.RestModels.PagoModelGet;
import com.ceiba.pruebaceiba.controllers.RestModels.PagoModelPost;
import com.ceiba.pruebaceiba.models.interfaces.IValidatorFormatService;
import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.models.interfaces.IPagoService;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PagoRestController {

    @Autowired
    private IPagoService pagoService;
    @Autowired
    private IValidatorFormatService validatorFormats;

    @GetMapping("/pagos")
    public List<PagoModelGet> getPagos(){

        List<PagoModelGet> response = new ArrayList<>();

        pagoService.findAll().forEach(
               pago -> {
               response.add(new PagoModelGet(pago));
                });

        return response;
    }

    @PostMapping("/pagos")
    public ResponseEntity<?> createPago(@RequestBody PagoModelPost pago){
        Pago newPago =  new Pago();
        PagoPK newIdPago = new PagoPK();
        Map<String, Object> response = new HashMap<>();


        if(!validatorFormats.allValidations(pago).equals("ok")){
            response.put("respuesta", validatorFormats.allValidations(pago));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        newIdPago.setDocumentoIdentificacionArrendatario(pago.getDocumentoIdentificacionArrendatario());
        newIdPago.setCodigoInmueble(pago.getCodigoInmueble());

        Calendar fecha =  validatorFormats.fechaFormatInput(pago.getFechaPago());
        newPago.setFechaPago(fecha);
        newPago.setId(newIdPago);
        newPago.setValorPagado(pago.getValorPagado());

        String respuesta = pagoService.pagoValidations(newPago, newIdPago);


        if(respuesta.equals("Error al insertar pago en base de datos")){
            response.put("respuesta", respuesta);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }else if(respuesta.contains("Gracias por tu abono, sin embargo recuerda que te hace falta pagar $")){
            response.put("respuesta", respuesta);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }else if(respuesta.equals("Gracias por pagar todo tu arriendo")){
            response.put("respuesta", respuesta);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
        }else if(respuesta.equals("Usted ya pagó toda su deuda")){
            response.put("respuesta", respuesta);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }else if(respuesta.contains("El valor debe ser menor a su deuda, la cual es de: ")){
            response.put("respuesta", respuesta);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }else{
            response.put("respuesta", "No se pude detectar su fallo, lo siento");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

    }
}
