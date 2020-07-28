package com.example.stock.service.impl;

import com.example.api.service.StockService;
import com.example.stock.dao.StockDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class StockServiceImpl implements StockService {

    @Autowired
    StockDao stockDao;

    @Override
    public boolean updateStock(int buys,String stockSku) {
        System.out.println("start update stock");
        return stockDao.updateStock(buys,stockSku);
    }
}
