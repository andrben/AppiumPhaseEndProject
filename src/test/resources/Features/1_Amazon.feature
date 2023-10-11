Feature: This feature will be used to test the Add to Cart functionality in Amazon app

  Scenario: Add product to cart
    Given User is logged into Amazon app
    When User searches for and selects "airpods pro 2"
    And User chooses the product named "Apple AirPods Pro (2nd Generation) ​​​​​​​"
    And User adds it to cart
    Then The product gets added to cart