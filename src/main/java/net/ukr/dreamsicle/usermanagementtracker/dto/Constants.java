package net.ukr.dreamsicle.usermanagementtracker.dto;

public interface Constants {
    String INPUT_STRING_VALUE_REGEX = "^[a-zA-Z]+(([',.\\-][a-zA-Z ])?[a-zA-Z]*)*$";
    String PHONE_REGEX = "^\\+?([0-9]{2})?\\(?[0-9]{3}\\)?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$";
    String CITY_REGEX = "([a-zA-Z]+|[a-zA-Z]+\\\\s[a-zA-Z]+)";

    String SUCCESSFULLY_COMPLETED = "Successfully completed!";



    String INPUT_VALID_DATA_FOR_PHONE = "Please input valid data for phone";
    String INPUT_VALID_DATA_FOR_CITY = "Please input valid data for city";

    String FILL_THE_USERNAME = "Please fill the username";
    String FILL_THE_EMAIL = "Please fill the email";
    String FILL_THE_CITY = "Please fill the city";

    String INPUT_VALID_DATA_FOR_NAME = "Please input valid data for name";
    String INPUT_VALID_DATA_FOR_USERNAME = "Please input valid data for username";
    String INPUT_VALID_DATA_FOR_EMAIL = "Please input valid data for email";
}
