package nice.controllers;

import nice.Application;
import nice.constants.Status;
import nice.dto.RequestTaskDto;
import nice.dto.RequestUserDto;
import nice.dto.ResponseTaskDto;
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

/**
 * Integration Test for TaskController.
 *
 * @author danielctrenado@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test scenario to obtain all tasks.
     */
    @Test
    public void getTasks() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<RequestUserDto> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List> response = restTemplate.exchange(createURLWithPort("/tasks"),
                HttpMethod.GET, entity, List.class);

        List<ResponseTaskDto> tasks = response.getBody();
        assertNotNull(tasks);
        assertTrue(!tasks.isEmpty());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Test scenario to obtain all tasks by status.
     */
    @Test
    public void getTasksByStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<RequestUserDto> entity = new HttpEntity<>(null, headers);
        Status status = Status.IN_PROGRESS;
        ResponseEntity<List> response = restTemplate.exchange(createURLWithPort("/tasks?status=" + status),
                HttpMethod.GET, entity, List.class);

        List<ResponseTaskDto> tasks = response.getBody();
        assertNotNull(tasks);
        assertTrue(!tasks.isEmpty());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * Test scenario to add a task.
     */
    @Test
    public void addTask() {
        String taskName = "taskName";
        String descTask = "descTask";
        Status status = Status.NOT_STARTED;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<RequestTaskDto> entity = new HttpEntity<>(requestTaskDto, headers);

        ResponseEntity<ResponseTaskDto> response = restTemplate.exchange(createURLWithPort("/tasks"),
                HttpMethod.POST, entity, ResponseTaskDto.class);

        ResponseTaskDto responseTaskDto = response.getBody();
        assertNotNull(responseTaskDto);

        assertEquals(taskName, response.getBody().getName());
        assertEquals(descTask, response.getBody().getDescription());
        assertEquals(status, response.getBody().getStatus());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void updateTask() {
        //given
        String taskNewName = "newName";
        String taskNewDesc = "newDesc.";
        Status taskNewStatus = Status.COMPLETE;

        String taskName = "Read a book";
        String descTask = "Read a book about Kotlin.";
        Status status = Status.IN_PROGRESS;
        RequestTaskDto requestTaskDto = new RequestTaskDto(taskName, descTask, status, null);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<RequestTaskDto> entityCreateTask = new HttpEntity<>(requestTaskDto, headers);
        ResponseEntity<ResponseTaskDto> responseCreateTask = restTemplate.exchange(createURLWithPort("/tasks"),
                HttpMethod.POST, entityCreateTask, ResponseTaskDto.class);

        Map<String, Object> params = new HashMap<>();
        params.put("id", responseCreateTask.getBody().getId());

        requestTaskDto.setName(taskNewName);
        requestTaskDto.setDescription(taskNewDesc);
        requestTaskDto.setStatus(taskNewStatus);
        HttpEntity<RequestTaskDto> entityUpdateTask = new HttpEntity<>(requestTaskDto, headers);

        ResponseEntity<ResponseTaskDto> response = restTemplate.exchange(createURLWithPort("/tasks/{id}"),
                HttpMethod.PUT, entityUpdateTask, ResponseTaskDto.class, params);

        ResponseTaskDto responseTaskDto = response.getBody();
        assertNotNull(responseTaskDto);

        assertEquals(taskNewName, response.getBody().getName());
        assertEquals(taskNewDesc, response.getBody().getDescription());
        assertEquals(taskNewStatus, response.getBody().getStatus());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + "/api/v1" + uri;
    }

}
