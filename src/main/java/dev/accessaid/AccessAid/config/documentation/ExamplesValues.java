package dev.accessaid.AccessAid.config.documentation;

import com.google.maps.model.AddressType;
import com.google.maps.model.OpeningHours;

public class ExamplesValues {

    public static final String LATITUDE = "42.0339357";

    public static final String LONGITUDE = "-87.6721867";

    public static final String ADDRESS = "123 Main St, Evanston, IL 60202, USA";

    public static final String API_PLACE_ID = "ChIJDYp7EELQD4gRDzMUL_0DFlU";

    public static final String COMMENT = "This place has good accessibility for wheelchair users.";

    public static final String COMMENT2 = "This place has good accessibility for wheelchair users.";

    public static final String FIRSTNAME = "John";

    public static final String LASTNAME = "Doe";

    public static final String AVATAR_PATH = "https://api.dicebear.com/6.x/adventurer/svg?seed=Scooter";

    public static final String EMAIL = "johndoe@email.com";

    public static final String USERNAME = "johndoe";

    public static final String PASSWORD = "password123";

    public static final Integer USER_ID = 1;

    public static final Integer PLACE_ID = 1;

    public static final double TOTAL_RATING = 4.5;

    public static final String REGISTER_MESSAGE = "user was registered correctly";

    public static final String STREET_ADDRESS = "123 Main St";

    public static final String CITY = "Evanston";

    public static final String COUNTRY = "Spain";

    public static final String PHONE = "123-456-789";

    public static final String ZIPCODE = "60202";

    public static final String ABOUT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    public static final String PLACE_NAME = "Australian Cruise Group";

    public static final String PLACE_PHONE = "(02) 8296 7351";

    public static final String PLACE_URL = "https://maps.google.com/?cid=3292831917685799941";

    public static final String IS_ACCESSIBLE = "true";

    public static final String WEBSITE = "https://www.australiancruisegroup.com.au/";

    public static final String TRAVEL_AGENCY = "TRAVEL_AGENCY";

    public static final String RESTAURANT = "RESTAURANT";

    public static final String FOOD = "FOOD";

    public static final String POINT_OF_INTEREST = "POINT_OF_INTEREST";

    public static final String ESTABLISHMENT = "ESTABLISHMENT";

    public static final String PLACE_TYPES_STRING = "[\"TRAVEL_AGENCY\", \"RESTAURANT\", \"FOOD\", \"POINT_OF_INTEREST\", \"ESTABLISHMENT\"]";

    public static final AddressType[] PLACE_TYPES = {
            AddressType.TRAVEL_AGENCY,
            AddressType.RESTAURANT,
            AddressType.FOOD,
            AddressType.POINT_OF_INTEREST,
            AddressType.ESTABLISHMENT
    };

    public static final String SENT_DATE = "2023-05-10T10:00:00";

    public static final String SUBJECT = "Test Email subject";

    public static final String MESSAGE = "This is an example message for testing purposes.";

    public static final String OPENING_HOURS_STRING = "{\"open_now\":true,\"weekday_text\":[\"Monday: Closed\",\"Tuesday: 9:00 AM - 5:00 PM\",\"Wednesday: 9:00 AM - 5:00 PM\",\"Thursday: 9:00 AM - 5:00 PM\",\"Friday: 9:00 AM - 5:00 PM\",\"Saturday: 9:00 AM - 5:00 PM\",\"Sunday: 9:00 AM - 5:00 PM\"]}";

    public static final OpeningHours OPENING_HOURS = new OpeningHours();
    static {
        OPENING_HOURS.openNow = true;
        OPENING_HOURS.weekdayText = new String[] {
                "Monday: Closed",
                "Tuesday: 9:00 AM - 5:00 PM",
                "Wednesday: 9:00 AM - 5:00 PM",
                "Thursday: 9:00 AM - 5:00 PM",
                "Friday: 9:00 AM - 5:00 PM",
                "Saturday: 9:00 AM - 5:00 PM",
                "Sunday: 9:00 AM - 5:00 PM"
        };
    }

}
