@wip
Feature: Login
  As I user, I should be able to login

  Scenario: Login as a user
    Given I login as a user
    Then  Status code should be 200