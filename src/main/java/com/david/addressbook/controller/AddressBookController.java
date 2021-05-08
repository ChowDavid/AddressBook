package com.david.addressbook.controller;

import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.dto.SimpleContactDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.service.AddressBookService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * User can create an address book record.
     * @param dto the json of (name, phone)
     * @param bookName name of address book
     * @return one address book record saved
     */
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Input Validation Error")})
    @PostMapping("/contacts/{book}")
    public AddressBook saveContact(@Valid @RequestBody ContactDto dto, @PathVariable("book") String bookName){
        return addressBookService.saveContact(bookName,dto);
    }

    /**
     * The address book record can be removed. If record not found it will return 404 status code
     * @param id, address book primary key
     */
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Contact not found") })
    @DeleteMapping(value = "/contacts/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteContactById(@PathVariable("id") Long id){
        addressBookService.deleteContactById(id);
    }

    /**
     * Method to show all the record for an address book
     * @param bookName, the address book name
     * @return a list of address book record belong to one book.
     */
    @ApiResponses(value = {@ApiResponse(code = 404, message = "No record found from address book")})
    @GetMapping("/contactReports/{book}")
    public List<AddressBook> printAllContact(@PathVariable("book") String bookName){
        return addressBookService.printAllContactsByBook(bookName);
    }

    /**
     * The same record (name, phone) can store multi times in same addressbook. or different address book
     * This feature to return the unique record of (name, phone) pair.
     * @return would be (name, phone) array
     */
    @ApiResponses(value = {@ApiResponse(code = 404, message = "No record found across all address books")})
    @GetMapping("/uniqueContacts")
    public Set<SimpleContactDto> printUniqueContact(){
        return addressBookService.printUniqueContact();
    }



}
