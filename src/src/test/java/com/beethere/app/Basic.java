package com.beethere.app; 
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class Basic {
    /* TODO: write a test that checks that api is up and running
       -Given the GitHub API is up and running
       -When a GET request is made to the /room endpoint
       -Then the response status code should be 200
    */

    public static void main(String[] args) { 
        RestAssured.baseURI = "http://localhost:8080/";
        String token = System.getenv("BEARER_TOKEN");
        if (token == null || token.isEmpty()) {
            //token = "rest-assured";
        RestAssured.given().log().all().queryParam("Bearer", "rest-assured")
        .header("Content-Type", "application/json")
        .header("Bearer", "eyJhbGciOiJIUzI1NiJ9.eyJsYXN0X25hbWUiOiJSaWNoaWUiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjo5NzIsImRlcGFydG1lbnQiOiJTYWxlcyIsInRpdGxlIjoiTWFuYWdlciIsImZpcnN0X25hbWUiOiJLb3JhIiwic3ViIjoiS29yYSBSaWNoaWUiLCJpYXQiOjE3NjIzODA1MjYsImV4cCI6MTc2MjM4NDEyNn0.BbjM5OUA3xb7Puq_ItydemCD0qNfefp3xX2lPMmeLDQ")
        .body(
                        "        \"location\": \"Mexico\",\n" + //
                        "        \"building\": \"Tokyo\",\n" + //
                        "        \"roomNumber\": \"2-101\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 1,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Japan\",\n" + //
                        "        \"building\": \"Tokyo\",\n" + //
                        "        \"roomNumber\": \"2-102\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 2,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Japan\",\n" + //
                        "        \"building\": \"Tokyo\",\n" + //
                        "        \"roomNumber\": \"4-220\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 3,\n" + //
                        "        \"seatCount\": 8\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Japan\",\n" + //
                        "        \"building\": \"Tokyo\",\n" + //
                        "        \"roomNumber\": \"1-100\",\n" + //
                        "        \"type\": \"auditorium\",\n" + //
                        "        \"id\": 4,\n" + //
                        "        \"seatCount\": 120\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Japan\",\n" + //
                        "        \"building\": \"Tokyo\",\n" + //
                        "        \"roomNumber\": \"1-110\",\n" + //
                        "        \"type\": \"video\",\n" + //
                        "        \"id\": 5,\n" + //
                        "        \"seatCount\": 24\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Brazil\",\n" + //
                        "        \"building\": \"Sao Paulo\",\n" + //
                        "        \"roomNumber\": \"1234\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 6,\n" + //
                        "        \"seatCount\": 18\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Brazil\",\n" + //
                        "        \"building\": \"Sao Paulo\",\n" + //
                        "        \"roomNumber\": \"1254\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 7,\n" + //
                        "        \"seatCount\": 8\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Brazil\",\n" + //
                        "        \"building\": \"Sao Paulo\",\n" + //
                        "        \"roomNumber\": \"1012\",\n" + //
                        "        \"type\": \"video\",\n" + //
                        "        \"id\": 8,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"United States\",\n" + //
                        "        \"building\": \"Dallas\",\n" + //
                        "        \"roomNumber\": \"212\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 9,\n" + //
                        "        \"seatCount\": 24\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"United States\",\n" + //
                        "        \"building\": \"Dallas\",\n" + //
                        "        \"roomNumber\": \"312\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 10,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"United States\",\n" + //
                        "        \"building\": \"Dallas\",\n" + //
                        "        \"roomNumber\": \"412\",\n" + //
                        "        \"type\": \"auditorium\",\n" + //
                        "        \"id\": 11,\n" + //
                        "        \"seatCount\": 220\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"United States\",\n" + //
                        "        \"building\": \"Dallas\",\n" + //
                        "        \"roomNumber\": \"512\",\n" + //
                        "        \"type\": \"video\",\n" + //
                        "        \"id\": 12,\n" + //
                        "        \"seatCount\": 32\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"United States\",\n" + //
                        "        \"building\": \"Dallas\",\n" + //
                        "        \"roomNumber\": \"330\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 13,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"United States\",\n" + //
                        "        \"building\": \"Dallas\",\n" + //
                        "        \"roomNumber\": \"340\",\n" + //
                        "        \"type\": \"video\",\n" + //
                        "        \"id\": 14,\n" + //
                        "        \"seatCount\": 32\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"South Africa\",\n" + //
                        "        \"building\": \"Johannesburg\",\n" + //
                        "        \"roomNumber\": \"2-210\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 15,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"South Africa\",\n" + //
                        "        \"building\": \"Johannesburg\",\n" + //
                        "        \"roomNumber\": \"2-212\",\n" + //
                        "        \"type\": \"video\",\n" + //
                        "        \"id\": 16,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Germany\",\n" + //
                        "        \"building\": \"Berlin\",\n" + //
                        "        \"roomNumber\": \"B200\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 17,\n" + //
                        "        \"seatCount\": 12\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Germany\",\n" + //
                        "        \"building\": \"Berlin\",\n" + //
                        "        \"roomNumber\": \"B212\",\n" + //
                        "        \"type\": \"conference\",\n" + //
                        "        \"id\": 18,\n" + //
                        "        \"seatCount\": 8\n" + //
                        "    },\n" + //
                        "    {\n" + //
                        "        \"location\": \"Germany\",\n" + //
                        "        \"building\": \"Berlin\",\n" + //
                        "        \"roomNumber\": \"B234\",\n" + //
                        "        \"type\": \"video\",\n" + //
                        "        \"id\": 19,\n" + //
                        "        \"seatCount\": 8\n" + //
                        "    }")
        .when().get("/room/").then().log().all()
        .assertThat().statusCode(209);
        }
    }
}

