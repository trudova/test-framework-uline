package com.uline.companycode.step_definitions;

import com.uline.companycode.utilities.ConfigurationReader;
import com.uline.companycode.utilities.DB_utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.*;

public class Hooks {

    public static RequestSpecification jobRequestSpec;
    public static ResponseSpecification jobResponseSpec;

    @Before
    public static void init() {
        baseURI = ConfigurationReader.getProperty("environment");
        basePath = ConfigurationReader.getProperty("base.path");
        jobRequestSpec = given().contentType(ContentType.JSON);
        jobResponseSpec = expect().logDetail(LogDetail.BODY)
                .statusCode(200)
                .contentType(ContentType.JSON).time(Matchers.lessThan(2000L));

//        // TODO db connection for db testing
//        String url = ConfigurationReader.getProperty("uline.database.url");
//        String username = ConfigurationReader.getProperty("uline.database.username");
//        String password = ConfigurationReader.getProperty("uline.database.password");
//        DB_utility.createConnection(url,username,password);
    }

    @After
    public static void cleanup() {
        reset();
        //TODO DB destroy for closing DB connection
        //DB_utility.destroy();
    }


}
