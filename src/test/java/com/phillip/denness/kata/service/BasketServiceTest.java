package com.phillip.denness.kata.service;

import com.phillip.denness.kata.exceptions.BasketNotFoundException;
import com.phillip.denness.kata.exceptions.ItemNotFoundException;
import com.phillip.denness.kata.model.Basket;
import com.phillip.denness.kata.repository.BasketRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BasketServiceTest {

    @Mock
    private BasketRepository repository;

    @Mock
    private PricingRules pricingRules;

    private BasketService service;

    private Basket mockBasket;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BasketService(repository, pricingRules);

        HashMap map = new HashMap();
        map.put("A", "some value");
        when(pricingRules.getItemMap()).thenReturn(map);

        mockBasket = new Basket();
        when(repository.getBasket()).thenReturn(Optional.of(mockBasket));
    }

    @Test
    public void shouldSaveBasketWhenBasketCreateIsCalled() {
        service.createBasket();
        verify(repository).save(any(Basket.class));
    }

    @Test
    public void shouldAddItemToBasketWhenAddToBasketIsCalled() {
        String item = "A";
        service.addToBasket(item);

        List<String> expectedBasketItems = Collections.singletonList(item);
        assertThat(mockBasket.getItems(), is(expectedBasketItems));
    }

    @Test(expected = BasketNotFoundException.class)
    public void shouldThrowNoBasketExceptionIfBasketWasNotCreated() {
        when(repository.getBasket()).thenReturn(Optional.empty());

        String item = "A";
        service.addToBasket(item);
    }

    @Test(expected = ItemNotFoundException.class)
    public void shouldThrowNoItemFoundExceptionIfBasketWasNotCreated() {
        when(pricingRules.getItemMap()).thenReturn(new HashMap<>());

        String item = "A";
        service.addToBasket(item);
    }

    @Test
    public void shouldCallCalculateTotalPrice() {
        service.getBasket();
        verify(pricingRules).calculateTotalPrice(any());
    }

    @Test
    public void shouldCalculateTotalPrice() {
        Integer expectedTotal = 99;
        when(pricingRules.calculateTotalPrice(anyList())).thenReturn(expectedTotal);

        Basket basket = service.getBasket();
        verify(pricingRules).calculateTotalPrice(any());
        assertThat(basket.getTotalPrice, is(expectedTotal));
    }
}
