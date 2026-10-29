package com.inditex.prices.config;

import com.inditex.prices.application.usecase.GetApplicablePriceUseCaseImpl;
import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.domain.port.out.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(PriceRepository priceRepository) {
        return new GetApplicablePriceUseCaseImpl(priceRepository);
    }
}
