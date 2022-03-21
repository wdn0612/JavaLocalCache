package com.dntech.localcache.service;

import com.dntech.localcache.dto.OrderDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class OrderCacheImpl implements OrderCache {

    private static final Logger LOG = LoggerFactory.getLogger(OrderCacheImpl.class);

    private List<OrderDetails> orderDetailsCache;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OrderCacheImpl() {
        orderDetailsCache = new ArrayList<>();
    }

    @Override
    public Optional<OrderDetails> get(String userId, String orderDate, String itemId) {
        LOG.info("Searching for userid: {} orderDate: {} itemId: {}", userId, orderDate, itemId);

        Stream<OrderDetails> orderDetailsStream = this.orderDetailsCache.stream()
                .filter(orderDetails -> orderDetails.getUserId().equals(userId))
                .filter(orderDetails -> orderDetails.getOrderDate().equals(orderDate))
                .filter(orderDetails -> orderDetails.getItemId().equals(itemId));
        Optional<OrderDetails> result = orderDetailsStream.findFirst();
        return result;
    }


    @Override
    public void load() {
        LOG.info("Loading Order Cache ...");
        String sql = "SELECT * FROM java_cache.order";
        List<OrderDetails> result = jdbcTemplate.query(sql, (rs, rowNum) ->
                new OrderDetails(
                        rs.getString("userId"),
                        rs.getString("orderDate"),
                        rs.getString("itemId"),
                        rs.getInt("quantity"),
                        rs.getDouble("unitPrice"),
                        rs.getDouble("totalPrice")
                        ));
        result.forEach(row -> orderDetailsCache.add(row));
    }

    @Override
    public OrderDetails getOrderDB(String userId, String orderDate, String itemId){
        String sql = "SELECT * FROM java_cache.order WHERE userId=? AND orderDate=? AND itemId=?";
        List<OrderDetails> result = jdbcTemplate.query(sql, new Object[]{userId, orderDate, itemId}, (rs, rowNum) ->
                new OrderDetails(
                        rs.getString("userId"),
                        rs.getString("orderDate"),
                        rs.getString("itemId"),
                        rs.getInt("quantity"),
                        rs.getDouble("unitPrice"),
                        rs.getDouble("totalPrice")
                ));
        return result.get(0);
    }
}
