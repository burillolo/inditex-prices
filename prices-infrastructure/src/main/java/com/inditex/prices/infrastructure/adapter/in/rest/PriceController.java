package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.api.PricesApi;
import com.inditex.prices.api.model.PriceResponse;
import com.inditex.prices.domain.port.in.GetApplicablePriceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PriceController implements PricesApi {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;
    private final PriceResponseMapper priceResponseMapper;

    public PriceController(GetApplicablePriceUseCase getApplicablePriceUseCase, PriceResponseMapper priceResponseMapper) {
        this.getApplicablePriceUseCase = getApplicablePriceUseCase;
        this.priceResponseMapper = priceResponseMapper;
    }

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        var price = getApplicablePriceUseCase.getApplicablePrice(brandId, productId, applicationDate);
        return ResponseEntity.ok(priceResponseMapper.domainToResponse(price));
    }
}
