package com.david.addressbook;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

public class DeleteContactIT extends RestfulBase {


   @Test
   @Sql("test_clear_data.sql")
    public void deleteContact_NotFound() throws JSONException {
       String expected = "{\"Error\":\"Contact id:1 Record Not found\"}";
       assertRestful(HttpMethod.DELETE,"/addressBook/contacts/1",null,expected,404);
    }

    @Test
    @Sql("test_data.sql")
    public void deleteContact_Success() throws JSONException {
       assertRestful(HttpMethod.DELETE,"/addressBook/contacts/1",null,null,204);
    }







}
