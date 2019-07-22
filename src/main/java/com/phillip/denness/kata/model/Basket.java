package com.phillip.denness.kata.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    public Integer getTotalPrice;

    public Basket() {
        this.items = new ArrayList<>();
    }

    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public Integer getGetTotalPrice() {
        return getTotalPrice;
    }

    public void setGetTotalPrice(Integer getTotalPrice) {
        this.getTotalPrice = getTotalPrice;
    }
}
