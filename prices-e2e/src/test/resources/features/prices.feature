Feature: Applicable price query
  As a client of the e-commerce platform
  I want to know the final price of a product of a brand at a given date
  So that the price list with the highest priority is applied

  Scenario Outline: <test>: product 35455 of brand 1 (ZARA) at <applicationDate>
    When the price of product 35455 of brand 1 is requested at "<applicationDate>"
    Then the response status is 200
    And the price list <priceList> applies with a final price of <price> EUR from "<startDate>" to "<endDate>"

    Examples:
      | test   | applicationDate     | priceList | price | startDate           | endDate             |
      | Test 1 | 2020-06-14T10:00:00 | 1         | 35.50 | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 |
      | Test 2 | 2020-06-14T16:00:00 | 2         | 25.45 | 2020-06-14T15:00:00 | 2020-06-14T18:30:00 |
      | Test 3 | 2020-06-14T21:00:00 | 1         | 35.50 | 2020-06-14T00:00:00 | 2020-12-31T23:59:59 |
      | Test 4 | 2020-06-15T10:00:00 | 3         | 30.50 | 2020-06-15T00:00:00 | 2020-06-15T11:00:00 |
      | Test 5 | 2020-06-16T21:00:00 | 4         | 38.95 | 2020-06-15T16:00:00 | 2020-12-31T23:59:59 |

  Scenario Outline: No price applies to product <productId> of brand <brandId> at <applicationDate>
    When the price of product <productId> of brand <brandId> is requested at "<applicationDate>"
    Then the response status is 404
    And the problem detail is "No price found for brand <brandId> and product <productId> at <detailDate>"

    Examples:
      | productId | brandId | applicationDate     | detailDate       |
      | 99999     | 1       | 2020-06-14T10:00:00 | 2020-06-14T10:00 |
      | 35455     | 2       | 2020-06-14T10:00:00 | 2020-06-14T10:00 |
      | 35455     | 1       | 2019-06-14T10:00:00 | 2019-06-14T10:00 |

  Scenario Outline: Invalid request: <reason>
    When the price is requested with applicationDate "<applicationDate>", productId "<productId>" and brandId "<brandId>"
    Then the response status is 400

    Examples:
      | reason                  | applicationDate     | productId | brandId |
      | missing date            |                     | 35455     | 1       |
      | missing product         | 2020-06-14T10:00:00 |           | 1       |
      | missing brand           | 2020-06-14T10:00:00 | 35455     |         |
      | malformed date          | 14/06/2020 10:00    | 35455     | 1       |
      | non numeric product     | 2020-06-14T10:00:00 | abc       | 1       |
      | product lower than 1    | 2020-06-14T10:00:00 | 0         | 1       |
      | brand lower than 1      | 2020-06-14T10:00:00 | 35455     | -1      |
