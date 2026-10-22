package com.inditex.prices.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

interface SpringDataPriceRepository extends Repository<PriceEntity, Long> {

    @Query("""
            select p from PriceEntity p
            where p.brandId = :brandId
              and p.productId = :productId
              and :applicationDate between p.startDate and p.endDate
            """)
    List<PriceEntity> findValidAt(@Param("brandId") Long brandId,
                                  @Param("productId") Long productId,
                                  @Param("applicationDate") LocalDateTime applicationDate);
}
