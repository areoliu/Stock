package com.example.stock.service;

import com.example.stock.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockDao stockDao;

    @Override
    public boolean updateStock(int buys,String stockSku) {
        return stockDao.updateStock(buys,stockSku);
    }
}
