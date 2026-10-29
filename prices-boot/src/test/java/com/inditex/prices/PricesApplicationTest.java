package com.inditex.prices;

import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PricesApplicationTest {

    @Autowired
    private GetApplicablePriceUseCase getApplicablePriceUseCase;

    @Test
    void wiresTheUseCaseWithItsAdapters() {
        assertThat(getApplicablePriceUseCase).isNotNull();
    }
}
