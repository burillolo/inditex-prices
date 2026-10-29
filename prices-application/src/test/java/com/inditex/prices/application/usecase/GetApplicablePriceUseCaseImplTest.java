package com.inditex.prices.application.usecase;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.port.out.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceUseCaseImplTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 16, 0);

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private GetApplicablePriceUseCaseImpl getApplicablePriceUseCase;

    @Test
    void returnsTheApplicablePrice() {
        var price = new Price(BRAND_ID, PRODUCT_ID, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30),
                1, new BigDecimal("25.45"), "EUR");
        when(priceRepository.findApplicable(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(Optional.of(price));

        assertThat(getApplicablePriceUseCase.getApplicablePrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).isEqualTo(price);
    }

    @Test
    void throwsPriceNotFoundWhenNoPriceApplies() {
        when(priceRepository.findApplicable(BRAND_ID, PRODUCT_ID, APPLICATION_DATE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getApplicablePriceUseCase.getApplicablePrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .isInstanceOf(PriceNotFoundException.class);
    }
}
