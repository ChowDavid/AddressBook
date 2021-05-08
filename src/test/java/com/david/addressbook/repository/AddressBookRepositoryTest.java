package com.david.addressbook.repository;

import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.entity.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

@Slf4j
@DataJpaTest
class AddressBookRepositoryTest {

    @Autowired
    private AddressBookRepository addressBookRepository;


    @Test
    @Sql({"test_clear_data.sql"})
    public void saveAddressBook(){
        AddressBook book = new AddressBook();
        book.setBook("Book");
        book.setName("name");
        book.setPhoneNumber("phone");
        AddressBook storebook = addressBookRepository.save(book);
        Assertions.assertNotNull(storebook.getId());
        Assertions.assertEquals("Book",storebook.getBook());
        Assertions.assertEquals("name",storebook.getName());
        Assertions.assertEquals("phone",storebook.getPhoneNumber());
    }

    @Test
    @Sql({"test_clear_data.sql","test_data.sql"})
    public void findByBook(){
        List<AddressBook> contacts = addressBookRepository.findByBook("NORMAL");
        Assertions.assertEquals(4,contacts.size());
    }

    @Test
    @Sql({"test_clear_data.sql","test_data.sql"})
    public void findUnique(){
        Set<ContactDto> contacts = addressBookRepository.findUnique();
        Assertions.assertEquals(4,contacts.size());
    }

    @Test
    @Sql({"test_clear_data.sql","test_data.sql"})
    public void deleteById(){
        Assertions.assertTrue(addressBookRepository.findById(1L).isPresent());
        addressBookRepository.deleteById(1L);
        Assertions.assertFalse(addressBookRepository.findById(1L).isPresent());
    }

    @Test
    public void deleteById_Empty(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()-> addressBookRepository.deleteById(1L));
    }






}