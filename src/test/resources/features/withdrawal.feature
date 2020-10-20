Feature: US 2
Scenario: In order to retrieve some or all of my savings
    Given As a bank client "bs33333"
    When I want to make a withdrawal of "55.03" from my account
    Then the withdrawal is done


