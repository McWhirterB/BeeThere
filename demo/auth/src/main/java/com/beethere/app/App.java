package com.beethere.app;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.net.URI;
import java.util.Map;
import java.util.HashMap;
/**
 * Hello world!
 */
public class App {

    public void get(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .build();

        HttpResponse<String> response =
            client.send(request, BodyHandlers.ofString());

        System.out.println(response.body());
    }



    public static void post(String uri, String data) throws Exception {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(data))
                .build();

        HttpResponse<?> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }


    public static void main(String[] args){
        // Map<String,String> token = new HashMap();
        // token.put("token", "eyJhbGciOiJIUzI1NiJ9.eyJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjEyNDcwNzksImV4cCI6MTc2MTI1MDY3OX0.NAQ3lvSOZxHDRaczWB6KBDRZsTjai049K0jf5OUwcas");
        String token = "{\n" + //
                        "    \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJsYXN0X25hbWUiOiJEdWNrd29ydGgiLCJsb2NhdGlvbiI6IkJyYXppbCIsImlkIjoyLCJkZXBhcnRtZW50IjoiSW5mb3JtYXRpb24gVGVjaG5vbG9neSIsInRpdGxlIjoiRGV2ZWxvcGVyIiwiZmlyc3RfbmFtZSI6Iktlbm5hbiIsInN1YiI6Iktlbm5hbiBEdWNrd29ydGgiLCJpYXQiOjE3NjEyNDcwNzksImV4cCI6MTc2MTI1MDY3OX0.NAQ3lvSOZxHDRaczWB6KBDRZsTjai049K0jf5OUwcas\"\n" + //
                        "}";

        try{
        post("http://172.16.0.51:8080/auth_service/api/auth/verify", token);
        }
        catch(Exception e){
            return;
        }
        
    }
}
