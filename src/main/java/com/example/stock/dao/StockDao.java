package com.example.stock.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface StockDao {

    @Update("update stock set stock_amout=stock_amout-#{buys} where stock_sku=#{stockSku} and stock_amout>#{buys}")
    public boolean updateStock(int buys, String stockSku);

    @Select("select stock_amout from stock")
    public int queryStock();
}
