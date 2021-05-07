package com.david.addressbook.controller;

import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.exception.RecordNotFoundException;
import com.david.addressbook.service.AddressBookService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Input Validation Error")
    })
    @PostMapping("/contacts/{book}")
    public AddressBook saveContact(@Valid @RequestBody ContactDto dto, @PathVariable("book") String bookName){
        return addressBookService.saveContact(bookName,dto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Contact not found") })
    @DeleteMapping(value = "/contacts/{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteContactById(@PathVariable("id") Long id){
        addressBookService.deleteContactById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "No record found from address book")
    })
    @GetMapping("/contactReports/{book}")
    public List<AddressBook> printAllContact(@PathVariable("book") String bookName){
        return addressBookService.printAllContactsByBook(bookName);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "No record found across all address books")
    })
    @GetMapping("/uniqueContacts")
    public Set<ContactDto> printUniqueContact(){
        return addressBookService.printUniqueContact();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationExceptions(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);

        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public Map<String, String> handleRecordNotFoundException(
            RecordNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return errors;
    }
}
