package com.uline.companycode.step_definitions;

import com.uline.companycode.utilities.ConfigurationReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class Login_step_definition {
    Response response;

    @Given("I login as a user")
    public void i_login_as_a_user() {

        baseURI = ConfigurationReader.getProperty("environment");
        basePath = ConfigurationReader.getProperty("base.path");
        Map<String, String> loginCred = new HashMap<>();
        loginCred.put("email", ConfigurationReader.getProperty("user1Email"));
        loginCred.put("password", ConfigurationReader.getProperty("user1Password"));
      response =  given()
                .contentType(ContentType.JSON).body(loginCred)
                .when()
                .post("auth/login")
                ;
    }

    @Then("Status code should be {int}")
    public void status_code_should_be(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
       response.then().contentType(ContentType.JSON)
                .statusCode(200).time(Matchers.lessThan(2000L));
    }

}
