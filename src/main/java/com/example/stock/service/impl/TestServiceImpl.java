package com.example.stock.service.impl;

import com.example.api.service.TestService;
import org.apache.dubbo.config.annotation.Service;

@Service(protocol = "dubbo")
public class TestServiceImpl implements TestService {
    @Override
    public String test(String param) {
        return "test service is "+param;
    }

}
