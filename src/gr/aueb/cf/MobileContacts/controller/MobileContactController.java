package gr.aueb.cf.MobileContacts.controller;

import gr.aueb.cf.MobileContacts.dao.IMobileContactDAO;
import gr.aueb.cf.MobileContacts.dao.MobileContactDAOImpl;
import gr.aueb.cf.MobileContacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.MobileContacts.dto.MobileContactReadOnlyDTO;
import gr.aueb.cf.MobileContacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.MobileContacts.model.MobileContact;
import gr.aueb.cf.MobileContacts.service.IMobileContactService;
import gr.aueb.cf.MobileContacts.service.MobileContactServiceImpl;
import gr.aueb.cf.MobileContacts.validation.ValidationUtil;

public class MobileContactController {

    private final IMobileContactDAO dao = new MobileContactDAOImpl();
    private final IMobileContactService service = new MobileContactServiceImpl(dao);

    public String insertContact(MobileContactInsertDTO insertDTO) {
        MobileContact mobileContact;
        MobileContactReadOnlyDTO readOnlyDTO;

        try {
          // Validate input data
          String errorVector = ValidationUtil.validateDTO(insertDTO);
          if (!errorVector.isEmpty()) {
              return "Error." + "Validation errorVector\n" + errorVector;
          }

          // If validation is ok, insert contact
          mobileContact = service.insertMobileContact(insertDTO);
          readOnlyDTO = mapMobileContactToDTO(mobileContact);
          return "OK\n" + serializeDTO(readOnlyDTO);
        } catch (PhoneNumberAlreadyExistsException e) {
            return "Error\n" + e.getMessage() + "\n";

        }
    }

    private MobileContactReadOnlyDTO mapMobileContactToDTO(MobileContact mobileContact) {
        return new MobileContactReadOnlyDTO(mobileContact.getId(), mobileContact.getFirstname(), mobileContact.getLastname(), mobileContact.getPhoneNumber());
    }

    private String serializeDTO(MobileContactReadOnlyDTO readOnlyDTO) {
        return "ID: " + readOnlyDTO.getId() + ", Όνομα: " + readOnlyDTO.getFirstname()
                + ", Επώνυμο: " + readOnlyDTO.getLastname() + ", Τηλ. Αριθμός: " + readOnlyDTO.getPhoneNumber();
    }
}
