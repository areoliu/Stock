package com.example.stock.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class Order {

    private int id;

    private int uid;

    private String orderId;

    private String orderSku;

    private int orderAmout;

    private double orderPrice;

    private Date createDate;

    private Date modefyDate;

    private int orderStatus;
}
