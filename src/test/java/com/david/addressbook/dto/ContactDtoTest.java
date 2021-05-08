package com.david.addressbook.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;



class ContactDtoTest {

    @Test
    public void getterSetter(){
        List<String> phoneNumbers= new ArrayList<>();
        phoneNumbers.add("123456");
        phoneNumbers.add("987554");
        ContactDto dto = new ContactDto("name",phoneNumbers);
        Assertions.assertEquals(phoneNumbers,dto.getPhoneNumbers());
        Assertions.assertEquals("name",dto.getName());
        dto.setName("ABCD");
        Assertions.assertEquals("ABCD",dto.getName());
        dto.setPhoneNumbers(phoneNumbers);
        Assertions.assertEquals(phoneNumbers,dto.getPhoneNumbers());
    }

}