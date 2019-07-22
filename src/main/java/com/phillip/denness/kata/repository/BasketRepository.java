package com.phillip.denness.kata.repository;

import com.phillip.denness.kata.model.Basket;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BasketRepository {

    /*
    Repository in memory only to avoid over extending scope of test
     */
    private Basket currentBasket;

    public void save(Basket basket) {
        this.currentBasket = basket;
    }

    public Optional<Basket> getBasket() {
        return Optional.ofNullable(currentBasket);
    }
}
