package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.api.PricesApi;
import com.inditex.prices.api.model.PriceResponse;
import com.inditex.prices.application.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.infrastructure.adapter.mapper.PriceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PriceController implements PricesApi {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;
    private final PriceMapper priceMapper;

    public PriceController(GetApplicablePriceUseCase getApplicablePriceUseCase, PriceMapper priceMapper) {
        this.getApplicablePriceUseCase = getApplicablePriceUseCase;
        this.priceMapper = priceMapper;
    }

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        var price = getApplicablePriceUseCase.getApplicablePrice(brandId, productId, applicationDate);
        return ResponseEntity.ok(priceMapper.domainToResponse(price));
    }
}
