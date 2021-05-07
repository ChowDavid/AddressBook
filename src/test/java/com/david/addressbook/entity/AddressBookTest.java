package com.david.addressbook.entity;

import com.david.addressbook.dto.ContactDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    private Validator validator;

    @BeforeEach
    public void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void fieldValidationTest(){
        AddressBook book = new AddressBook();
        Set<ConstraintViolation<AddressBook>> validations = validator.validate(book);
        Assertions.assertEquals(2,validations.size());
        assertEqual(validations,"name","Please provide the name");
        assertEqual(validations,"phoneNumber","Please provide the phone number");

        book.setBook("1234567890");
        validations = validator.validate(book);
        Assertions.assertEquals(2,validations.size());
        assertEqual(validations,"name","Please provide the name");
        assertEqual(validations,"phoneNumber","Please provide the phone number");

        book.setBook("1234567890A");
        validations = validator.validate(book);
        Assertions.assertEquals(3,validations.size());
        assertEqual(validations,"name","Please provide the name");
        assertEqual(validations,"phoneNumber","Please provide the phone number");
        assertEqual(validations,"book","Address book name Cannot longer than 10 characters");

        book.setBook("1234567890");
        book.setName("");
        book.setPhoneNumber("");
        validations = validator.validate(book);
        Assertions.assertEquals(2,validations.size());
        assertEqual(validations,"name","Please provide the name");
        assertEqual(validations,"phoneNumber","Please provide the phone number");

        book.setBook("1234567890");
        book.setName("David");
        book.setPhoneNumber("123");
        validations = validator.validate(book);
        Assertions.assertEquals(0,validations.size());

        book.setName(new String(new char[50]));
        book.setPhoneNumber(new String(new char[50]));
        validations = validator.validate(book);
        Assertions.assertEquals(0,validations.size());

        book.setName(new String(new char[51]));
        book.setPhoneNumber(new String(new char[51]));
        validations = validator.validate(book);
        Assertions.assertEquals(2,validations.size());
        assertEqual(validations,"name","Name - Max Length cannot longer than 50 characters");
        assertEqual(validations,"phoneNumber","Phone number - Max Length cannot longer than 50 characters");
    }

    @Test
    public void constructorTest(){
        ContactDto dto = new ContactDto("david","123456");
        AddressBook book = new AddressBook(dto,"book");
        Assertions.assertEquals(null,book.getId());
        Assertions.assertEquals("david",book.getName());
        Assertions.assertEquals("123456",book.getPhoneNumber());
        Assertions.assertEquals("book",book.getBook());
    }


    private void assertEqual(Set<ConstraintViolation<AddressBook>> validations, String key, String expectedValue){
        Assertions.assertEquals(expectedValue,validations.stream().filter(v->v.getPropertyPath().toString().equals(key)).map(v->v.getMessageTemplate()).findFirst().get());
    }


}