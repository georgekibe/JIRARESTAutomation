package JIRA;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreatingIssue {

    @Test
    public void createIssue() {
        RestAssured.baseURI = "https://georgekibeacademy.atlassian.net";

        // Creating the issue on JIRA
        String createIssueResponse = given().log().all()
                .header("Authorization", "Basic Z2Vvcmdla2liZTUwMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSVB0TGw0ZzRGd2w2NHNLWnc4b25KbzhMVERBallRcWswUWRzU2xrb2VWZC0td2tJOXlsQmI2RV9hbDVtZVBoclVTMUdWLWZGbWVqbnZCZHhPSlExcW5kR3haa3ZQZ0lpejQ4cUptZ1Q1M1dTUGNnbVhVYUU5LU5FOF9nZktwSkxwaDBoTUNHMWt6alpWaVkxN29DQjVZSjdDTXNleUs5U0dlX05RekVDRkxNPUVFMTFFRDZC")
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
                .then().log().all().assertThat().statusCode(201).contentType("application/json").extract().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        String issueID = js.getString("id");
        System.out.println("ID: " + issueID);

        //Adding an attachment to the issue I have just created above and asserting success

        given().pathParam("id", issueID)
                .header("X-Atlassian-Token","no-check")
                .header("Authorization", "Basic Z2Vvcmdla2liZTUwMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwSVB0TGw0ZzRGd2w2NHNLWnc4b25KbzhMVERBallRcWswUWRzU2xrb2VWZC0td2tJOXlsQmI2RV9hbDVtZVBoclVTMUdWLWZGbWVqbnZCZHhPSlExcW5kR3haa3ZQZ0lpejQ4cUptZ1Q1M1dTUGNnbVhVYUU5LU5FOF9nZktwSkxwaDBoTUNHMWt6alpWaVkxN29DQjVZSjdDTXNleUs5U0dlX05RekVDRkxNPUVFMTFFRDZC")
                .multiPart("file", new File("C:/Users/georgek/Pictures/download.jpg"))
                .when()
                .post("/rest/api/3/issue/{id}/attachments")
                .then().log().all().assertThat().statusCode(200);

    }


}
