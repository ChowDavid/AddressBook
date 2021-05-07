package com.david.addressbook.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactDtoTest {

    @Test
    public void compareTest(){
        ContactDto a = new ContactDto("david","12345");
        ContactDto b = new ContactDto("david","12345");
        ContactDto c = new ContactDto("David","12345");

        Assertions.assertEquals(a,b);
        Assertions.assertNotEquals(a,c);
        Assertions.assertEquals(a.hashCode(),b.hashCode());
        Assertions.assertNotEquals(a.hashCode(),c.hashCode());
    }

}