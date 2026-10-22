package com.inditex.prices.infrastructure.adapter.mapper;

import com.inditex.prices.api.model.PriceResponse;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.adapter.out.persistence.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "amount", source = "price")
    Price entityToDomain(PriceEntity priceEntity);

    @Mapping(target = "price", source = "amount")
    PriceResponse domainToResponse(Price price);
}
