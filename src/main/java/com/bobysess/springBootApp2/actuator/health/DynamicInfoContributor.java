package com.bobysess.springBootApp2.actuator.health;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        // Create a map to hold dynamic information
        Map<String, Object> dynamicDetails = new HashMap<>();
        
        // Add current time
        dynamicDetails.put("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // Add JVM information
        Map<String, Object> jvmInfo = new HashMap<>();
        jvmInfo.put("version", System.getProperty("java.version"));
        jvmInfo.put("vendor", System.getProperty("java.vendor"));
        jvmInfo.put("vmName", System.getProperty("java.vm.name"));
        
        // Add system information
        Map<String, Object> systemInfo = new HashMap<>();
        systemInfo.put("os", System.getProperty("os.name"));
        systemInfo.put("arch", System.getProperty("os.arch"));
        systemInfo.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        systemInfo.put("freeMemory", Runtime.getRuntime().freeMemory());
        systemInfo.put("totalMemory", Runtime.getRuntime().totalMemory());
        
        // Combine all dynamic information
        dynamicDetails.put("jvm", jvmInfo);
        dynamicDetails.put("system", systemInfo);
        
        // Add to the info endpoint
        builder.withDetail("dynamic", dynamicDetails);
    }
}
