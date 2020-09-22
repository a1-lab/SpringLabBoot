package com.learning.boot.web;

import com.learning.boot.properties.App1Properties;
import com.learning.boot.properties.App2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Value("${welcomeMessage}")
    private String welcomeMsg;

    @GetMapping
    public String index() {
        return app1Properties.getName();
    }

    @GetMapping("app2")
    public String app2() {
        return app2Properties.getName();
    }

    @Autowired
    App1Properties app1Properties;

    @Autowired
    App2Properties app2Properties;
}
