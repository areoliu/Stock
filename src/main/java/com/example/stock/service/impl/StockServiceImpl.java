package com.example.stock.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.api.service.StockService;
import com.example.stock.dao.StockDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service(version = "2.0.0")
public class StockServiceImpl implements StockService {

    @Autowired
    StockDao stockDao;

    @Override
    public boolean updateStock(int buys,String stockSku) {
        //System.out.println("start update stock");
//        try {
//            Thread.sleep(150);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return stockDao.updateStock(buys,stockSku);
//        return true;
    }

    @Override
    public int queryStock() {
        return stockDao.queryStock();
    }
//
//    /**
//     * 降级方法，限流后应用
//     * @return
//     */
//    public boolean degradeMethod(String name, BlockException blockException){
//
//        System.out.println("请求被限流,触发限流规则="+blockException.getRule().getResource());
//        return false;
//    }

}
