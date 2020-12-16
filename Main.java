import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * HubSpot Online Assessment
 * Candidate name: Tim Zhang
 * Candidate email: tzhang03@usc.edu
 *
 * This application reads, process and sends JSON file from and to specified URLs.
 * The application will reads JSON file from a given URL,
 * and finds two consecutive days for each Country that ensures maximum amount of candidates
 * can attend the conference/meeting.
 * The output are in JSON format, includes all variables and data types in the Country class.
 * Both HTTP_GET URL and HTTP_POST url can be found in the HTTPClientGetAndPost.class.
 *
 *
 * @Author Tim Zhang
 */
public class Main {
    final static private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//date format standard.
    final static private int MILLIS_IN_DAY = 1000 * 60 * 60 * 24; //86,400,000 milliseconds in a day
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();//JSON file reader/writer
        HttpClientGetAndPost client = new HttpClientGetAndPost(); //A HttpClientGetAndPost class object
        mapper.setDateFormat(dateFormat);
        //ignore UNKOWN_properties when deserializes json file,to avoid interruption.
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
           Directory directory = mapper.readValue(client.Get(),Directory.class);
           getResults(directory);
           directory.setPartners(null); //no longer needs Partners information, since requires JSON array of countries.
           mapper.configure(SerializationFeature.INDENT_OUTPUT, true); //for better look JSON
           mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
           mapper.writeValue(new File("newCounrty.json"),directory);
           client.Post(mapper.writeValueAsString(directory));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * This is the core method with void return type.
     * It performs reading and modification of the Directory.
     * The method will ultimately produce result country.list and add to the directory.
     * @param directory
     */
    private static void getResults(Directory directory){
        //get the list of partners
        List<Partner> partners = directory.getPartners();
        //Sort partners by its country and store in HashMap
        HashMap<Country, List<Partner>> mapOfPartnersByCountry = countPartnerByCountry(partners);

        //For each Entry Set in the map
        for(Map.Entry<Country, List<Partner>> entry: mapOfPartnersByCountry.entrySet()){
            //Count the number of partners available to attend for all date possible from one country.
            HashMap<Date, Integer> mapOfCountByDate = countDate(entry);
            //Get the bestDate.
            Date bestDate = getBestDate(mapOfCountByDate);
            //update country information accordingly
            updateCountryInformation(entry,bestDate);
            //Add this country to Directory
            directory.addCountry(entry.getKey());
        }
    }
    /**
     * Sort and store partners by its country.
     * All partners from the same country will be stored in the same map-value list.
     * @param partners List of partners.
     * @return
     */
    private static HashMap<Country, List<Partner>> countPartnerByCountry(List<Partner> partners){
        HashMap<Country, List<Partner>> res = new HashMap<>();
        for(Partner partner: partners){
            Country country = new Country(partner.getCountry());
            if(res.containsKey(country)){
                res.get(country).add(partner);
            }else{
                res.put(country, new ArrayList<>(Arrays.asList(partner)));
            }
        }
        return res;
    }

    /**
     * Count the number of partners available to attend for all date possible from one country.
     * @param entry
     * @return EntryList of Country (key) - Partners
     */
    private static HashMap<Date, Integer> countDate (Map.Entry<Country, List<Partner>> entry){
        HashMap<Date, Integer> res = new HashMap<>();
        for(Partner p: entry.getValue()){
            for(Date date: p.getAvailableDates()){
                res.put(date, res.getOrDefault(date, 0)+1);
            }
        }
        return res;
    }
    /**
     * Get the earliest start date of two consecutive dates, while ensuring the maximum number of partners can attend
     * @param map key is date, value is total number of partners can attend on that date.
     * @return the start date
     */
    private static Date getBestDate(HashMap<Date, Integer> map){
        Date bestDate = null;
        int max_count = 0;
        /*
        Iterate the entry set of map, get the total count (#partners) of the current date and
        the next date of current date, or in another word: total = currentDate_count+nextDay_Count;
        update the bestDate if found total > max_count
        update bestDate if total==max_count and currentDate is early than bestDate
         */
        for(Map.Entry<Date, Integer> entry: map.entrySet()){
            Date currentDate = entry.getKey();
            int currentDate_count = entry.getValue();
            if(bestDate==null) {
                bestDate = currentDate;
                max_count = currentDate_count;
            }
            Date nextDay = getNextDay(currentDate); //the next day of currentDate
            int nextDay_Count = map.getOrDefault(nextDay, 0);
            int total = currentDate_count+nextDay_Count; //Current date count plus next day count.

            if(total<max_count) continue;
            else if(total==max_count){
                bestDate = getEarlyDate(currentDate,bestDate);
            }else{
                max_count = total;
                bestDate = currentDate;
            }
        }
        return bestDate;
    }
    /**
     * Sub-method #1 for getBestDate
     * Method to get the next day
     * @param date
     * @return
     */
    private static Date getNextDay(Date date){
        Date nextDay = new Date(date.getTime() + MILLIS_IN_DAY);
        return nextDay;
    }
    /**
     * Sub-method #2 for getBestDate
     * Method to get the early date of two dates, return date2 if two dates are the same.
     * @param date1
     * @param date2
     * @return
     */
    private static Date getEarlyDate(Date date1, Date date2) {
        if (date1.compareTo(date2) < 0) {
            return date1;
        } else {
            return date2;
        }
    }
    /**
     * This method updates attendeeCount, attendees and best_date variable for each country.
     * @param entry An entry set of List<Partner> for a country.
     * @param bestDate the bestDate for country in the entrySet.
     */
    private static void updateCountryInformation(Map.Entry<Country, List<Partner>> entry, Date bestDate){
        Country country = entry.getKey();
        int attendeeCount = 0; //default attendeeCount to 0.
        List<String> attendees = new ArrayList<>();
        for(Partner partner: entry.getValue()){
            if(partner.getAvailableDates().contains(bestDate)){
                attendeeCount++;
                attendees.add(partner.getEmail());
            }
        }
        country.setStartDate(dateFormat.format(bestDate)); //update bestDate to the country.
        country.setAttendeeCount(attendeeCount); //update attendeeCount.
        country.setAttendees(attendees);//update attendees
    }
}
