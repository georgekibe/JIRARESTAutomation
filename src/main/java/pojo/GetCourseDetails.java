package pojo;


import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetCourseDetails {

    // The request I am using uses OAuth for aunthentication
    // Get the authorization (client credentials
    // Use the authorization  to get the course details
    //Then pass the response to use POJO Classes


    String access_token;

    @Test
    public void getAccessToken() {
        // get access token
        String tokenResponse = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath js  =  new JsonPath(tokenResponse);
         access_token = js.getString("access_token");
    }

    @Test
    public void getCourseDetails(){
        // get course details

        given().pathParam("access_token", access_token)
                .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token={access_token}")
                .then().log().all();

    }

}
