package com.david.addressbook;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

@Slf4j
@SpringBootTest(classes = AddressBookApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestfulBase {

    @LocalServerPort
    protected int port;

    protected TestRestTemplate restTemplate = new TestRestTemplate();

    protected String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    protected HttpHeaders getHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    protected void assertRestful(HttpMethod method, String path, String inputJson, String expected, int expectedResponseCode) throws JSONException {
        HttpEntity<String> request = null;
        if (inputJson!=null){
            request = new HttpEntity<>(inputJson,getHeader());
        }
        ResponseEntity<String> response  = restTemplate.exchange(createURLWithPort(path), method,request,String.class);
        log.info("Response body {}",response.getBody());
        JSONAssert.assertEquals(expected,response.getBody(),false);
        Assertions.assertEquals(expectedResponseCode,response.getStatusCodeValue());
    }
}
