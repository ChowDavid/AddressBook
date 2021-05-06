package com.david.addressbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.Objects;

@Getter @Setter @AllArgsConstructor
public class AddressBookDto {
    private String name;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookDto that = (AddressBookDto) o;
        return name.equals(that.name) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }
}
