Feature: Login Functionality

  @smoke
  Scenario Outline: Login with valid credentials
    Given User launches the application
    When User enters username "<username>"
    And User enters password "<password>"
    And User clicks Login
    Then Home page should be displayed

    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
      | problem_user  | secret_sauce |
      | visual_user   | secret_sauce |

  @smoke
  Scenario: Login using DataTable
    Given User launches the application
    When User enters login credentials
      | username      | password     |
      | standard_user | secret_sauce |
    And User clicks Login
    Then Home page should be displayed
