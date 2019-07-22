package com.phillip.denness.kata.controller;

import com.phillip.denness.kata.model.PriceRequest;
import com.phillip.denness.kata.service.PricingRules;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private PricingRules service;

    public PriceController(PricingRules service) {
        this.service = service;
    }

    @PostMapping("/price")
    public ResponseEntity createPriceRule(@RequestBody PriceRequest priceRequest) {

        if (priceRequest.getItemId() == null || priceRequest.getPrice() == null
                || priceRequest.getPrice() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please supply price and itemId. Any numbers must not be less than 1");
        }
        service.addRule(priceRequest.getItemId(),
                priceRequest.getPrice(),
                priceRequest.getDiscountPrice(),
                priceRequest.getDiscountBulk());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.getItemMap());
    }
}
