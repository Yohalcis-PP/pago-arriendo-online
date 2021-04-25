package com.ceiba.pruebaceiba.models.services;


import com.ceiba.pruebaceiba.models.interfaces.IValidatorFormatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidatorFormatServiceImplTest {

    @Autowired
    private IValidatorFormatService validatorFormats;

    @Test
    void fechaFormatInput() {
        int day = 25;
        int month = 10;
        int year = 2015;
        String fechaInput = day+"/"+month+"/"+year;
        Calendar fecha = validatorFormats.fechaFormatInput(fechaInput);

        int dayCalendar = fecha.get(Calendar.DAY_OF_MONTH);
        int monthCalendar = fecha.get(Calendar.MONTH)+1;
        int yearCalendar = fecha.get(Calendar.YEAR);

        assertEquals(day,dayCalendar);
        assertEquals(month,monthCalendar);
        assertEquals(year,yearCalendar);

    }

    @Test
    void fechaFormatOutput() {
        int dayCalendar = 25;
        int monthCalendar = 10;
        int yearCalendar = 2015;

        Calendar cal = new GregorianCalendar(yearCalendar,monthCalendar,dayCalendar);
        String fecha = validatorFormats.fechaFormatOutput(cal);

        String[] parts = fecha.split("/");

        int day = Integer.valueOf(parts[0]);
        int month = Integer.valueOf(parts[1])-1;
        int year = Integer.valueOf(parts[2]);

        assertEquals(dayCalendar, day);
        assertEquals(monthCalendar, month);
        assertEquals(yearCalendar, year);

    }

    @Test
    void fechaFormatValidFormatInvalid() {
        int day = 25;
        int month = 10;
        int year = 2015;
        String fechaInput = day+"-"+month+"-"+year;
        Boolean isValid = validatorFormats.fechaFormatValid(fechaInput);
        assertFalse(isValid);
    }

    @Test
    void fechaFormatValidFormatValid() {
        int day = 25;
        int month = 10;
        int year = 2015;
        String fechaInput = day+"/"+month+"/"+year;
        Boolean isValid = validatorFormats.fechaFormatValid(fechaInput);
        assertTrue(isValid);
    }

    @Test
    void limitPago() {
        //Valor dentro de los rangos
        Double pago = 500000.0;
        Boolean isValid = validatorFormats.limitPago(pago);

        assertTrue(isValid);

        //Valor inferior a los rangos
        pago = -200000.0;
        isValid = validatorFormats.limitPago(pago);

        assertFalse(isValid);

        //Valor superior a los rangos
        pago = 50000000.0;
        isValid = validatorFormats.limitPago(pago);

        assertFalse(isValid);

    }

    @Test
    void alphaNumeric() {
        String cadenaValid = "1322";
        String cadenaValid2 = "abc123";
        String cadenaValid3 = "adss12313";

        String cadenaInvalid = "4231/asdasd";

        //Case 1 - Valid
        assertTrue(validatorFormats.alphaNumeric(cadenaValid));

        //Case 2 - Valid
        assertTrue(validatorFormats.alphaNumeric(cadenaValid2));

        //Case 3 - Valid
        assertTrue(validatorFormats.alphaNumeric(cadenaValid3));

        //Case 4 - Invalid
        assertFalse(validatorFormats.alphaNumeric(cadenaInvalid));

    }

    @Test
    void isDayPar() {
        //fecha impar
        String fechaImpar = "05/03/2021";

        //fecha par
        String fechaPar = "04/03/2021";

        //Case 1 - Fecha par
        assertTrue(validatorFormats.isDayPar(fechaPar));

        //Case 2 - Fecha impar
        assertFalse(validatorFormats.isDayPar(fechaImpar));
    }


}