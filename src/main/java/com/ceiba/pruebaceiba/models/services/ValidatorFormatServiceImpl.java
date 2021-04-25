package com.ceiba.pruebaceiba.models.services;

import com.ceiba.pruebaceiba.controllers.RestModels.PagoModelPost;
import com.ceiba.pruebaceiba.models.interfaces.IPagoService;
import com.ceiba.pruebaceiba.models.interfaces.IValidatorFormatService;
import com.ceiba.pruebaceiba.persistence.entity.Pago;
import com.ceiba.pruebaceiba.persistence.entity.PagoPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ValidatorFormatServiceImpl implements IValidatorFormatService {


    @Override
    public Calendar fechaFormatInput(String fecha) {

        String[] parts = fecha.split("/");

        int day = Integer.valueOf(parts[0]);
        int month = Integer.valueOf(parts[1])-1;
        int year = Integer.valueOf(parts[2]);

        Calendar cal = new GregorianCalendar(year,month,day);
        return cal;
    }

    public String fechaFormatOutput(Calendar fecha) {

        int day = fecha.get(Calendar.DAY_OF_MONTH);
        int month = fecha.get(Calendar.MONTH)+1;
        int year = fecha.get(Calendar.YEAR);

        String fechaOutput = String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
        return fechaOutput;
    }

    @Override
    public Boolean fechaFormatValid(String fecha) {
        Pattern pat = Pattern.compile("^([0-2][0-9]|3[0-1])(\\/)(0[1-9]|1[0-2])\\2(\\d{4})$");
        Matcher mat = pat.matcher(fecha);
        return mat.find();
    }

    @Override
    public Boolean limitPago(Double pago){
        Double pagoMaximo = 1000000.0;
        return pago>=1 && pago <= pagoMaximo;
    }

    @Override
    public Boolean alphaNumeric(String cadena) {
        for (int x=0;x<cadena.length();x++){
            if(cadena.charAt(x)<48 || (cadena.charAt(x)>57 && cadena.charAt(x)<65) || (cadena.charAt(x)>90 && cadena.charAt(x)<97) || cadena.charAt(x)>122 ){
                return false;
            }
        }
        return true;

    }

    @Override
    public Boolean isDayPar(String fecha) {
        String[] parts = fecha.split("/");
        Integer day = Integer.parseInt(parts[0]);
        return (day%2==0);
    }

    @Override
    public String allValidations(PagoModelPost pago) {
        String response = "ok";

        //Validaciones
        if(pago.getDocumentoIdentificacionArrendatario() == null || pago.getCodigoInmueble() == null ){
            response = "DocumentoIdentificacionArrendatario y CodigoInmueble, no pueden ser nulos";
            return response;
        }else{
            if(!alphaNumeric(pago.getCodigoInmueble())){
                response = "Codigo inmueble no debe tener caracteres especiales";
                return response;
            }
        }

        if(!fechaFormatValid(pago.getFechaPago())){
            response = "Formato de fecha incorrecto";
            return response;
        }else if(isDayPar(pago.getFechaPago())){
            response = "lo siento pero no se puede recibir el pago por decreto de administración";
            return response;
        }

        if(!limitPago(pago.getValorPagado())){
            Double pagoMaximo = 1000000.0;
            Double pagoMinimo = 1.0;
            response = "El valor debe estar entre ".concat(pagoMinimo.toString()).concat(" y ").concat(pagoMaximo.toString());
            return response;
        }

        return response;
        //fin
    }


}
