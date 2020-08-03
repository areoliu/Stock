package com.example.stock.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.api.service.StockService;
import com.example.stock.entity.HttpRequest;
import com.example.stock.entity.HttpResponseBody;
import com.example.stock.entity.HttpResponseHead;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @SentinelResource("updateStock")
    @PostMapping(value = "stock/updateStock")
    public Map<String,Object> updateStock(@RequestBody HttpRequest httpRequest){
        Map<String, Object> responseMap = new HashMap<String, Object>();
        HttpResponseBody responseBody = new HttpResponseBody();
        HttpResponseHead responseHead = new HttpResponseHead();
        Gson gson=new Gson();
        String str=gson.toJson(httpRequest.getRequestBody().getRequestData());
        stockService.updateStock(3,"iphone12");
        responseBody.setResultCode("S00001");
        responseBody.setResultMessage("succeed!");
        responseHead.setToken("");
        responseHead.setDate(new SimpleDateFormat("yy-MM-dd hh:mm:ss").format(new Date()));
        responseMap.put("responseBody",responseBody);
        responseMap.put("responseHead",responseHead);
        return responseMap;
    }

    @GetMapping(value = "stock/test")
    public String test(){
        return "stock test now";
    }


}
