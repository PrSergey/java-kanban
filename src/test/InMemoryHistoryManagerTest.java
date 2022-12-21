package test;

import constant.Status;
import domain.Epic;
import domain.Task;
import managers.InMemoryHistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class InMemoryHistoryManagerTest  {


    InMemoryHistoryManager manager;
    LocalDateTime timeTask1 = LocalDateTime.of(2022, 11, 14, 15, 14);

    Epic epic1WithTime1 = new Epic("пробная", "эпик номер 1", Status.NEW, timeTask1, Duration.ofMinutes(50));


    InMemoryHistoryManager getManager() {
        return new InMemoryHistoryManager();
    }

    public void addTwoSaneTask(){
        manager.addHistoryTasks(epic1WithTime1);
        manager.addHistoryTasks(epic1WithTime1);
    }

    @BeforeEach
    public void setup() {
        manager = getManager();
    }

    @Test
    public void shouldGetSortedHistoryTaskWhenNotTask(){
        Assertions.assertEquals(manager.getSortedHistoryTask().size(), 0);
    }

    @Test
    public void shouldGetHistoryIdWhenNotTask(){
        Assertions.assertEquals(manager.getHistoryId().size(), 0);
    }

    @Test
    public void shouldSortedHistoryIdWhenNotTask(){
        Assertions.assertEquals(manager.sortedHistoryId().size(), 0);
    }

    @Test
    public void shouldGetHistoryTasksWhenNotTask(){
        Assertions.assertEquals(manager.getHistoryTasks().size(), 0);
    }

    @Test
    public void addHistoryTasksWhenTaskIsNull(){
        manager.addHistoryTasks(null);
        Assertions.assertEquals(manager.sortedHistoryId().size(), 0);
    }

    @Test
    public void removeTaskWhenHistoryTaskIsEmpty(){
        manager.remove(1);
        Assertions.assertEquals(manager.sortedHistoryId().size(), 0);
    }

    @Test
    public void shouldToStringHistoryTasksWhenNotTask(){
        Assertions.assertEquals(manager.toString(), "");
    }


    @Test
    public void shouldGetSortedHistoryTaskWithTwoTask(){
        addTwoSaneTask();
        Assertions.assertEquals(manager.getSortedHistoryTask().size(), 1);
    }

    @Test
    public void shouldGetHistoryIdWithTwoTask(){
        addTwoSaneTask();
        Assertions.assertEquals(manager.getHistoryId().size(), 1);
    }

    @Test
    public void shouldSortedHistoryIdWithTwoTask(){
        addTwoSaneTask();
        Assertions.assertEquals(manager.sortedHistoryId().size(), 1);
    }

    @Test
    public void shouldGetHistoryTasksWithTwoTask(){
        addTwoSaneTask();
        Assertions.assertEquals(manager.getHistoryTasks().size(), 1);
    }

    @Test
    public void addHistoryTasks(){
        manager.addHistoryTasks(epic1WithTime1);
        Assertions.assertEquals(manager.sortedHistoryId().size(), 1);
    }

    @Test
    public void removeTaskWhenHistoryTaskWithTwoTask(){
        addTwoSaneTask();
        manager.remove(0);
        Assertions.assertEquals(manager.sortedHistoryId().size(), 0);
    }

    @Test
    public void shouldToStringHistoryTasksWithTwoTask(){
        addTwoSaneTask();;
        Assertions.assertEquals(manager.toString(), "1 была вызвана задача с id: 0" +"\n");
    }



}
