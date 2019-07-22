package com.phillip.denness.kata.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PricingRulesTest {

    private PricingRules pricingRules;

    @Before
    public void setUp() {
        pricingRules = new PricingRules();
    }

    @Test
    public void shouldReturnCorrectTotalForSingleValue() {
        List<String> basket = Arrays.asList("A");
        Integer actual = pricingRules.calculateTotalPrice(basket);

        assertThat(actual, is(50));
    }

    @Test
    public void shouldReturnCorrectTotalForSimpleAddedValues() {
        List<String> basket = Arrays.asList("A", "A");
        Integer actual = pricingRules.calculateTotalPrice(basket);

        assertThat(actual, is(100));
    }

    @Test
    public void shouldReturnCorrectTotalForDiscountApplied() {
        List<String> basket = Arrays.asList("A", "A", "A");
        Integer actual = pricingRules.calculateTotalPrice(basket);

        assertThat(actual, is(130));
    }

    @Test
    public void shouldReturnCorrectTotalForStackedDiscountApplied() {
        List<String> basket = Arrays.asList("A", "A", "A", "A", "A", "A", "A");
        Integer actual = pricingRules.calculateTotalPrice(basket);

        assertThat(actual, is(310));
    }

    @Test
    public void shouldReturnCorrectTotalMixedBasket() {
        List<String> basket = Arrays.asList("A", "B");
        Integer actual = pricingRules.calculateTotalPrice(basket);

        assertThat(actual, is(80));
    }

    @Test
    public void shouldApplyDiscountForEachItemTypeInBasket() {
        List<String> basket = Arrays.asList("A", "A", "A", "A", "A", "A", "A", "B", "B", "B");
        Integer actual = pricingRules.calculateTotalPrice(basket);

        assertThat(actual, is(385));
    }
}