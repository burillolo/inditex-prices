package com.inditex.prices.application.port.in;

import com.inditex.prices.domain.model.Price;

import java.time.LocalDateTime;

public interface GetApplicablePriceUseCase {

    Price getApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
