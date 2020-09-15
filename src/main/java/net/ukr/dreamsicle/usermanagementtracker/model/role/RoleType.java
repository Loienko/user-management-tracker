package net.ukr.dreamsicle.usermanagementtracker.model.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    private static final String ROLE_DOES_NOT_EXIST_PLEASE_CHOOSE_THE_NEXT_ITEMS = "Role does not exist. Please choose the next items: ";
    @Getter
    private final String name;

    public static <T extends Enum<T>> RoleType getEnumFromString(String stringValue) {
        if (stringValue != null) {
            try {
                return Enum.valueOf(RoleType.class, stringValue.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(ROLE_DOES_NOT_EXIST_PLEASE_CHOOSE_THE_NEXT_ITEMS + USER + ", " + ADMIN);
            }
        }
        return null;
    }
}
