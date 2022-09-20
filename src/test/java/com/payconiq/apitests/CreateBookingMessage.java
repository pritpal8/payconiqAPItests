package com.payconiq.apitests;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the createBooking object structure to the Json fields
 * for serializing the outgoing request body
 * */
public class CreateBookingMessage {

    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("totalprice")
    private int totalPrice;
    @JsonProperty("depositpaid")
    private boolean depositPaid;
    @JsonProperty("bookingdates")
    private BookingDates bookingdates;
    @JsonProperty("additionalneeds")
    private String additionalneeds;

    /**
     * getter method for field firstName.
     * @return firstName field
     * */
    public String getFirstname() {
        return firstName;
    }

    /**
     * setter method for field firstName.
     * */
    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    /**
     * getter method for field lastName.
     * @return lastname property
     * */
    public String getLastname() {
        return lastName;
    }

    /**
     * setter method for field lastName.
     * @return firstName field
     * */
    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    /**
     * getter method for field totalPrice.
     * @return totalPrice field
     * */
    public int getTotalprice() {
        return totalPrice;
    }

    /**
     * setter method for field totalPrice.
     * */
    public void setTotalprice(int totalprice) {
        this.totalPrice = totalprice;
    }

    /**
     * getter method for field depositPaid.
     * @return depositPaid field
     * */
    public boolean isDepositpaid() {
        return depositPaid;
    }

    /**
     * setter method for field depositPaid.
     * */
    public void setDepositpaid(boolean depositpaid) {
        this.depositPaid = depositpaid;
    }

    /**
     * getter method for field bookingdates.
     * @return bookingdated field
     * */
    public BookingDates getBookingdates() {
        return bookingdates;
    }

    /**
     * setter method for field bookingdates.
     * */
    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    /**
     * getter method for field additionalneeds.
     * @return additionalNeeds field
     * */
    public String getAdditionalNeeds() {
        return additionalneeds;
    }

    /**
     * setter method for field additionalneeds.
     * @param additionalNeeds property
     * */
    public void setAdditionalNeeds(String additionalNeeds) {
        this.additionalneeds = additionalNeeds;
    }
}

class BookingDates {

    /**
     * getter method for field CheckIn.
     * @return CheckIn field
     * */
    public String getCheckin() {
        return checkIn;
    }

    /**
     * setter method for field CheckIn Date.
     * @param checkin property
     * */
    public void setCheckin(String checkin) {
        this.checkIn = checkin;
    }

    /**
     * getter method for field CheckOut.
     * @return CheckOut field
     * */
    public String getCheckout() {
        return checkOut;
    }

    /**
     * setter method for field CheckOut Date.
     * @param checkout property
     * */
    public void setCheckout(String checkout) {
        this.checkOut = checkout;
    }

    @JsonProperty("checkin")
    private String checkIn;

    @JsonProperty("checkOut")
    private String checkOut;
}
