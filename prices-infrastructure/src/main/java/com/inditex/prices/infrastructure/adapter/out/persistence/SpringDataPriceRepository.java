package com.inditex.prices.infrastructure.adapter.out.persistence;

import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

interface SpringDataPriceRepository extends Repository<PriceEntity, Long> {

    Optional<PriceEntity> findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long brandId, Long productId, LocalDateTime startDate, LocalDateTime endDate);
}
