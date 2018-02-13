package nayanda.droid.eatr.model;

/**
 * Created by nayanda on 13/02/18.
 */

public class Address {
    private String street;
    private String city;
    private String country;
    private String zipCode;
    private boolean primary;

    public Address(String street, String city, String country, String zipCode, boolean primary) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.primary = primary;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
