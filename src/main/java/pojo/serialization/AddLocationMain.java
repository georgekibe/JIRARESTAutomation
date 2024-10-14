package pojo.serialization;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/*

Adding location and using serialization using POJO classes to insert REST request Payload
This is the main classes

 */
public class AddLocationMain {


    @Test
    public void addingLocation() {

       String addLocationResponse =  given().queryParam("key", "qaclick123")
                .body()
                .when().post("https://rahulshettyacademy.com/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().asString();

    }


}
