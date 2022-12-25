import adapter.LocalDateTimeAdapter;
import com.google.gson.Gson;
import constant.Status;
import domain.Epic;
import domain.Subtask;
import domain.Task;
import managers.HttpTaskManager;
import managers.Managers;
import server.HttpTaskServer;
import server.KVTaskClient;
import server.KVServer;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Gson gson=Managers.getGson();



        LocalDateTime timeTask1 = LocalDateTime.of(2022, 11, 16, 15, 14);
        LocalDateTime timeTask2 = LocalDateTime.of(2022, 11, 19, 15, 14);
        LocalDateTime timeTask3 = LocalDateTime.of(2022, 11, 20, 15, 14);
        Epic epic1WithTime1 = new Epic("пробная", "эпик номер 1", Status.NEW, timeTask1, Duration.ofMinutes(50));
        Task task1WithTime1 = new Task("пробная", "таск номер 1", Status.NEW, timeTask2, Duration.ofMinutes(50));
        Subtask subtask1WithTime1 = new Subtask("пробная", "сабтаск номер 1", Status.NEW, timeTask3, Duration.ofMinutes(50), 1);


        new KVServer().start();

        HttpTaskManager manager = new HttpTaskManager("http://localhost:8080");


    }

}