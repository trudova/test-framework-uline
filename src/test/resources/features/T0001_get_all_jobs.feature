@wip
Feature: authorised user must see all the jobs applications

  Scenario: GET all jobs end point being called
    Given user call "jobs" end point
    Then status code should be two hundred, response time should be less them two seconds
    And  count parameter should be equal to actual number of the jobs

