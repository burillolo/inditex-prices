package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.port.out.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceRepositoryAdapter implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;
    private final PriceEntityMapper priceEntityMapper;

    PriceRepositoryAdapter(SpringDataPriceRepository springDataPriceRepository, PriceEntityMapper priceEntityMapper) {
        this.springDataPriceRepository = springDataPriceRepository;
        this.priceEntityMapper = priceEntityMapper;
    }

    @Override
    public Optional<Price> findApplicable(Long brandId, Long productId, LocalDateTime applicationDate) {
        return springDataPriceRepository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId, productId, applicationDate, applicationDate)
                .map(priceEntityMapper::entityToDomain);
    }
}
