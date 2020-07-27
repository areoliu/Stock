package com.example.stock.controller;

import com.example.second.entity.*;
import com.example.second.service.OrderService;
import com.example.second.service.StockService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @PostMapping(value = "stock/updateStock")
    public Map<String,Object> updateStock(@RequestBody HttpRequest httpRequest){
        Map<String, Object> responseMap = new HashMap<String, Object>();
        HttpResponseBody responseBody = new HttpResponseBody();
        HttpResponseHead responseHead = new HttpResponseHead();
        Gson gson=new Gson();
        String str=gson.toJson(httpRequest.getRequestBody().getRequestData());
        Order order=gson.fromJson(str,Order.class);
        orderService.createOrder(order);
        System.out.println(order);
        stockService.updateStock(order.getOrderAmout(),order.getOrderSku());
        responseBody.setResultCode("S00001");
        responseBody.setResultMessage("succeed!");
        responseHead.setToken("");
        responseHead.setDate(new SimpleDateFormat("yy-MM-dd hh:mm:ss").format(new Date()));
        responseMap.put("responseBody",responseBody);
        responseMap.put("responseHead",responseHead);
        return responseMap;
    }

}
