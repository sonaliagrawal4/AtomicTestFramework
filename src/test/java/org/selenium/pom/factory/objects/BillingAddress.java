package org.selenium.pom.factory.objects;

public class BillingAddress {
    private String firstname;
    private String lastname;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String country;
    private String state;

//    public BillingAddress(String firstname,String lastname,String addressLine1,String city,String postalCode,String email)
//    {
//        this.firstname=firstname;
//        this.lastname=lastname;
//        this.addressLine1=addressLine1;
//        this.city=city;
//        this.postalCode=postalCode;
//        this.email=email;
//    }
    public String getFirstname() {
        return firstname;
    }

    public BillingAddress setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public BillingAddress setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public BillingAddress setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getCity() {
        return city;
    }

    public BillingAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public BillingAddress setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BillingAddress setEmail(String email) {
        this.email = email;
        return this;
    }

    private String addressLine1;
    private String city;
    private String postalCode;
    private String email;

}
