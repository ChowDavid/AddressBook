package com.david.addressbook;

import org.apache.maven.surefire.shared.lang3.StringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

public class SaveContactIT extends RestfulBase {


    @Test
    @Sql("test_clear_data_it.sql")
    public void saveContact() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \"david\",\n" +
                "  \"phoneNumbers\": [\"123\"]\n" +
                "}";
        String expected = "{\"id\":1,\"book\":\"NORMAL\",\"name\":\"david\",\"phoneNumbers\":[\"123\"]}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,200);

    }
    @Test
    public void saveContact_nameEmpty() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \"\",\n" +
                "  \"phoneNumbers\": [\"123\"]\n" +
                "}";
        String expected = "{\"name\":\"Please provide the name\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }
    @Test
    public void saveContact_nameEmpty2() throws JSONException {
        String inputJson ="{\n" +
                "  \"phoneNumbers\": [\"123\"]\n" +
                "}";
        String expected = "{\"name\":\"Please provide the name\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }
    @Test
    public void saveContact_nameTooLong() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \""+ StringUtils.repeat("a", 51)+"\",\n" +
                "  \"phoneNumbers\": [\"123\"]\n" +
                "}";
        String expected = "{\"name\":\"Name - Max Length cannot longer than 50 characters\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }
    @Test
    public void saveContact_nameMissing() throws JSONException {
        String inputJson ="{\n" +
                "  \"phoneNumbers\": [\"123\"]\n" +
                "}";
        String expected = "{\"name\":\"Please provide the name\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }
    @Test
    public void saveContact_bookTooLong() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \"david\",\n" +
                "  \"phoneNumbers\": [\"123\"]\n" +
                "}";
        String expected = "{\"book\":\"Address book name Cannot longer than 10 characters\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/"+StringUtils.repeat("A",11),inputJson,expected,400);
    }

    @Test
    public void saveContact_phoneEmpty() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \"david\"\n" +
                "}";
        String expected = "{\"phoneNumbers\":\"Please provide the phone numbers\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }
    @Test
    public void saveContact_phoneEmpty2() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \"david\",\n" +
                "  \"phoneNumber\": \"\"\n" +
                "}";
        String expected = "{\"phoneNumbers\":\"Please provide the phone numbers\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }

    @Test
    public void saveContact_phoneTooLong() throws JSONException {
        String inputJson ="{\n" +
                "  \"name\": \"david\",\n" +
                "  \"phoneNumbers\": [\""+StringUtils.repeat("A",51)+"\"]\n" +
                "}";
        String expected = "{\"phoneNumbers\":\"Phone number - Max Length cannot longer than 50 characters\"}";
        assertRestful(HttpMethod.POST,"/addressBook/contacts/NORMAL",inputJson,expected,400);
    }






}
