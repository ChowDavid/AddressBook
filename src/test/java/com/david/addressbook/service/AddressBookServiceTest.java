package com.david.addressbook.service;


import com.david.addressbook.dto.ContactDto;
import com.david.addressbook.entity.AddressBook;
import com.david.addressbook.exception.RecordNotFoundException;
import com.david.addressbook.repository.AddressBookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


class AddressBookServiceTest {

    @InjectMocks
    private AddressBookService addressBookService;
    @Mock
    private AddressBookRepository addressBookRepository;
    @Captor
    private ArgumentCaptor<AddressBook> addressBookArgumentCaptor;
    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

    }
    @Test
    public void saveContact_null(){
        Assertions.assertThrows(NullPointerException.class, () -> addressBookService.saveContact(null,null));
        Assertions.assertThrows(NullPointerException.class, () -> addressBookService.saveContact("name",null));
        Assertions.assertThrows(NullPointerException.class, () -> addressBookService.saveContact(null,new ContactDto(null,null)));
    }
    @Test
    public void saveContact(){
        addressBookService.saveContact("book",new ContactDto("david","123456"));
        verify(addressBookRepository,times(1)).save(addressBookArgumentCaptor.capture());
        Assertions.assertEquals("book",addressBookArgumentCaptor.getValue().getBook());
        Assertions.assertEquals("david",addressBookArgumentCaptor.getValue().getName());
        Assertions.assertEquals("123456",addressBookArgumentCaptor.getValue().getPhoneNumber());
    }

    @Test
    public void deleteContactById_null(){
        Assertions.assertThrows(NullPointerException.class, () -> addressBookService.deleteContactById(null));
    }

    @Test
    public void deleteContactById(){
        addressBookService.deleteContactById(1L);
        verify(addressBookRepository,times(1)).deleteById(longArgumentCaptor.capture());
        Assertions.assertEquals(1L,longArgumentCaptor.getValue());
    }

    @Test
    public void deleteContactById_Empty(){
        doThrow(EmptyResultDataAccessException.class).when(addressBookRepository).deleteById(anyLong());
        Assertions.assertThrows(RecordNotFoundException.class, ()->addressBookService.deleteContactById(1L));
        verify(addressBookRepository,times(1)).deleteById(longArgumentCaptor.capture());
        Assertions.assertEquals(1L,longArgumentCaptor.getValue());
    }

    @Test
    public void printAllContactsByBook_success(){
        List list = mock(List.class);
        when(list.isEmpty()).thenReturn(false);
        when(addressBookRepository.findByBook(anyString())).thenReturn(list);

        Assertions.assertEquals(list,addressBookService.printAllContactsByBook("any"));
        verify(addressBookRepository,times(1)).findByBook(stringArgumentCaptor.capture());
        Assertions.assertEquals("any",stringArgumentCaptor.getValue());
    }

    @Test
    public void printAllContactsByBook_input_null(){
        Assertions.assertThrows(NullPointerException.class, () -> addressBookService.printAllContactsByBook(null));
    }

    @Test
    public void printAllContactsByBook_noRecordFound(){
        List list = mock(List.class);
        when(list.isEmpty()).thenReturn(true);
        when(addressBookRepository.findByBook(anyString())).thenReturn(list);

        Assertions.assertThrows(RecordNotFoundException.class, () -> addressBookService.printAllContactsByBook("any"));
        verify(addressBookRepository,times(1)).findByBook(stringArgumentCaptor.capture());
        Assertions.assertEquals("any",stringArgumentCaptor.getValue());
    }

    @Test
    public void printUniqueContact_success(){
        Set set = mock(Set.class);
        when(set.isEmpty()).thenReturn(false);
        when(addressBookRepository.findUnique()).thenReturn(set);

        Assertions.assertEquals(set,addressBookService.printUniqueContact());
        verify(addressBookRepository,times(1)).findUnique();
    }

    @Test
    public void printUniqueContact_noRecord(){
        Set set = mock(Set.class);
        when(set.isEmpty()).thenReturn(true);
        when(addressBookRepository.findUnique()).thenReturn(set);

        Assertions.assertThrows(RecordNotFoundException.class, () -> addressBookService.printUniqueContact());
        verify(addressBookRepository,times(1)).findUnique();
    }


}