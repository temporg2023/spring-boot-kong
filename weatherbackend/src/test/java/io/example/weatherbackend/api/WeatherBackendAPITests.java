package io.example.weatherbackend.api;

import io.example.weatherbackend.WeatherBackendApplication;
import io.example.weatherbackend.model.GeneralOutlook;
import io.example.weatherbackend.model.MethodOfPayment;
import io.example.weatherbackend.model.Product;
import io.example.weatherbackend.model.User;
import io.example.weatherbackend.model.Weather;
import io.example.weatherbackend.businesslogic.IncredibleLogic;
import io.example.weatherbackend.model.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = WeatherBackendApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class WeatherBackendAPITests {

    @LocalServerPort
    int port;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

	@Test
    public void should_create_weather() {
	    
	    Weather weather = new Weather();
	    weather.setFlagColor("blue");
	    weather.setPostalCode("99425");
	    weather.setProduct(Product.ForecastBasic);
        weather.addUser(new User(27, 4300, MethodOfPayment.Bitcoin));
        weather.addUser(new User(45, 500300, MethodOfPayment.Paypal));
        weather.addUser(new User(67, 60000300, MethodOfPayment.Paypal));

	    given() // can be ommited when GET only
	        .contentType(ContentType.JSON)
            .body(weather)
        .when() // can be ommited when GET only
            .post("/weather/general/outlook")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .assertThat()
                .equals(IncredibleLogic.generateGeneralOutlook());
	    
	    GeneralOutlook outlook = given() // can be ommited when GET only
	            .contentType(ContentType.JSON)
	            .body(weather).post("/weather/general/outlook").as(GeneralOutlook.class);
	    
	    assertEquals("Weimar", outlook.getCity());
    }

    @Test
    public void should_get_outlook_info() {
        get("/weather/general/outlook")
            .then().statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .assertThat().equals("Try a POST also against this URL! Just send some body with it like: '{\n" +
                "    \"postalCode\": \"99425\",\n" +
                "    \"flagColor\": \"blue\",\n" +
                "    \"product\": \"ForecastBasic\",\n" +
                "    \"users\": [\n" +
                "        {\n" +
                "            \"age\": 55,\n" +
                "            \"contribution\": 5634500,\n" +
                "            \"methodOfPayment\": \"Bitcoin\"\n" +
                "        }\n" +
                "    ]\n" +
                "}'");
    }

    @Test
    public void should_get_named_response() {
        get("/weather/norbert")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.TEXT)
            .assertThat()
                .equals("Hello norbert! This is a RESTful HttpService written in Spring. Try to use some other HTTP verbs (don´t say 'methods' :P ) :)");
    }

}
