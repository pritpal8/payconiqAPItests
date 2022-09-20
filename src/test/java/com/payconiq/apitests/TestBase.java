package com.payconiq.apitests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class serves as a test base for all the tests. It does the
 * initialization of the request specification and provides the
 * createbooking Id method which is used in most tests.
 * */
public class TestBase {
    protected static RequestSpecification requestSpecification;

    /**
     * This method setups up the request specification common for all the tests.
     * */
    @BeforeClass
    public static void setupRequestSpecification() throws URISyntaxException {
        String token = getAuthorizationToken();
        URI uri = new URI(Utilities.getProperty("base_url"));
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(uri);
        requestSpecBuilder.setBasePath(Utilities.getProperty("base_path"));
        requestSpecBuilder.addHeader("Cookie", "token=" + token);
        requestSpecBuilder.addHeader("Content-Type",
                Utilities.getProperty("content_type"));
        requestSpecification = requestSpecBuilder.build();
    }

    /**
     * getter method for fetching the request specification.
     * */
    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    /**
     * This method returns the string token for authorization.
     * @return String authorization token from the api
     *
     * */
    private static String getAuthorizationToken() {

        Response response = RestAssured
                .given()
                        .baseUri(Utilities.getProperty("base_url"))
                        .contentType("application/x-www-form-urlencoded; charset=utf-8")
                        .formParam("username", Utilities.getProperty("username"))
                        .formParam("password", Utilities.getProperty("password"))
                .when()
                        .post(Utilities.getProperty("authentication"))
                .then()
                        // Verify that the Token api returned success.
                        .statusCode(ResponseCodes.SUCCESS)
                        .extract().response();
        return response.path("token");
    }

    /**
     * This method is used for creating new Booking Id's for test data setup.
     * @return Booking id of the Created bookings
     * */
    protected int createBookingId() {
        CreateBookingMessage createBookingMessage = new CreateBookingMessage();
        createBookingMessage.setFirstname("Preetpal");
        createBookingMessage.setLastname("Singh");
        createBookingMessage.setTotalprice(2500);
        createBookingMessage.setDepositpaid(true);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").
                format(Calendar.getInstance().getTime());
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin(currentDate);
        bookingDates.setCheckout(currentDate);
        createBookingMessage.setAdditionalNeeds("Swimming");
        createBookingMessage.setBookingdates(bookingDates);

        Response response = given().
                spec(requestSpecification).
                config(RestAssured.config().encoderConfig(encoderConfig().
                        encodeContentTypeAs("ContentType.JSON",
                                ContentType.JSON))).
                body(createBookingMessage).
                when().
                    post().
                then().
                    extract().response();
        assertThat(response.getStatusCode(), equalTo(ResponseCodes.SUCCESS));
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.get("bookingid");
    }
}
