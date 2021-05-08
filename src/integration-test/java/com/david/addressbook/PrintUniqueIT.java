package com.david.addressbook;


import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

public class PrintUniqueIT extends RestfulBase {


   @Test
   @Sql("test_clear_data_it.sql")
    public void printUniqueContact_empty() throws JSONException {
       String expected = "{\"Error\":\"No Record found from Address book\"}";
       assertRestful(HttpMethod.GET,"/addressBook/uniqueContacts",null,expected,404);
    }

    @Test
    @Sql({"test_clear_data_it.sql","test_data_it.sql"})
    public void printUniqueContact_hasRecord() throws JSONException {
        String expected = "[{\"name\":\"David\",\"phoneNumber\":\"123456\"},{\"name\":\"David\",\"phoneNumber\":\"1234567\"},{\"name\":\"John\",\"phoneNumber\":\"987654\"},{\"name\":\"david\",\"phoneNumber\":\"123456\"}]";
        assertRestful(HttpMethod.GET,"/addressBook/uniqueContacts",null,expected,200);
    }







}
