package com.david.addressbook.controller;


import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.exception.RecordNotFoundException;
import com.david.addressbook.service.AddressBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;


import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.*;

import java.util.Arrays;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressBookController.class)
public class AddressBookControllerTest {

    @MockBean
    private AddressBookService addressBookService;
    @Autowired
    private MockMvc mvc;

    @Captor
    ArgumentCaptor<String> bookCaptor;
    @Captor
    ArgumentCaptor<Long> idCaptor;

    @Test
    public void saveContact_happy() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(1L);
        addressBook.setBook("NORMAL");
        addressBook.setName("David");
        addressBook.setPhoneNumber("123456");
        when(addressBookService.saveContact(anyString(),any(ContactDto.class))).thenReturn(addressBook);
        mvc.perform(MockMvcRequestBuilders.post("/addressBook/contacts/BUSINESS")
                .content("{\n" +
                        "  \"name\": \"david\",\n" +
                        "  \"phoneNumber\": \"123456\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"book\": \"NORMAL\",\n" +
                        "  \"name\": \"David\",\n" +
                        "  \"phoneNumber\": \"123456\"\n" +
                        "}"));
        verify(addressBookService,times(1)).saveContact(bookCaptor.capture(),any(ContactDto.class));
        Assertions.assertEquals("BUSINESS",bookCaptor.getValue());
    }

    @Test
    public void saveContact_inputError() throws Exception {
        ConstraintViolationException ex = generateException();
        when(addressBookService.saveContact(anyString(),any(ContactDto.class))).thenThrow(ex);
        mvc.perform(MockMvcRequestBuilders.post("/addressBook/contacts/BUSINESS")
                .content("{\n" +
                        "  \"name\": \"david\",\n" +
                        "  \"phoneNumber\": \"123456\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"phoneNumber\":\"Please provide the phone number\",\"name\":\"Please provide the name\"}"));
        verify(addressBookService,times(1)).saveContact(bookCaptor.capture(),any(ContactDto.class));
        Assertions.assertEquals("BUSINESS",bookCaptor.getValue());
    }
    @Test
    public void saveContact_500_Error() throws Exception {
        RuntimeException ex =  new RuntimeException("Unknown SQL");
        when(addressBookService.saveContact(anyString(),any(ContactDto.class))).thenThrow(ex);
        mvc.perform(MockMvcRequestBuilders.post("/addressBook/contacts/BUSINESS")
                .content("{\n" +
                        "  \"name\": \"david\",\n" +
                        "  \"phoneNumber\": \"123456\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{\"Error\":\"Unknown SQL\"}"));
        verify(addressBookService,times(1)).saveContact(bookCaptor.capture(),any(ContactDto.class));
        Assertions.assertEquals("BUSINESS",bookCaptor.getValue());
    }

    @Test
    public void deleteContactById_happy() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/addressBook/contacts/1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
        verify(addressBookService,times(1)).deleteContactById(idCaptor.capture());
        Assertions.assertEquals(1,idCaptor.getValue());
    }

    @Test
    public void deleteContactById_noRecordFound() throws Exception {
        doThrow(new RecordNotFoundException("Record not found id 1")).when(addressBookService).deleteContactById(anyLong());
        mvc.perform(MockMvcRequestBuilders.delete("/addressBook/contacts/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("{\"Error\":\"Record not found id 1\"}"));
        verify(addressBookService,times(1)).deleteContactById(idCaptor.capture());
        Assertions.assertEquals(1,idCaptor.getValue());
    }

    @Test
    public void deleteContactById_exception() throws Exception {
        doThrow(new RuntimeException("SQL Error")).when(addressBookService).deleteContactById(anyLong());
        mvc.perform(MockMvcRequestBuilders.delete("/addressBook/contacts/1"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("{\"Error\":\"SQL Error\"}"));
        verify(addressBookService,times(1)).deleteContactById(idCaptor.capture());
        Assertions.assertEquals(1,idCaptor.getValue());
    }

    @Test
    public void printAllContact_happy() throws Exception {
        AddressBook book = new AddressBook();
        book.setId(1L);
        book.setBook("NORMAL");
        book.setName("david");
        book.setPhoneNumber("123456");
        when(addressBookService.printAllContactsByBook(anyString())).thenReturn(Arrays.asList(book));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/contactReports/NORMAL")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"book\":\"NORMAL\",\"name\":\"david\",\"phoneNumber\":\"123456\"}]"));
        verify(addressBookService,times(1)).printAllContactsByBook(bookCaptor.capture());
        Assertions.assertEquals("NORMAL",bookCaptor.getValue());
    }
    @Test
    public void printAllContact_happy2() throws Exception {
        AddressBook book = new AddressBook();
        book.setId(1L);
        book.setBook("NORMAL");
        book.setName("david");
        book.setPhoneNumber("123456");
        when(addressBookService.printAllContactsByBook(anyString())).thenReturn(Arrays.asList(book,book));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/contactReports/NORMAL")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": 1,\n" +
                        "    \"book\": \"NORMAL\",\n" +
                        "    \"name\": \"david\",\n" +
                        "    \"phoneNumber\": \"123456\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 1,\n" +
                        "    \"book\": \"NORMAL\",\n" +
                        "    \"name\": \"david\",\n" +
                        "    \"phoneNumber\": \"123456\"\n" +
                        "  }\n" +
                        "]"));
        verify(addressBookService,times(1)).printAllContactsByBook(bookCaptor.capture());
        Assertions.assertEquals("NORMAL",bookCaptor.getValue());
    }

    @Test
    public void printAllContact_noRecord() throws Exception {
        when(addressBookService.printAllContactsByBook(anyString())).thenThrow(new RecordNotFoundException("Record not found"));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/contactReports/NORMAL")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"Error\":\"Record not found\"}"));
        verify(addressBookService,times(1)).printAllContactsByBook(bookCaptor.capture());
        Assertions.assertEquals("NORMAL",bookCaptor.getValue());
    }

    @Test
    public void printAllContact_exception() throws Exception {
        when(addressBookService.printAllContactsByBook(anyString())).thenThrow(new RuntimeException("SQL Error"));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/contactReports/NORMAL")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{\"Error\":\"SQL Error\"}"));
        verify(addressBookService,times(1)).printAllContactsByBook(bookCaptor.capture());
        Assertions.assertEquals("NORMAL",bookCaptor.getValue());
    }

    @Test
    public void printUniqueContact_happy() throws Exception {
        ContactDto book = new ContactDto("david","123456");
        when(addressBookService.printUniqueContact()).thenReturn(Sets.newSet(book));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/uniqueContacts")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"david\",\"phoneNumber\":\"123456\"}]"));
        verify(addressBookService,times(1)).printUniqueContact();
    }
    @Test
    public void printUniqueContact_noRecord() throws Exception {
        when(addressBookService.printUniqueContact()).thenThrow(new RecordNotFoundException("Record not found"));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/uniqueContacts")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"Error\":\"Record not found\"}"));
        verify(addressBookService,times(1)).printUniqueContact();
    }
    @Test
    public void printUniqueContact_exception() throws Exception {
        when(addressBookService.printUniqueContact()).thenThrow(new RuntimeException("SQL Error"));
        mvc.perform(MockMvcRequestBuilders.get("/addressBook/uniqueContacts")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().json("{\"Error\":\"SQL Error\"}"));
        verify(addressBookService,times(1)).printUniqueContact();
    }

    private ConstraintViolationException generateException() {
        AddressBook book = new AddressBook();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AddressBook>> validations = validator.validate(book);
        return new ConstraintViolationException("Error",validations);
    }


}