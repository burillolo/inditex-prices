package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    @Mapping(target = "amount", source = "price")
    Price entityToDomain(PriceEntity priceEntity);
}
