package net.ukr.dreamsicle.usermanagementtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static net.ukr.dreamsicle.usermanagementtracker.dto.Constants.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

    private String id;

    @NotBlank(message = FILL_THE_USERNAME)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_NAME)
    private String name;

    @NotBlank(message = FILL_THE_USERNAME)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_USERNAME)
    private String username;

    @NotBlank(message = FILL_THE_EMAIL)
    @Email(message = INPUT_VALID_DATA_FOR_EMAIL)
    private String email;

    @NotBlank(message = FILL_THE_CITY)
    @Pattern(regexp = CITY_REGEX, message = INPUT_VALID_DATA_FOR_CITY)
    private String city;

    @NotBlank(message = INPUT_VALID_DATA_FOR_PHONE)
    @Pattern(regexp = PHONE_REGEX, message = INPUT_VALID_DATA_FOR_PHONE)
    private String phone;

    private Set<String> roles;
}
