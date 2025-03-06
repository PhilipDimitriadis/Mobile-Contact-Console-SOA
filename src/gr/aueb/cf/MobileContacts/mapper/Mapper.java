package gr.aueb.cf.MobileContacts.mapper;

import gr.aueb.cf.MobileContacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.MobileContacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.MobileContacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.MobileContacts.model.MobileContact;

public class Mapper {

    /**
     * No instances of this class should be available
     */
    private Mapper() {

    }

    public static MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    public static MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    public static MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(), mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }
}
