package com.david.addressbook.service;

import com.david.addressbook.dto.AddressBookDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.exception.RecordNotFoundException;
import com.david.addressbook.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    public AddressBook saveBook(String addressBookName,AddressBookDto dto){
        log.info("saveAddressBook start");
        AddressBook book = new AddressBook(dto,addressBookName);
        return addressBookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        log.info("deleteBookById {}",id);
        try {
            addressBookRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            log.warn("Address Book Record not found id:{}",id);
            throw new RecordNotFoundException("Address Book id:"+id + " Record Not found");
        }
    }

    public List<AddressBook> printAllAddressContactsByBook(String bookName ){
        log.info("printAllAddressContacts start...");
        return addressBookRepository.findByBook(bookName);
    }

    public Set<AddressBookDto> printUniqueAddress(){
        return addressBookRepository.findUnique();
    }
}
