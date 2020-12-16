import java.util.Date;
import java.util.List;

/**
 * The Partner class for storing partner information.
 * The class stores firstName, lastName, email, country, and availableDates.
 * Has getters and setters methods for each variable.
 */
public class Partner {
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private List<Date> availableDates;

    /**
     * firstName variable setter.
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * lastName variable setter
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * email variable setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * country variable setter
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * availableDates variable setter
     * @param availableDates
     */
    public void setAvailableDates(List<Date> availableDates) {
        this.availableDates = availableDates;
    }

    /**
     * firstname variable getter
     * @return
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * lastName variable getter
     * @return
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * email variable getter
     * @return
     */
    public String getEmail() {
        return email;
    }
    /**
     * country variable getter
     * @return
     */
    public String getCountry() {
        return country;
    }
    /**
     * availableDates variable getter
     * @return
     */
    public List<Date> getAvailableDates() {
        return availableDates;
    }


}