package pojo.deserialization;


import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetCourseDetails {

    // The request I am using OAuth for authentication
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

        JsonPath js = new JsonPath(tokenResponse);
        access_token = js.getString("access_token");
    }

    @Test
    public void getCourseDetails() {
        // get course details

        GetPojoCourseDetails getCourseDetailsResponse = given().pathParam("access_token", access_token)
                .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token={access_token}")
                .then().log().all().extract().as(GetPojoCourseDetails.class);


        // print the linkedIn URL
        String linkedIn = getCourseDetailsResponse.getLinkedIn();
        System.out.println(linkedIn);
        // print the Cypress Course Price
        String cypressPrice = getCourseDetailsResponse.getCourses().getWebAutomation().get(0).getPrice();
        System.out.println(cypressPrice);
        // print the mobile entry that is for Appium
        String mobileCourseTitle = getCourseDetailsResponse.getCourses().getMobile().get(0).getCourseTitle();
        System.out.println(mobileCourseTitle);

        /// printing the sum for all the course under Web Automation

        int arraySize = getCourseDetailsResponse.getCourses().getWebAutomation().size();
        System.out.println("Size: " + arraySize);

        int sum = 0;
        for (int i = 0; i < arraySize; i++) {
            int price = Integer.parseInt(getCourseDetailsResponse.getCourses().getWebAutomation().get(i).getPrice());
            sum += price;
        }

        System.out.println("Sum: " + sum);

        // initialize course title and compare with output using assertion to match
        String[] expectedCourseTitles =  {"Selenium Webdriver Java", "Cypress", "Protractor"};
        List <String> expectedList = Arrays.asList(expectedCourseTitles); // convert string array to array list

        ArrayList<String> actualList = new ArrayList<String>();
        List<WebAutomation> titles = getCourseDetailsResponse.getCourses().getWebAutomation();

        for(int k=0; k<titles.size(); k++)
        {
            actualList.add(titles.get(k).getCourseTitle());
        }

        Assert.assertEquals(expectedList, actualList);

    }


}
