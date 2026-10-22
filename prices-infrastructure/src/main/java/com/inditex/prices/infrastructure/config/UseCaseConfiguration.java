package com.inditex.prices.infrastructure.config;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.application.service.GetApplicablePriceService;
import com.inditex.prices.domain.port.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(PriceRepository priceRepository) {
        return new GetApplicablePriceService(priceRepository);
    }
}
