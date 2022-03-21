package com.dntech.localcache.service;

import com.dntech.localcache.dto.OrderDetails;

import java.util.Optional;

public interface OrderCache extends Cache{

    Optional<OrderDetails> get(String userId, String orderDate, String itemId);

    OrderDetails getOrderDB(String userId, String orderDate, String itemId);
}
