package nice.controllers;

import nice.Application;
import nice.dto.RequestUserDto;
import nice.dto.ResponseUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<RequestUserDto> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List> response = restTemplate.exchange(createURLWithPort("/users"),
                HttpMethod.GET, entity, List.class);

        List<ResponseUserDto> users = response.getBody();
        assertNotNull(users);
        assertTrue(!users.isEmpty());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void addUser() {
        String userName = "batman";
        RequestUserDto requestUserDto = new RequestUserDto(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<RequestUserDto> entity = new HttpEntity<>(requestUserDto, headers);

        ResponseEntity<ResponseUserDto> response = restTemplate.exchange(createURLWithPort("/users"),
                HttpMethod.POST, entity, ResponseUserDto.class);

        ResponseUserDto responseUserDto = response.getBody();
        assertNotNull(responseUserDto);
        assertEquals(userName, response.getBody().getUserName());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateUser() {
        String userName = "Hulk";
        RequestUserDto requestUserDto = new RequestUserDto(userName);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<RequestUserDto> entity = new HttpEntity<>(requestUserDto, headers);
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);

        ResponseEntity<ResponseUserDto> response = restTemplate.exchange(createURLWithPort("/users/{id}"),
                HttpMethod.PUT, entity, ResponseUserDto.class, params);

        ResponseUserDto responseUserDto = response.getBody();
        assertNotNull(responseUserDto);
        assertEquals(1, response.getBody().getId());
        assertEquals(userName, response.getBody().getUserName());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        RequestUserDto requestUserDto = new RequestUserDto("Venom");
        HttpEntity<RequestUserDto> entityCreateUser = new HttpEntity<>(requestUserDto, headers);

        ResponseEntity<ResponseUserDto> responseCreateUser = restTemplate.exchange(createURLWithPort("/users"),
                HttpMethod.POST, entityCreateUser, ResponseUserDto.class);

        Map<String, Object> params = new HashMap<>();
        params.put("id", responseCreateUser.getBody().getId());
        HttpEntity<RequestUserDto> entityDeleteUser = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseDeleteUser = restTemplate.exchange(createURLWithPort("/users/{id}"),
                HttpMethod.DELETE, entityDeleteUser, String.class, params);

        assertEquals(responseDeleteUser.getStatusCode(), HttpStatus.OK);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/v1" + uri;
    }

}
