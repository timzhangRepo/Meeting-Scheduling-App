import java.util.ArrayList;
import java.util.List;

/**
 * This class is for storing a list of partners, and a list countries.
 * This class contains setter and getter method for partners,
 * getter and addCountry method for countries.
 * The addCountry method adds a Country object to the countries list.
 * @Author Tim Zhang
 */
public class Directory {
    private List<Partner> partners = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();
    public Directory() {
    }

    /**
     *
     * @return a list of partners
     */
    public List<Partner> getPartners() {
        return partners;
    }

    /**
     * set this.partners references to parameter partners.
     * @param partners
     */
    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }

    /**
     * Add a country object to countries
     * @param country
     */
    public void addCountry(Country country){countries.add(country);}
    public List<Country> getCountries() { return countries; }

    @Override
    public String toString() {
        return "partners=" + partners;
    }


}