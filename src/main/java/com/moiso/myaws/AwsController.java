package com.moiso.myaws;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aws")
public record AwsController(String accessKeyId, String secretAccessKey) {
    
}
