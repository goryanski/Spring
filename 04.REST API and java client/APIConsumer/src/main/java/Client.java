import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Client {
    public static void main(String[] args) {
        final String sensorName = "Sensor2";
        registerSensor(sensorName);

        Random random = new Random();
        double minTemperature = 0.0;
        double maxTemperature = 45.0;
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            sendMeasurement(random.nextDouble() * maxTemperature,
                    random.nextBoolean(), sensorName); // random.nextBoolean() - random isRaining value
        }
    }

    private static void registerSensor(String sensorName) {
        final String url = "http://localhost:8080/sensors/registration";
        /*
        {
           "name": "Sensor2"
        }
        */
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void sendMeasurement(double value, boolean raining, String sensorName) {
        final String url = "http://localhost:8080/measurements/add";
        /*
        {
            "value": 28.2,
            "raining": true,
            "sensor": {
                "name": "Sensor2"
            }
        }
        */
        Map<String, Object> jsonData = new HashMap<>();
        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName)); // "nested object"

        makePostRequestWithJSONData(url, jsonData);
    }

    private static void makePostRequestWithJSONData(String url, Map<String, Object> jsonData) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Data has been sent to the server");
        } catch (HttpClientErrorException e) {
            System.out.println("Sending error:");
            System.out.println(e.getMessage());
        }
    }
}