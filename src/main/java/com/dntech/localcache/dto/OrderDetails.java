package com.dntech.localcache.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetails {

    private String userId;
    private String orderDate;
    private String itemId;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

}
