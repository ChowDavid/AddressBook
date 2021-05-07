package com.david.addressbook.entity;

import com.david.addressbook.dto.ContactDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 10, message = "Address book name Cannot longer than 10 characters")
    private String book;
    @NotEmpty(message = "Please provide the name")
    @Size(max = 50, message = "Name - Max Length cannot longer than 50 characters")
    private String name;
    @NotEmpty(message = "Please provide the phone number")
    @Size(max = 50, message = "Phone number - Max Length cannot longer than 50 characters")
    private String phoneNumber;

    public AddressBook(ContactDto dto, String addressBookName){
        this.book = addressBookName;
        this.name = dto.getName();
        this.phoneNumber = dto.getPhoneNumber();
    }

}
