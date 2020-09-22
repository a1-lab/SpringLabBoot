package com.learning.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app2")
public class App2Properties {
    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String description;
}
