package com.example.rabbit06idempotent.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: keyanbin
 * @description:
 * @create: 2023-11-25 17:04
 **/
@Data
@Builder
public class Order implements Serializable {
    private String id;
    private String OrderName;
    private BigDecimal Money;
    private Date createTime;
}
