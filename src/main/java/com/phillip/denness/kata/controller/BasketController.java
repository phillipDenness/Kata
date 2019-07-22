package com.phillip.denness.kata.controller;

import com.phillip.denness.kata.exceptions.BasketNotFoundException;
import com.phillip.denness.kata.exceptions.ItemNotFoundException;
import com.phillip.denness.kata.model.Basket;
import com.phillip.denness.kata.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasketController {

    private BasketService service;

    public BasketController(BasketService service) {
        this.service = service;
    }

    @PostMapping("/basket")
    public ResponseEntity createBasket() {

        service.createBasket();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/basket/{itemId}")
    public ResponseEntity updateBasket(@PathVariable String itemId) {

        try {
            service.addToBasket(itemId);
        } catch (BasketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please create basket before attempting to add to it");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(itemId + " is not a valid item");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/basket")
    public ResponseEntity getBasket() {
        try {
            Basket basket = service.getBasket();
            return ResponseEntity.status(HttpStatus.OK).body(basket);
        } catch (BasketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please create basket before attempting to get it");
        }
    }
}
