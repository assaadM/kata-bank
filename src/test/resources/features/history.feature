Feature: US 3
Scenario: In order to check my operations
Given As a bank client "bs33333"
When I want to see the history (operation, date, amount, balance) of my operations
Then I can see history
