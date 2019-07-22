package com.phillip.denness.kata.service;

import com.phillip.denness.kata.exceptions.BasketNotFoundException;
import com.phillip.denness.kata.exceptions.ItemNotFoundException;
import com.phillip.denness.kata.model.Basket;
import com.phillip.denness.kata.repository.BasketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private BasketRepository repository;
    private PricingRules pricingRules;

    public BasketService(BasketRepository repository, PricingRules pricingRules) {
        this.repository = repository;
        this.pricingRules = pricingRules;
    }

    public void createBasket() {
        Basket basket = new Basket();
        repository.save(basket);
    }

    public void addToBasket(String item) {
        Optional<Basket> basket = repository.getBasket();

        if (pricingRules.getItemMap().get(item) == null) {
            throw new ItemNotFoundException();
        }
        basket.orElseThrow(BasketNotFoundException::new)
                .getItems().add(item);
    }

    public Basket getBasket() {
        Optional<Basket> basket = repository.getBasket();
        List<String> items = basket.orElseThrow(BasketNotFoundException::new)
                .getItems();
        Integer total = pricingRules.calculateTotalPrice(items);

        basket.get().setGetTotalPrice(total);
        return basket.get();
    }
}
