package com.inditex.prices.domain.port;

import com.inditex.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {

    List<Price> findValidAt(Long brandId, Long productId, LocalDateTime applicationDate);
}
