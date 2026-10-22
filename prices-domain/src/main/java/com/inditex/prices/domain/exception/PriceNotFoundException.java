package com.inditex.prices.domain.exception;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long brandId, Long productId, LocalDateTime applicationDate) {
        super("No price found for brand %d and product %d at %s".formatted(brandId, productId, applicationDate));
    }
}
