package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.port.PriceRepository;
import com.inditex.prices.infrastructure.adapter.mapper.PriceMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryAdapter implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;
    private final PriceMapper priceMapper;

    PriceRepositoryAdapter(SpringDataPriceRepository springDataPriceRepository, PriceMapper priceMapper) {
        this.springDataPriceRepository = springDataPriceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public List<Price> findValidAt(Long brandId, Long productId, LocalDateTime applicationDate) {
        return springDataPriceRepository.findValidAt(brandId, productId, applicationDate).stream()
                .map(priceMapper::entityToDomain)
                .toList();
    }
}
