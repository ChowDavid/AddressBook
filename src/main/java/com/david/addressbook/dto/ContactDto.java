package com.david.addressbook.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter @Setter
public class ContactDto {
    private String name;
    private List<String> phoneNumbers;

    public ContactDto(String name, List<String> phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }
}
