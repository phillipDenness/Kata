package com.phillip.denness.kata.service;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PricingRules {
    private Map<String, PriceModifier> itemMap;

    public PricingRules() {
        itemMap = new HashMap<>();
        itemMap.put("A", new PriceModifier(50, 20, 3));
        itemMap.put("B", new PriceModifier(30, 15, 2));
        itemMap.put("C", new PriceModifier(20));
        itemMap.put("D", new PriceModifier(15));
    }

    public Integer calculateTotalPrice(List<String> basketItems) {
        Integer totalPrice = 0;
        for (Map.Entry<String, PriceModifier> entry : itemMap.entrySet()) {
            PriceModifier priceModifier = entry.getValue();
            String itemId = entry.getKey();

            int occurrences = Collections.frequency(basketItems, itemId);
            Integer totalCost = occurrences * itemMap.get(itemId).price;

            if (discountActive(priceModifier)) {
                int discountsApplicable = occurrences / priceModifier.discountBulk;
                Integer totalDiscount = discountsApplicable * priceModifier.discountPrice;
                totalPrice = totalPrice + (totalCost - totalDiscount);
            } else {
                totalPrice = totalPrice + totalCost;
            }
        }
        return totalPrice;
    }

    private boolean discountActive(PriceModifier priceModifier) {
        return priceModifier.discountBulk != null || priceModifier.discountPrice != null;
    }

    public void addRule(String itemId, Integer price, Integer discountPrice, Integer discountBulk) {
        itemMap.put(itemId, new PriceModifier(price, discountPrice, discountBulk));
    }

    public Map<String, PriceModifier> getItemMap() {
        return itemMap;
    }

    public class PriceModifier {
        private Integer price;
        private Integer discountPrice;
        private Integer discountBulk;

        public PriceModifier(Integer price) {
            this.price = price;
            this.discountPrice = null;
            this.discountBulk = null;
        }

        public PriceModifier(Integer price, Integer discountPrice, int discountBulk) {
            this.price = price;
            this.discountPrice = discountPrice;
            this.discountBulk = discountBulk;
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
}
