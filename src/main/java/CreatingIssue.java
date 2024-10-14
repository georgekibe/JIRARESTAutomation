import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreatingIssue {

    @Test
    public void createIssue() {
        RestAssured.baseURI = "https://georgekibeacademy.atlassian.net";

        // Creating the issue on JIRA
        given().log().all().header("Authorization", "Basic Z2Vvcmdla2liZTUwMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSVB0TGw0ZzRGd2w2NHNLWnc4b25KbzhMVERBallRcWswUWRzU2xrb2VWZC0td2tJOXlsQmI2RV9hbDVtZVBoclVTMUdWLWZGbWVqbnZCZHhPSlExcW5kR3haa3ZQZ0lpejQ4cUptZ1Q1M1dTUGNnbVhVYUU5LU5FOF9nZktwSkxwaDBoTUNHMWt6alpWaVkxN29DQjVZSjdDTXNleUs5U0dlX05RekVDRkxNPUVFMTFFRDZC")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"JIR\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Links are not working 2\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}\n")
                .when().post("/rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).contentType("application/json");

    }


}
