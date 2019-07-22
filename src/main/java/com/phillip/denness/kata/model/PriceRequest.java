package com.phillip.denness.kata.model;

public class PriceRequest {

    private String itemId;
    private Integer price;
    private Integer discountPrice;
    private Integer discountBulk;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getDiscountBulk() {
        return discountBulk;
    }

    public void setDiscountBulk(Integer discountBulk) {
        this.discountBulk = discountBulk;
    }
}
