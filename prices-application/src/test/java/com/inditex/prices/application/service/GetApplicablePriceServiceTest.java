package com.inditex.prices.application.service;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.port.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceServiceTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 16, 0);

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private GetApplicablePriceService service;

    @Test
    void returnsTheOnlyValidPrice() {
        var price = price(1, 0);
        when(priceRepository.findValidAt(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(List.of(price));

        assertThat(service.getApplicablePrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).isEqualTo(price);
    }

    @Test
    void returnsThePriceWithHighestPriorityWhenSeveralAreValid() {
        var lowPriority = price(1, 0);
        var highPriority = price(2, 1);
        when(priceRepository.findValidAt(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .thenReturn(List.of(lowPriority, highPriority, price(3, 0)));

        assertThat(service.getApplicablePrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).isEqualTo(highPriority);
    }

    @Test
    void throwsPriceNotFoundWhenNoPriceIsValid() {
        when(priceRepository.findValidAt(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(List.of());

        assertThatThrownBy(() -> service.getApplicablePrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .isInstanceOf(PriceNotFoundException.class);
    }

    private static Price price(int priceList, int priority) {
        return new Price(BRAND_ID, PRODUCT_ID, priceList,
                LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                priority, new BigDecimal("35.50"), "EUR");
    }
}
