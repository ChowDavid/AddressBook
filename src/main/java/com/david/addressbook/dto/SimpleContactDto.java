package com.david.addressbook.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class SimpleContactDto {
    private String name;
    private String phoneNumber;

    public SimpleContactDto(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleContactDto that = (SimpleContactDto) o;
        return name.equals(that.name) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber);
    }
}
