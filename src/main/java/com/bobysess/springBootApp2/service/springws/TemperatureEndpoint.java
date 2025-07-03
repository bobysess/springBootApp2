package com.bobysess.springBootApp2.service.springws;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.bobysess.temperature.ConvertTemperatureRequest;
import com.bobysess.temperature.ConvertTemperatureResponse;
import com.bobysess.temperature.TemperatureUnit;



@Endpoint
public class TemperatureEndpoint {
    private static final String NAMESPACE_URI = "http://bobysess.com/temperature";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "convertTemperatureRequest")
    @ResponsePayload
    public ConvertTemperatureResponse getCountry(@RequestPayload ConvertTemperatureRequest request) {
        double valueInDegree = switch (request.getFromUnit()) {
            case FAHRENHEIT -> (request.getValue() - 32) * 5.0 / 9.0;
            case KELVIN -> request.getValue() - 273.15;
            default -> request.getValue();
        };

        double convertedValue = switch (request.getToUnit()) {
            case FAHRENHEIT -> valueInDegree * 9.0 / 5.0 + 32;
            case KELVIN -> valueInDegree + 273.15;
            default -> valueInDegree;
        };
        
        ConvertTemperatureResponse response = new ConvertTemperatureResponse();
        response.setConvertedValue(convertedValue);
        response.setOriginalValue(request.getValue());
        response.setFromUnit(request.getFromUnit());
        response.setToUnit(request.getToUnit());
        
        return response;
    }    
}
