package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.api.model.PriceResponse;
import com.inditex.prices.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {

    @Mapping(target = "price", source = "amount")
    PriceResponse domainToResponse(Price price);
}
