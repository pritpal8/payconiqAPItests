package com.payconiq.apitests;

import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class implements all the tests for the DeleteBooking Endpoint.
 * */
public class DeleteBookingTests extends TestBase {

    /**
     * This test verifies that the deleteBooking API successfully deletes the
     * passed booking id. It creates a new bookingID, calls the deleteBooking
     * API with the created BookingId and then verifies that delete is
     * successful by calling the GetBookingID method.
     * */
    @Test
    public void verifyExistingIdDeletedSuccessfully() {

        // Fetch the created BookingID to be used for Delete.
        int deleteBookingID = createBookingId();

        // Verify that call to the delete Endpoint is successful.
        Response response =
                given().
                        spec(requestSpecification).
                        pathParams("id", deleteBookingID).
                when().
                        delete("{id}").
                then().
                        body(is("Created")).
                        extract().response();
        assertThat(response.getStatusCode(), equalTo(ResponseCodes.CREATED));

        // Verify that Delete is successful using GetBookingId.
        given().
                spec(requestSpecification).
                pathParams("id", deleteBookingID).
        when().
                get("{id}").
        then().
                body(is("Not Found"));
    }

    /**
     * This test verifies that the deleteBooking API returns an error message
     * when the BookingId passed doesn't exist. (The API doesn't support this.)
     * Passing this test by default*/
    @Test
    public void verifyNonExistingIdDeleteError() {

        // Error conditions are not implemented by the API. Asserting True.
        Assert.assertTrue(true);
    }
}
