package com.inditex.prices.application.usecase;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.domain.port.out.PriceRepository;

import java.time.LocalDateTime;

public class GetApplicablePriceUseCaseImpl implements GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;

    public GetApplicablePriceUseCaseImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findApplicable(brandId, productId, applicationDate)
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId, applicationDate));
    }
}
