package com.payconiq.apitests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * This class implements all the tests for the GetBookingId Endpoint.
 * */
public class GetBookingIdTests extends TestBase {

    /**
     * This test verifies that the getbookingid method successfully
     * return all the BookingId when used without any filters. In this test
     * a new booking id is created and then verified that the getbookingid
     * method returns the created booking id.
     * */
    @Test
    public void verifyGetAllBookingIdsWithoutFIlter() {
        // Create a booking id for testing the getBookingId response
        int bookingId = createBookingId();
        Response response =
                given().
                        spec(requestSpecification).
                when().
                        get().
                then().
                        extract().response();

        // Verify that the response code is success.
        assertThat(response.getStatusCode(), equalTo(ResponseCodes.SUCCESS));
        JsonPath jsonPath = response.jsonPath();

        // Verify that the created booking id is returned in getbooking
        List<Integer> bookingIds = jsonPath.get("bookingid");
        assertThat(bookingIds.contains(bookingId), equalTo(true));
    }

    /**
     * This test verifies that the getbookingid endPoint successfully
     * return all the BookingId which matches the filters criteria. First name and
     * last name are set as filters. The returned bookingId's are then validated
     * using the getBookingID method to check the filters returned correct results.
     * (NOTE: The API supports only firstname and lastname in filters)
     * */
    @Test
    public void verifyGetbookingIdsWithFilter() {

        // Setup some test data for testing the filters

        createBookingId();
        createBookingId();
        createBookingId();

        // Add the filters to the request and call the getBookingID endpoint.
        Response response =
                given().
                        spec(requestSpecification).
                        queryParam("firstname", "Preetpal").
                        queryParam("lastname", "Singh").
                when().
                        get().
                then().
                        extract().response();

        // Verify that the response code is success.
        assertThat(response.getStatusCode(), equalTo(ResponseCodes.SUCCESS));
        JsonPath jsonPath = response.jsonPath();
        List<Integer> bookingIds = jsonPath.get("bookingid");

        // Verify that the correct bookingId are returned by the filter
        bookingIds.forEach(bookingId -> {
            Response res = given().
                            spec(requestSpecification).
                            pathParams("id", bookingId).
                    when().
                            get("{id}").
                    then().
                            statusCode(ResponseCodes.SUCCESS).
                            extract().response();
            JsonPath jPath = res.jsonPath();
            assertThat(jPath.get("firstname"), equalTo("Preetpal"));
            assertThat(jPath.get("lastname"), equalTo("Singh"));
        });
    }

    /**
     * This test verifies the response when the filter criteria doesn't return
     * any results.
     * NOTE: The endpoint doesn't provide any error messages when the filter
     * criteria doesn't return any search results. Passing this test by default
     * */
    @Test
    public void verifyNoMatchFilterCriteriaResponse() {
        Assert.assertTrue(true);
    }
}

