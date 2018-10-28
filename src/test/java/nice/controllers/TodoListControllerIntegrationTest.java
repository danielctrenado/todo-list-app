package nice.controllers;

import nice.Application;
import nice.constants.Status;
import nice.dto.*;
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
public class TodoListControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getTodoLists() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<RequestUserDto> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List> response = restTemplate.exchange(createURLWithPort("/todolists"),
                HttpMethod.GET, entity, List.class);

        List<ResponseTodoListDto> todoListDtos = response.getBody();
        assertNotNull(todoListDtos);
        assertTrue(!todoListDtos.isEmpty());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void addTodoList() {

        String name = "todolistname";
        RequestTodoListDto requestTodoListDto = new RequestTodoListDto(name, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<RequestTodoListDto> entity = new HttpEntity<>(requestTodoListDto, headers);

        ResponseEntity<ResponseTodoListDto> response = restTemplate.exchange(createURLWithPort("/todolists"),
                HttpMethod.POST, entity, ResponseTodoListDto.class);

        ResponseTodoListDto responseTodoListDto = response.getBody();
        assertNotNull(responseTodoListDto);

        assertEquals(name, responseTodoListDto.getName());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deleteTodoList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        RequestTodoListDto requestTodoListDto = new RequestTodoListDto("taskToDelete", null);
        HttpEntity<RequestTodoListDto> entityCreateTodoList = new HttpEntity<>(requestTodoListDto, headers);

        ResponseEntity<ResponseTodoListDto> responseCreateTodoList = restTemplate.exchange(
                createURLWithPort("/todolists"), HttpMethod.POST, entityCreateTodoList,
                ResponseTodoListDto.class);

        Map<String, Object> params = new HashMap<>();
        params.put("id", responseCreateTodoList.getBody().getId());
        HttpEntity<RequestTodoListDto> entityDeleteTodoList = new HttpEntity<>(null, headers);

        ResponseEntity<String> responseDeleteTodoList = restTemplate.exchange(
                createURLWithPort("/todolists/{id}"), HttpMethod.DELETE,
                entityDeleteTodoList, String.class, params);

        assertEquals(responseDeleteTodoList.getStatusCode(), HttpStatus.OK);
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/v1" + uri;
    }

}
