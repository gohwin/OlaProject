package com.ola.service;

import com.ola.entity.OrderList;
import com.ola.entity.Product;
import com.ola.repository.OrderListRepository;
import com.ola.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesService {

    @Autowired
    private OrderListRepository orderListRepository;

    @Autowired
    private ProductRepository productRepository;

    private Map<String, Map<Long, Long>> calculateSalesData(List<OrderList> orders) {
        Map<Long, Long> salesAmountData = new HashMap<>();
        Map<Long, Integer> salesVolumeData = new HashMap<>();

        for (OrderList order : orders) {
            Map<Long, Integer> productQuantities = order.getProductQuantities();
            for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
                Long productId = entry.getKey();
                Integer quantity = entry.getValue();

                // Calculate sales volume
                salesVolumeData.put(productId, salesVolumeData.getOrDefault(productId, 0) + quantity);

                // Calculate sales amount
                Product product = productRepository.findById(productId).orElse(null);
                if (product != null) {
                    long price = product.getPrice();
                    salesAmountData.put(productId, salesAmountData.getOrDefault(productId, 0L) + (price * quantity));
                }
            }
        }

        Map<String, Map<Long, Long>> combinedData = new HashMap<>();
        combinedData.put("salesVolume", convertIntegerMapToLongMap(salesVolumeData));
        combinedData.put("salesAmount", salesAmountData);
        return combinedData;
    }

    private Map<Long, Long> convertIntegerMapToLongMap(Map<Long, Integer> map) {
        Map<Long, Long> longMap = new HashMap<>();
        map.forEach((key, value) -> longMap.put(key, value.longValue()));
        return longMap;
    }

    public Map<String, Map<Long, Long>> getTotalSalesData() {
        List<OrderList> orders = orderListRepository.findAll();
        return calculateSalesData(orders);
    }

    public Map<String, Map<Long, Long>> getMonthlySalesData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();

        List<OrderList> orders = orderListRepository.findOrdersBetweenDates(oneMonthAgo, new Date());
        return calculateSalesData(orders);
    }

    public Map<String, Map<Long, Long>> get3MonthsSalesData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date threeMonthsAgo = cal.getTime();

        List<OrderList> orders = orderListRepository.findOrdersBetweenDates(threeMonthsAgo, new Date());
        return calculateSalesData(orders);
    }

    public Map<String, Map<Long, Long>> get6MonthsSalesData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        Date sixMonthsAgo = cal.getTime();

        List<OrderList> orders = orderListRepository.findOrdersBetweenDates(sixMonthsAgo, new Date());
        return calculateSalesData(orders);
    }

    public Map<String, Map<Long, Long>> get1YearSalesData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date oneYearAgo = cal.getTime();

        List<OrderList> orders = orderListRepository.findOrdersBetweenDates(oneYearAgo, new Date());
        return calculateSalesData(orders);
    }
}
