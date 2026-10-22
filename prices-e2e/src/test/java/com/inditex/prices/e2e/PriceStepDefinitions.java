package com.inditex.prices.e2e;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;

public class PriceStepDefinitions {

    private static final RestAssuredConfig DECIMAL_PRICES = RestAssuredConfig.config()
            .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));

    @LocalServerPort
    private int port;

    @Value("${prices.base-url:}")
    private String baseUrl;

    private long requestedProductId;
    private long requestedBrandId;
    private Response response;

    @When("the price of product {long} of brand {long} is requested at {string}")
    public void requestPrice(long productId, long brandId, String applicationDate) {
        requestedProductId = productId;
        requestedBrandId = brandId;
        response = request()
                .queryParam("applicationDate", applicationDate)
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .get("/prices");
    }

    @When("the price is requested with applicationDate {string}, productId {string} and brandId {string}")
    public void requestPriceWithRawParameters(String applicationDate, String productId, String brandId) {
        var request = request();
        Map.of("applicationDate", applicationDate, "productId", productId, "brandId", brandId)
                .forEach((name, value) -> {
                    if (!value.isEmpty()) {
                        request.queryParam(name, value);
                    }
                });
        response = request.get("/prices");
    }

    @Then("the response status is {int}")
    public void verifyStatus(int status) {
        response.then().statusCode(status);
    }

    @Then("the price list {int} applies with a final price of {bigdecimal} {word} from {string} to {string}")
    public void verifyPrice(int priceList, BigDecimal price, String currency, String startDate, String endDate) {
        response.then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("productId", equalTo((int) requestedProductId))
                .body("brandId", equalTo((int) requestedBrandId))
                .body("priceList", equalTo(priceList))
                .body("price", comparesEqualTo(price))
                .body("currency", equalTo(currency))
                .body("startDate", equalTo(startDate))
                .body("endDate", equalTo(endDate));
    }

    @Then("the problem detail is {string}")
    public void verifyProblemDetail(String detail) {
        response.then()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .body("detail", equalTo(detail));
    }

    private RequestSpecification request() {
        return given().config(DECIMAL_PRICES).baseUri(baseUrl.isBlank() ? "http://localhost:" + port : baseUrl);
    }
}
