package gr.aueb.cf.MobileContacts.service;

import gr.aueb.cf.MobileContacts.dao.IMobileContactDAO;
import gr.aueb.cf.MobileContacts.dto.MobileContactInsertDTO;
import gr.aueb.cf.MobileContacts.dto.MobileContactUpdateDTO;
import gr.aueb.cf.MobileContacts.exceptions.ContactNotFoundException;
import gr.aueb.cf.MobileContacts.exceptions.PhoneNumberAlreadyExistsException;
import gr.aueb.cf.MobileContacts.model.MobileContact;

import java.util.List;

public class MobileContactServiceImpl implements IMobileContactService {

    private final IMobileContactDAO dao;

    public MobileContactServiceImpl(IMobileContactDAO dao) {
        this.dao = dao;
    }

    @Override
    public MobileContact insertMobileContact(MobileContactInsertDTO dto) throws PhoneNumberAlreadyExistsException {
        MobileContact mobileContact;

        try {
            if (dao.phoneNumberExists(dto.getPhoneNumber())) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number: " + dto.getPhoneNumber() + " already exists");
            }
            mobileContact = mapInsertDTOToContact(dto);

            System.err.printf("MobileContactServiceImpl Logger: %s was inserted\n", mobileContact);
            return dao.insert(mobileContact);
        } catch (PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s already exists\n", dto.getPhoneNumber());
            throw e;
        }
    }

    @Override
    public MobileContact updateMobileContact(MobileContactUpdateDTO dto) throws PhoneNumberAlreadyExistsException, ContactNotFoundException {
        MobileContact mobileContact;
        MobileContact newContact;
        try {
            if (!dao.userIdExists(dto.getId())) {
                throw new ContactNotFoundException("Contact with id: " + dto.getId() + " not found");
            }

            mobileContact = dao.getById(dto.getId());
            boolean isPhoneNumberOurOwn = mobileContact.getPhoneNumber().equals(dto.getPhoneNumber());
            boolean isPhoneNumberExits = dao.phoneNumberExists(dto.getPhoneNumber());

            if (isPhoneNumberExits && !isPhoneNumberOurOwn) {
                throw new PhoneNumberAlreadyExistsException("Contact with phone number: " + dto.getPhoneNumber()
                        + " already exists and cannot be updated");
            }

            newContact = mapUpdateDTOToContact(dto);
            System.err.printf("MobileContactServiceImpl Logger: %s was updated with new info: %s\n", mobileContact, newContact);
            return dao.update(dto.getId(), newContact);
        } catch (ContactNotFoundException | PhoneNumberAlreadyExistsException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        try {
            if (!dao.userIdExists(id)) {
                throw new ContactNotFoundException("Contact with id: " + id + " not found to delete");
            }

            System.err.printf("MobileContactServiceImpl Logger: contact with id: %d was deleted\n", id);
            dao.deleteById(id);
        } catch (ContactNotFoundException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }

    @Override
    public MobileContact getContactById(Long id) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getById(id);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Contact with id: " + id + " not found");
            }
            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.printf("Contact with id: %d was not found to get returned\n", id);
            throw e;
        }
    }

    @Override
    public List<MobileContact> getAllContacts() {
        return dao.getAll();
    }

    @Override
    public MobileContact getContactByPhone(String phoneNumber) throws ContactNotFoundException {
        MobileContact mobileContact;

        try {
            mobileContact = dao.getByPhoneNumber(phoneNumber);
            if (mobileContact == null) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + " not found");
            }
            return mobileContact;
        } catch (ContactNotFoundException e) {
            System.err.printf("Contact with phone number: %s was not found to get returned\n", phoneNumber);
            throw e;
        }
    }

    @Override
    public void deleteContactByPhoneNumber(String phoneNumber) throws ContactNotFoundException {
        try {
            if (!dao.phoneNumberExists(phoneNumber)) {
                throw new ContactNotFoundException("Contact with phone number: " + phoneNumber + " not found to delete");
            }

            System.err.printf("MobileContactServiceImpl Logger: contact with phone number: %s was deleted\n", phoneNumber);
            dao.deleteByPhoneNumber(phoneNumber);
        } catch (ContactNotFoundException e) {
            System.err.printf("MobileContactServiceImpl Logger: %s\n", e.getMessage());
            throw e;
        }
    }

    private MobileContact mapInsertDTOToContact(MobileContactInsertDTO dto) {
        return new MobileContact(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }

    private MobileContact mapUpdateDTOToContact(MobileContactUpdateDTO dto) {
        return new MobileContact(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber());
    }
}
