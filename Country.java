import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Country class for storing country information
 * @Author Tim Zhang
 */
public class Country {

    private int attendeeCount;
    public List<String> attendees = new ArrayList<>();
    public String name;
    private String startDate;

    /**
     * Setter
     * @param name
     */
    public Country(String name) {
        this.name = name;
    }
    /**
     * Setter
     * @param attendeeCount
     */
    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }
    /**
     * Setter
     * @param attendees
     */
    public void setAttendees(List<String>  attendees) {
        this.attendees = attendees;
    }

    /**
     * Setter
     * @param startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter
     * @return name variable
     */
    public String getName() {
        return name;
    }
    /**
     * Getter
     * @return attendeeCount variable
     */
    public int getAttendeeCount() {
        return attendeeCount;
    }
    /**
     * Getter
     * @return attendees variable
     */
    public List<String>  getAttendees() {
        return attendees;
    }
    /**
     * Getter
     * @return startDate variable
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * When comparing, compares two country name.
     * if two string values are equal, then returns equal.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return name.equals(country.name);
    }

    /**
     * Hashcode returns name variable hash.
     * @return
     */

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", attendeeCount=" + attendeeCount +
                ", attendees=" + attendees +
                ", best_date='" + startDate + '\'' +
                '}';
    }
}
