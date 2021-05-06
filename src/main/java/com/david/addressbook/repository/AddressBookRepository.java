package com.david.addressbook.repository;

import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AddressBookRepository  extends JpaRepository<AddressBook,Long> {

    List<AddressBook> findByBook(String bookName);

    @Query(value ="select distinct new com.david.addressbook.dto.ContactDto(a.name,a.phoneNumber) from AddressBook a")
    Set<ContactDto> findUnique();
}
