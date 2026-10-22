package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.adapter.mapper.PriceMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PriceRepositoryAdapter.class, PriceMapperImpl.class})
class PriceRepositoryAdapterTest {

    @Autowired
    private PriceRepositoryAdapter adapter;

    @Test
    void findsEveryPriceValidAtTheApplicationDate() {
        var prices = adapter.findValidAt(1L, 35455L, LocalDateTime.of(2020, 6, 14, 16, 0));

        assertThat(prices).extracting(Price::priceList).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void mapsAllFieldsToTheDomainModel() {
        var prices = adapter.findValidAt(1L, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

        assertThat(prices).containsExactly(new Price(1L, 35455L, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0, new BigDecimal("35.50"), "EUR"));
    }

    @Test
    void includesPricesWhoseRangeBoundaryMatchesTheApplicationDate() {
        var prices = adapter.findValidAt(1L, 35455L, LocalDateTime.of(2020, 6, 14, 18, 30));

        assertThat(prices).extracting(Price::priceList).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    void findsNothingForUnknownProduct() {
        assertThat(adapter.findValidAt(1L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0))).isEmpty();
    }

    @Test
    void findsNothingOutsideAnyRange() {
        assertThat(adapter.findValidAt(1L, 35455L, LocalDateTime.of(2021, 1, 1, 0, 0))).isEmpty();
    }
}
