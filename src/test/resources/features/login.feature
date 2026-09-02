Feature: Complete Purchase Flow

  @smoke
  Scenario Outline: User completes a purchase successfully
    Given User launches the application
    When User logs in with username "<username>" and password "<password>"
    And User adds "<product>" to the cart
    And User proceeds to checkout
    And User enters first name "<firstName>", last name "<lastName>" and zip code "<zipCode>"
    And User continues to checkout overview
    Then Checkout overview should display "<product>"
    When User finishes the order
    Then Checkout complete page should be displayed
    And Thank you message should be displayed
    When User goes back home
    And User logs out
    Then Login page should be displayed

    Examples:
      | username      | password     | product                     | firstName | lastName       | zipCode |
      | standard_user | secret_sauce | Sauce Labs Backpack         | Balaji    | G              | 600001  |
      | visual_user   | secret_sauce | Sauce Labs Fleece Jacket    | Balaji    | Ganesamurthy   | 600001  |