package com.david.addressbook.service;

import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.dto.SimpleContactDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.exception.RecordNotFoundException;
import com.david.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    public AddressBook saveContact(String addressBookName, ContactDto dto){
        log.info("saveContact to book {}",addressBookName);
        Objects.requireNonNull(addressBookName,"address book name");
        Objects.requireNonNull(dto, "ContactDto should be provided");
        AddressBook book = new AddressBook(dto,addressBookName);
        return addressBookRepository.save(book);
    }

    public void deleteContactById(Long id) {
        log.info("deleteContactById {}",id);
        Objects.requireNonNull(id, "contact id should be provided");
        try {
            addressBookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            log.warn("Contact Record not found by id:{}",id);
            throw new RecordNotFoundException("Contact id:"+id + " Record Not found");
        }
    }

    public List<AddressBook> printAllContactsByBook(String bookName){
        log.info("printAllContactsByBook {}",bookName);
        Objects.requireNonNull(bookName, "address book name not provided");
        List<AddressBook> contacts = addressBookRepository.findByBook(bookName);
        if (contacts==null || contacts.isEmpty()){
            log.warn("No Record found from address book {}",bookName);
            throw new RecordNotFoundException("No Record found from addres book "+bookName);
        }
        return contacts;
    }

    public Set<SimpleContactDto> printUniqueContact(){
        log.info("printUniqueContact");
        Set<SimpleContactDto> dtos = addressBookRepository.findAll().stream().
                flatMap(ab->ab.getPhoneNumbers().stream()
                        .map(phone->new SimpleContactDto(ab.getName(),phone)))
                .collect(Collectors.toSet());
        if (dtos==null || dtos.isEmpty()){
            log.warn("No Record found from Address book");
            throw new RecordNotFoundException("No Record found from Address book");
        }
        return dtos;
    }
}
