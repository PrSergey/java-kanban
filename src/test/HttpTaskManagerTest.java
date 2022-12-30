package test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import constant.Status;
import constant.TaskType;
import domain.Epic;
import domain.Subtask;
import managers.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskManagerTest {

    HttpTaskServer server;
    Gson gson = Managers.getGson();
    String newTaskJson;
    String newSubtaskJson;
    Epic newTask;
    Subtask newSubtask;
    KVServer kvServer;
    HttpClient client;


    public void createTaskInJson() {
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 11, 19, 15, 14);
        newTask = new Epic(1, "пробная эпика", "epic №1", Status.NEW, TaskType.EPIC, timeTask1, Duration.ofMinutes(50));
        newTaskJson = gson.toJson(newTask);
        LocalDateTime timeTask2 = LocalDateTime.of(2022, 11, 20, 15, 14);
        newSubtask = new Subtask(2, "пробная subtask", "subtask №1", Status.NEW, TaskType.SUBTASK, timeTask2, Duration.ofMinutes(50), 1);
        newSubtaskJson = gson.toJson(newSubtask);
    }

    @BeforeEach
    public void startServers() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        server = new HttpTaskServer();
        server.start();
        client = HttpClient.newHttpClient();
        ;
    }

    @AfterEach
    public void stopServers() {
        kvServer.stop();
        server.stop();
    }

    @Test
    public void shouldReturnEmptyTasks() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(), 200);
        Assertions.assertEquals(response.body(), "[]");
    }

    @Test
    public void shouldReturnCode200AfterAddEpicInServer() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic");
        createTaskInJson();
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(newTaskJson)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void shouldReturnEpicAfterAddEpic() throws IOException, InterruptedException {
        URI url = URI.create("http://localhost:8080/tasks/epic");
        createTaskInJson();
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(newTaskJson)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(server.getManager().getEpicById(1), newTask);
    }

    @Test
    public void shouldReturnTwoTaskAfterAddEpicAndSubtask() throws IOException, InterruptedException {
        URI urlEpic = URI.create("http://localhost:8080/tasks/epic");
        createTaskInJson();
        HttpRequest requestEpic = HttpRequest.newBuilder().uri(urlEpic).POST(HttpRequest.BodyPublishers.ofString(newTaskJson)).build();
        HttpResponse<String> responseEpic = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
        URI urlSubtask = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest requestSubtask = HttpRequest.newBuilder().uri(urlSubtask).POST(HttpRequest.BodyPublishers.ofString(newSubtaskJson)).build();
        HttpResponse<String> responseSubtask = client.send(requestSubtask, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(server.getManager().getAllTasks().size(), 2);
    }


}

