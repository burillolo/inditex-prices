package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PriceRepositoryAdapter.class, PriceEntityMapperImpl.class})
class PriceRepositoryAdapterTest {

    @Autowired
    private PriceRepositoryAdapter adapter;

    @Test
    void mapsAllFieldsOfTheApplicablePrice() {
        var price = adapter.findApplicable(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

        assertThat(price).contains(new Price(1L, 35455L, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0, new BigDecimal("35.50"), "EUR"));
    }

    @Test
    void returnsThePriceWithHighestPriorityWhenSeveralAreValid() {
        var price = adapter.findApplicable(1L, 35455L, LocalDateTime.of(2020, 6, 14, 16, 0));

        assertThat(price).map(Price::priceList).contains(2);
    }

    @Test
    void includesPricesWhoseRangeStartsAtTheApplicationDate() {
        var price = adapter.findApplicable(1L, 35455L, LocalDateTime.of(2020, 6, 14, 15, 0));

        assertThat(price).map(Price::priceList).contains(2);
    }

    @Test
    void includesPricesWhoseRangeEndsAtTheApplicationDate() {
        var price = adapter.findApplicable(1L, 35455L, LocalDateTime.of(2020, 6, 14, 18, 30));

        assertThat(price).map(Price::priceList).contains(2);
    }

    @Test
    void findsNothingForUnknownProduct() {
        assertThat(adapter.findApplicable(1L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0))).isEmpty();
    }

    @Test
    void findsNothingForUnknownBrand() {
        assertThat(adapter.findApplicable(2L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0))).isEmpty();
    }

    @Test
    void findsNothingOutsideAnyRange() {
        assertThat(adapter.findApplicable(1L, 35455L, LocalDateTime.of(2021, 1, 1, 0, 0))).isEmpty();
    }
}
