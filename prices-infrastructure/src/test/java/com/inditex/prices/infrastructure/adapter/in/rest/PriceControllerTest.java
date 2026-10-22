package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCase;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.adapter.mapper.PriceMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import(PriceMapperImpl.class)
class PriceControllerTest {

    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 16, 0);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetApplicablePriceUseCase getApplicablePriceUseCase;

    @Test
    void returnsTheApplicablePrice() throws Exception {
        when(getApplicablePriceUseCase.getApplicablePrice(1L, 35455L, APPLICATION_DATE)).thenReturn(new Price(
                1L, 35455L, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30),
                1, new BigDecimal("25.45"), "EUR"));

        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void returnsNotFoundWhenNoPriceApplies() throws Exception {
        when(getApplicablePriceUseCase.getApplicablePrice(1L, 35455L, APPLICATION_DATE))
                .thenThrow(new PriceNotFoundException(1L, 35455L, APPLICATION_DATE));

        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("No price found for brand 1 and product 35455 at 2020-06-14T16:00"));
    }

    @Test
    void returnsBadRequestWhenParameterIsMissing() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void returnsBadRequestWhenDateIsMalformed() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "14/06/2020")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void returnsBadRequestWhenIdentifierIsNotNumeric() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "abc")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void returnsBadRequestWhenIdentifierIsNotPositive() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400));
    }
}
