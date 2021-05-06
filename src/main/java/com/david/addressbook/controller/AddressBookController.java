package com.david.addressbook.controller;

import com.david.addressbook.dto.AddressBookDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.exception.RecordNotFoundException;
import com.david.addressbook.service.AddressBookService;
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
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/save/{book}")
    public AddressBook saveAddressBook(@Valid @RequestBody AddressBookDto dto, @PathVariable("book") String bookName){
        return addressBookService.saveBook(bookName,dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAddressBooKById(@PathVariable("id") Long id){
        addressBookService.deleteBookById(id);
    }

    @GetMapping("/printAllContact/{book}")
    public List<AddressBook> printAllAddressContact(@PathVariable("book") String bookName){
        return addressBookService.printAllAddressContactsByBook(bookName);
    }

    @GetMapping("/printUniqueAddressContact/")
    public Set<AddressBookDto> printUniqueAddressContact(){
        return addressBookService.printUniqueAddress();
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RecordNotFoundException.class)
    public Map<String, String> handleRecordNotFoundException(
            RecordNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", ex.getMessage());
        return errors;
    }
}
