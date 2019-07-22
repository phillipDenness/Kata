package com.phillip.denness.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phillip.denness.kata.model.PriceRequest;
import com.phillip.denness.kata.service.PricingRules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PricingRules service;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldCallBasketCreateOnBasketServiceWhenPostReceived() throws Exception {
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setDiscountBulk(1);
        priceRequest.setDiscountPrice(5);
        priceRequest.setItemId("E");
        priceRequest.setPrice(50);

        mvc.perform(post("/price")
                .content(mapper.writeValueAsString(priceRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

        verify(service).addRule(anyString(), anyInt(), anyInt(), anyInt());
    }


    @Test
    public void shouldThrowBadRequestIfItemIdIsNull() throws Exception {
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setDiscountBulk(1);
        priceRequest.setDiscountPrice(5);
        priceRequest.setItemId(null);
        priceRequest.setPrice(50);

        mvc.perform(post("/price")
                .content(mapper.writeValueAsString(priceRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowBadRequestIfPriceIsInvalid() throws Exception {
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setDiscountBulk(1);
        priceRequest.setDiscountPrice(5);
        priceRequest.setItemId("A");
        priceRequest.setPrice(-50);

        mvc.perform(post("/price")
                .content(mapper.writeValueAsString(priceRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAllowNoDiscount() throws Exception {
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setItemId("A");
        priceRequest.setPrice(50);

        mvc.perform(post("/price")
                .content(mapper.writeValueAsString(priceRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }
}