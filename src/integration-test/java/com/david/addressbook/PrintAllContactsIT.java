package com.david.addressbook;


import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

public class PrintAllContactsIT extends RestfulBase {


   @Test
   @Sql("test_clear_data_it.sql")
    public void loadContact_empty() throws JSONException {
       String expected = "{\n" +
               "  \"Error\": \"No Record found from addres book NORMAL\"\n" +
               "}";
       assertRestful(HttpMethod.GET,"/addressBook/contactReports/NORMAL",null,expected,404);
    }

    @Test
    @Sql("test_data_it.sql")
    public void loadContact_hasRecord() throws JSONException {
        String expected = "[{\"id\":1,\"book\":\"NORMAL\",\"name\":\"David\",\"phoneNumbers\":[\"123456\"]},{\"id\":2,\"book\":\"NORMAL\",\"name\":\"david\",\"phoneNumbers\":[\"123456\"]},{\"id\":3,\"book\":\"NORMAL\",\"name\":\"David\",\"phoneNumbers\":[\"1234567\"]},{\"id\":4,\"book\":\"NORMAL\",\"name\":\"John\",\"phoneNumbers\":[\"987654\"]}]";
        assertRestful(HttpMethod.GET,"/addressBook/contactReports/NORMAL",null,expected,200);
    }







}
