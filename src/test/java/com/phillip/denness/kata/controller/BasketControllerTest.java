package com.phillip.denness.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phillip.denness.kata.exceptions.BasketNotFoundException;
import com.phillip.denness.kata.exceptions.ItemNotFoundException;
import com.phillip.denness.kata.model.Basket;
import com.phillip.denness.kata.service.BasketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BasketController.class)
public class BasketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BasketService service;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void shouldCallBasketCreateOnBasketServiceWhenPostReceived() throws Exception {
        mvc.perform(post("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated());

        verify(service).createBasket();
    }

    @Test
    public void shouldCallAddToBasketWhenPutReceived() throws Exception {
        String itemId = "A";
        mvc.perform(put("/basket/" + itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        verify(service).addToBasket(anyString());
    }

    @Test
    public void shouldHandleBasketNotFoundExceptionWhenPutReceived() throws Exception {
        doThrow(BasketNotFoundException.class).when(service).addToBasket(anyString());
        String itemId = "A";

        mvc.perform(put("/basket/" + itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());

        verify(service).addToBasket(anyString());
    }

    @Test
    public void shouldHandleItemNotFoundExceptionWhenPutReceived() throws Exception {
        doThrow(ItemNotFoundException.class).when(service).addToBasket(anyString());
        String itemId = "A";

        mvc.perform(put("/basket/" + itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());

        verify(service).addToBasket(anyString());
    }

    @Test
    public void shouldReturnBasketWithGet() throws Exception {
        when(service.getBasket()).thenReturn(new Basket());

        MvcResult mvcResult = mvc.perform(get("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        Basket actualBasket = mapper.readValue(mvcResult.getResponse().getContentAsString(), Basket.class);
        assertThat(actualBasket.getItems().size(), is(0));
    }

    @Test
    public void shouldReturnBadRequestWhenGetBasketIsCalledBeforeCreateBasket() throws Exception {
        when(service.getBasket()).thenThrow(BasketNotFoundException.class);

        mvc.perform(get("/basket")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}