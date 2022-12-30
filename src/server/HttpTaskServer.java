package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import com.sun.net.httpserver.HttpServer;
import constant.Endpoint;
import domain.Epic;
import domain.Subtask;
import domain.Task;
import managers.Managers;
import managers.TaskManager;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {


    private final int PORT = 8080;
    private final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private Gson gson;
    private TaskManager manager;
    private HttpServer server;


    public HttpTaskServer() throws IOException, InterruptedException {
        this.manager = Managers.getDefaultFile("http://localhost:8078");
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handleTask);
    }


    public TaskManager getManager() {
        return manager;
    }

    public void handleTask(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] elementPath = path.split("/");
        InputStream inputStream = exchange.getRequestBody();
        String jsonTask = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

        try {
            switch (requestMethod) {
                case ("GET"): {

                    if (elementPath.length == 2) {
                        String allTasks = gson.toJson(manager.getAllTasks());
                        sendText(exchange, allTasks);
                    } else if (elementPath[2].equals("history")) {
                        String history = gson.toJson(manager.getHistory().getSortedHistoryTask());
                        sendText(exchange, history);
                    } else if (elementPath.length < 4) {
                        sendText(exchange, getTasks(elementPath));
                    } else if ((elementPath.length < 5)) {
                        if (parseTaskId(elementPath[3]) != -1) {
                            sendText(exchange, getTaskById(elementPath));
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                        }
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                case ("POST"): {
                    if (elementPath.length == 3) {
                        addTask(elementPath, jsonTask);
                        exchange.sendResponseHeaders(200, 0);
                    } else if (elementPath.length == 4 && elementPath[3].equals("update")) {
                        updateTask(elementPath, jsonTask);
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                case ("DELETE"): {
                    if (elementPath.length == 2) {
                        manager.removeAllTasks();
                        exchange.sendResponseHeaders(200, 0);
                    } else if (elementPath.length < 4) {
                        deleteTasks(elementPath);
                        exchange.sendResponseHeaders(200, 0);
                    } else if ((elementPath.length < 5)) {
                        deleteTasksById(elementPath);
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                    }
                    break;
                }
                default: {
                    System.out.println("Переданный метод не найден");
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception exception) {
            exception.getStackTrace();
        } finally {
            exchange.close();
        }
    }


    public void addTask(String[] path, String bodyTask) {
        String typeTask = path[2];
        switch (typeTask) {
            case ("task"): {
                Task task = gson.fromJson(bodyTask, Task.class);
                manager.add(task);
                break;
            }
            case ("epic"): {
                System.out.println(bodyTask);
                Epic task = gson.fromJson(bodyTask, Epic.class);
                manager.add(task);
                System.out.println("задача добавлена");
                break;
            }
            case ("subtask"): {
                Subtask task = gson.fromJson(bodyTask, Subtask.class);
                manager.add(task);
                break;
            }
            default: {
                System.out.println("Задача не добавлена");
            }
        }

    }

    public void updateTask(String[] path, String bodyTask) {
        String typeTask = path[2];
        switch (typeTask) {
            case ("task"): {
                Task task = gson.fromJson(bodyTask, Task.class);
                manager.updateTask(task);
                break;
            }
            case ("epic"): {
                Epic task = gson.fromJson(bodyTask, Epic.class);
                manager.updateEpic(task);
                break;
            }
            case ("subtask"): {
                Subtask task = gson.fromJson(bodyTask, Subtask.class);
                manager.updateSubtask(task);
                break;
            }
            default: {
                System.out.println("Задача не добавлена");
            }
        }
    }

    public String getTasks(String[] path) {
        String tasks = "";
        switch (path[2]) {
            case ("task"): {
                tasks = gson.toJson(manager.getTasks());
                break;
            }
            case ("epic"): {
                tasks = gson.toJson(manager.getEpics());
                break;
            }
            case ("subtask"): {
                tasks = gson.toJson(manager.getSubtasks());
                break;
            }
            default: {
                System.out.println("Передан неизветсный тип задачи");
            }
        }
        return tasks;
    }

    public String getTaskById(String[] path) {
        String task = "";
        int taskId = parseTaskId(path[3]);
        switch (path[2]) {
            case ("task"): {
                task = gson.toJson(manager.getTaskById(taskId));
                break;
            }
            case ("epic"): {
                task = gson.toJson(manager.getEpicById(taskId));
                break;
            }
            case ("subtask"): {
                task = gson.toJson(manager.getSubtaskById(taskId));
                break;
            }
            default: {
                System.out.println("Передан неизветсный тип задачи");
            }
        }
        return task;
    }


    public void deleteTasks(String[] path) {
        switch (path[2]) {
            case ("task"): {
                manager.removeTasks();
                break;
            }
            case ("epic"): {
                manager.removeEpics();
                break;
            }
            case ("subtask"): {
                manager.removeSubtasks();
                break;
            }
            default: {
                System.out.println("Передан неизветсный тип задачи");
            }
        }
    }

    public void deleteTasksById(String[] path) {
        int taskId = parseTaskId(path[3]);
        switch (path[2]) {
            case ("task"): {
                manager.removeTaskById(taskId);
                break;
            }
            case ("epic"): {
                manager.removeEpicById(taskId);
                break;
            }
            case ("subtask"): {
                manager.removeSubtaskById(taskId);
                break;
            }
            default: {
                System.out.println("Передан неизветсный тип задачи");
            }
        }
    }

    public int parseTaskId(String taskId) {
        try {
            return Integer.parseInt(taskId);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }


    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
