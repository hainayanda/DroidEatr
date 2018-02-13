package nayanda.droid.eatr.model;

import java.util.List;

/**
 * Created by nayanda on 13/02/18.
 */

public class User {

    private Blob profilePicture;
    private String userName;
    private String email;
    private String fullName;
    private List<Phone> phones;
    private List<Address> addresses;
    private Location location;
    private String profileDescription;

    public User(Blob profilePicture, String userName, String email, String fullName, List<Phone> phones, List<Address> addresses, Location location, String profileDescription) {
        this.profilePicture = profilePicture;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.phones = phones;
        this.addresses = addresses;
        this.location = location;
        this.profileDescription = profileDescription;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public Blob getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }
}
