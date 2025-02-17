package gr.aueb.cf.MobileContacts.service;

import gr.aueb.cf.MobileContacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.MobileContacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.MobileContacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.MobileContacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.MobileContacts.model.MobileContact;

public interface IMobileContactService {

    MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException;
    MobileContact updateMobileContact(MobileContactUpdateDTO dto)
        throws PhoneNumberAlreadyExistsException, ContactNotFoundException;
}
