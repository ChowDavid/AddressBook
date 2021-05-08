package com.david.addressbook.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SimpleContactDtoTest {

    @Test
    public void compareTest(){
        SimpleContactDto a = new SimpleContactDto("david", "12345");
        SimpleContactDto b = new SimpleContactDto("david","12345");
        SimpleContactDto c = new SimpleContactDto("David","12345");

        Assertions.assertEquals(a,b);
        Assertions.assertNotEquals(a,c);
        Assertions.assertEquals(a.hashCode(),b.hashCode());
        Assertions.assertNotEquals(a.hashCode(),c.hashCode());

        Assertions.assertEquals("david",a.getName());
        Assertions.assertEquals("12345",a.getPhoneNumber());


    }

}