package com.inditex.prices.application.service;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.port.PriceRepository;

import java.time.LocalDateTime;

public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;

    public GetApplicablePriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository.findValidAt(brandId, productId, applicationDate).stream()
                .reduce((selected, candidate) -> candidate.hasPrecedenceOver(selected) ? candidate : selected)
                .orElseThrow(() -> new PriceNotFoundException(brandId, productId, applicationDate));
    }
}
