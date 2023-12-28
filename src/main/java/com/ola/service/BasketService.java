package com.ola.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ola.entity.Basket;
import com.ola.repository.BasketRepository;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    public void removeProductFromAllBaskets(Long productNo) {
        List<Basket> baskets = basketRepository.findAll();
        for (Basket basket : baskets) {
            basket.removeProduct(productNo);
            basketRepository.save(basket);
        }
    }
}
