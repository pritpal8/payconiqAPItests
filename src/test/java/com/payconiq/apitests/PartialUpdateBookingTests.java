package com.payconiq.apitests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class implements all the tests for the UpdateBookings Endpoint.
 * */
public class PartialUpdateBookingTests extends TestBase {

    /**
     * bookingID field stores the test created booking id number.
     * */
    private static int bookingId;

    /**
     * This test verifies that the partialUpdateBooking method successfully
     * updates all the fields in the partial payload. In this test a BookingId
     * is created then Firstname and Last name are updated. The updated changes
     * are verified using GetBooking Method.
     * */
    @Test
    public  void verifyPartialUpdateFirstnameLastname() {
        bookingId = createBookingId();

        // Create a partial update payload and trigger the UpdateBooking Endpoint.
        Response response =
                given().
                        spec(requestSpecification).
                        contentType("application/x-www-form-urlencoded;"
                                + "charset=utf-8").
                        formParam("firstname", "Roger").
                        formParam("lastname", "Federer").
                        pathParams("id", bookingId).
                when().
                        patch("{id}").
                then().
                        extract().response();

        // Perform Assertion on the updated fields
        assertThat(response.getStatusCode(), equalTo(ResponseCodes.SUCCESS));

        // Perform Assertions on the returned response
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.get("firstname"), equalTo("Roger"));
        assertThat(jsonPath.get("lastname"), equalTo("Federer"));

        // Verify that BookingId is successfully updated using getBooking
        given().
                spec(requestSpecification).
                pathParams("id", bookingId).
        when().
                get("{id}").
        then().
                body("firstname", is("Roger")).
                body("lastname", is("Federer"));
    }

    /**
     * This test verifies that the partialUpdateBooking method successfully
     * updates an ALREADY UPDATED BookingId. In this test totalPrice,
     * depositPaid and additionalNeeds are updated for a bookingId which is
     * updated once. The updated changes are verified using GetBooking Method.
     * Note: This test depends on the verifyPartialUpdateFirstnameLastname test
     * */
    @Test
    public void verifyPartialUpdateAlreadyUpdatedBookingID() {
        Response response =
            given().
                    spec(requestSpecification).
                    contentType("application/x-www-form-urlencoded; "
                            + "charset=utf-8").
                    formParam("totalprice", 5000).
                    formParam("depositpaid", true).
                    formParam("additionalneeds", "Sports").
                    pathParams("id", bookingId).
            when().
                    patch("{id}").
            then().
                    extract().response();

        //  verify that partial update is successful
        assertThat(response.getStatusCode(), equalTo(ResponseCodes.SUCCESS));
        JsonPath jsonPath = response.jsonPath();
        assertThat(jsonPath.get("totalprice"), equalTo(5000));
        assertThat(jsonPath.get("depositpaid"), equalTo(true));
        assertThat(jsonPath.get("additionalneeds"), equalTo("Sports"));

        // Verify that the Partial update is successful by calling the getBooking EndPoint.
        given().
                spec(requestSpecification).
                pathParams("id", bookingId).
        when().
                get("{id}").
        then().
                body("depositpaid", is(true)).
                body("totalprice", is(5000)).
                body("additionalneeds", is("Sports"));
    }
}
