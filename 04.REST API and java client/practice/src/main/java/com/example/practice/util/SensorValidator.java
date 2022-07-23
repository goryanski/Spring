package com.example.practice.util;

import com.example.practice.models.Sensor;
import com.example.practice.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;

        // Sensor's name must be unique
        if (sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "There is already sensor with this name");
        }
    }
}