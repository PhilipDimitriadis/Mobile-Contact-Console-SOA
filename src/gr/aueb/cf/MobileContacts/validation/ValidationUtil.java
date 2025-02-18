package gr.aueb.cf.MobileContacts.validation;

import gr.aueb.cf.MobileContacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.MobileContacts.dto.MobileContactUpdateDTO;

public class ValidationUtil {

    /**
     * No instances of this class should be available
     */

    private ValidationUtil() {

    }

    public static String validateDTO(MobileContactInsertDTO insertDTO) {
        String errorResponse = "";

        if (insertDTO.getPhoneNumber().length() <= 5)
            errorResponse += "Phone number must have more than 5 symbols\n";
        if (insertDTO.getFirstname().length() < 2)
            errorResponse += "First name must have 2 or more characters\n";
        if (insertDTO.getLastname().length() < 2)
            errorResponse += "Last name must have 2 or more characters\n";

        return errorResponse;
    }

    public static String validateDTO(MobileContactUpdateDTO updateDTO) {
        String errorResponse = "";

        if (updateDTO.getPhoneNumber().length() <= 5)
            errorResponse += "Phone number must have more than 5 symbols\n";
        if (updateDTO.getFirstname().length() < 2)
            errorResponse += "First name must have 2 or more characters\n";
        if (updateDTO.getLastname().length() < 2)
            errorResponse += "Last name must have 2 or more characters\n";

        return errorResponse;
    }
}
