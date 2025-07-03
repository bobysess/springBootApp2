package com.bobysess.springBootApp2.service.springws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.bobysess.temperature.ConvertTemperatureRequest;
import com.bobysess.temperature.ConvertTemperatureResponse;
import com.bobysess.temperature.TemperatureUnit;

@Service
@EnableScheduling
public class TemperatureClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WebServiceTemplate template;

    public TemperatureClient(WebServiceTemplateBuilder builder) {
        // Configure JAXB2 marshaller
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.bobysess.temperature");
        
        this.template = builder
                .setMarshaller(marshaller)
                .setUnmarshaller(marshaller)
                .build();
    }

    @Scheduled(fixedRate = 5_000L)
    void callService() {
        logger.info("Starting calling client");
        var request = new ConvertTemperatureRequest();
        request.setFromUnit(TemperatureUnit.CELSIUS);
        request.setToUnit(TemperatureUnit.FAHRENHEIT);
        request.setValue(100);

        var response = (ConvertTemperatureResponse) template.marshalSendAndReceive(
                "http://localhost:8080/ws", request);

        logger.info("response: {}", response);
        if (response != null) {
            logger.info("Converted {} {} to {} {}", 
                    response.getOriginalValue(), response.getFromUnit(),
                    response.getConvertedValue(), response.getToUnit());
        }
    }
}
