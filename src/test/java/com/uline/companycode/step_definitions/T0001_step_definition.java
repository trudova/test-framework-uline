package com.uline.companycode.step_definitions;

import com.uline.companycode.utilities.AuthTokens;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;


import java.util.List;

import static io.restassured.RestAssured.*;

public class T0001_step_definition {

    Response response;

    @Given("user call {string} end point")
    public void userCallEndPoint(String jobs) {
        response = given().spec(Hooks.jobRequestSpec)
                .header("Authorization", "Bearer " + AuthTokens.jobToken).get(jobs);
    }

    @Then("status code should be two hundred, response time should be less them two seconds")
    public void statusCodeShouldBeTwoHundredResponseTimeShouldBeLessThemTwoSeconds() {
        response.then().spec(Hooks.jobResponseSpec);
    }

    //TODO this one should not pass, report in target folder
    @Then("count parameter should be equal to actual number of the jobs")
    public void count_parameter_should_be_equal_to_actual_number_of_the_jobs() {
        List<Object> jobs = response.path("jobs");
        int count = response.path("count");
        Assertions.assertEquals(jobs.size(), count + 1);

    }


}
