package com.example.practice.services;
import com.example.practice.models.Measurement;
import com.example.practice.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    public MeasurementService(MeasurementRepository measurementRepository,
                              SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement) {
        // object Sensor which contains in measurement was converted from json and hibernate cannot make bound Measurement
        // and Sensor (sensor is not in a persistent context), so we have to find
        // sensor in DB and set it to field "sensor" in measurement object
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());
        // and add current DateTime
        measurement.setMeasurementDateTime(LocalDateTime.now());
    }
}
