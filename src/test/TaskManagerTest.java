package test;

import domain.Epic;
import constant.Status;
import domain.Subtask;
import domain.Task;
import managers.HistoryManager;
import managers.TaskManager;
import constant.TaskType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class TaskManagerTest<T extends TaskManager> {

    T manager;
    LocalDateTime timeTask1 = LocalDateTime.of(2022, 11, 14, 15, 14);
    LocalDateTime timeTask2 = LocalDateTime.of(2022, 11, 15, 15, 14);
    LocalDateTime timeTask3 = LocalDateTime.of(2022, 11, 16, 15, 14);
    Epic epic1WithTime1 = new Epic("пробная", "эпик номер 1", Status.NEW, timeTask1, Duration.ofMinutes(50));
    Task taskWithTime1 = new Task("пробная", "эпик номер 1", Status.NEW, timeTask2, Duration.ofMinutes(50));


    abstract T getManager();

    @BeforeEach
    public void setup() {
        manager = getManager();
    }


    public void addSubtaskWithDifferentStatus(Status StatusSubtask1, Status StatusSubtask2) {
        Subtask subtask1WithTime1 = new Subtask("пробная подзадача 1", "подзадача для эпик 1",
                StatusSubtask1, timeTask2, Duration.ofMinutes(50), epic1WithTime1.getId());
        manager.add(subtask1WithTime1, epic1WithTime1.getId());
        Subtask subtask2WithTime1 = new Subtask("пробная подзадача 1", "подзадача для эпик 1",
                StatusSubtask2, timeTask3, Duration.ofMinutes(50), epic1WithTime1.getId());
        manager.add(subtask2WithTime1, epic1WithTime1.getId());
    }


    public void shouldAddTaskInManager() {
        manager.add(taskWithTime1);
        Assertions.assertEquals(taskWithTime1, manager.getTasks().get(0));
    }


    public void shouldAddEpicInManager() {
        manager.add(epic1WithTime1);
        Assertions.assertEquals(epic1WithTime1, manager.getEpics().get(0));
    }


    public void shouldAddSubtaskInManager() {
        manager.add(epic1WithTime1);
        Subtask subtask2WithTime1 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", Status.NEW,
                timeTask2, Duration.ofMinutes(50), 1);
        manager.add(subtask2WithTime1, 1);
        Assertions.assertEquals(subtask2WithTime1, manager.getSubtasks().get(0));
    }


    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusNew() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.NEW, Status.NEW);
        Assertions.assertEquals(epic1WithTime1.getStatus(), Status.NEW);
    }


    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusDone() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.DONE, Status.DONE);
        Assertions.assertEquals(epic1WithTime1.getStatus(), Status.DONE);
    }


    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusDoneAndNew() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.DONE, Status.NEW);
        Assertions.assertEquals(epic1WithTime1.getStatus(), Status.IN_PROGRESS);
    }


    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusInProgress() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.IN_PROGRESS, Status.IN_PROGRESS);
        Assertions.assertEquals(epic1WithTime1.getStatus(), Status.IN_PROGRESS);
    }


    public void shouldNotAddSubtaskWithoutEpic() {
        addSubtaskWithDifferentStatus(Status.IN_PROGRESS, Status.IN_PROGRESS);
        Assertions.assertEquals(manager.getSubtasks().size(), 0);
    }


    public void shouldReturnAnEmptyListTasks() {
        Assertions.assertEquals(manager.getTasks().size(), 0);
    }


    public void shouldGetTasksWithTask() {
        manager.add(taskWithTime1);
        Assertions.assertEquals(manager.getTasks().size(), 1);
    }


    public void shouldReturnAnEmptyListEpics() {
        Assertions.assertEquals(manager.getEpics().size(), 0);
    }


    public void shouldGetTaskWithEpics() {
        manager.add(epic1WithTime1);
        Assertions.assertEquals(manager.getEpics().size(), 1);
    }


    public void shouldReturnAnEmptyListSubtasks() {
        Assertions.assertEquals(manager.getSubtasks().size(), 0);
    }


    public void shouldGetListWithSubtaskWithTwoTasks() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.NEW, Status.NEW);
        Assertions.assertEquals(manager.getSubtasks().size(), 2);
    }


    public void shouldRemoveTasksListWithoutTask() {
        manager.removeTasks();
        Assertions.assertEquals(manager.getTasks().size(), 0);
    }


    public void shouldRemoveTasksWithoutEpic() {
        manager.removeEpics();
        Assertions.assertEquals(manager.getEpics().size(), 0);
    }

    public void shouldRemoveAllTasksWithoutSubtask() {
        manager.removeSubtasks();
        Assertions.assertEquals(manager.getSubtasks().size(), 0);
    }


    public void shouldRemoveAllTasks() {
        manager.add(taskWithTime1);
        manager.removeTasks();
        Assertions.assertEquals(manager.getTasks().size(), 0);
    }


    public void shouldRemoveAllEpics() {
        manager.add(epic1WithTime1);
        manager.removeEpics();
        Assertions.assertEquals(manager.getEpics().size(), 0);
    }


    public void shouldRemoveAllSubtasks() {
        addSubtaskWithDifferentStatus(Status.NEW, Status.NEW);
        manager.removeSubtasks();
        Assertions.assertEquals(manager.getSubtasks().size(), 0);
    }


    public void shouldReturnNullWhenTasksIsEmpty() {
        Assertions.assertNull(manager.getTaskById(5));
    }


    public void shouldReturnNullWhenEpicsIsEmpty() {
        Assertions.assertNull(manager.getEpicById(5));
    }


    public void shouldReturnNullWhenSubtasksIsEmpty() {
        Assertions.assertNull(manager.getSubtaskById(5));
    }


    public void shouldReturnTaskAfterAddTask() {
        manager.add(taskWithTime1);
        Assertions.assertEquals(manager.getTaskById(1), taskWithTime1);
    }


    public void shouldReturnTaskAfterAddEpic() {
        manager.add(epic1WithTime1);
        Assertions.assertEquals(manager.getEpicById(1), epic1WithTime1);
    }


    public void shouldReturnTaskAfterAddSubtask() {
        manager.add(epic1WithTime1);
        Subtask subtaskWithTime1 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", Status.NEW,
                timeTask2, Duration.ofMinutes(50), 1);
        manager.add(subtaskWithTime1, 1);
        Assertions.assertEquals(manager.getSubtaskById(2), subtaskWithTime1);
    }


    public void shouldRemoveTaskByIdWithoutTask() {
        manager.removeTaskById(5);
        Assertions.assertNull(manager.getTaskById(5));
    }


    public void shouldRemoveEpicsByIdWithoutTask() {
        manager.removeEpicById(5);
        Assertions.assertNull(manager.getEpicById(5));
    }


    public void shouldRemoveSubtaskByIdWithoutTask() {
        manager.removeSubtaskById(5);
        Assertions.assertNull(manager.getSubtaskById(5));
    }


    public void shouldRemoveTaskById() {
        manager.add(taskWithTime1);
        manager.removeTaskById(1);
        Assertions.assertNull(manager.getTaskById(1));
    }


    public void shouldRemoveEpicById() {
        manager.add(epic1WithTime1);
        manager.removeEpicById(1);
        Assertions.assertNull(manager.getEpicById(1));
    }


    public void shouldRemoveSubtaskById() {
        addSubtaskWithDifferentStatus(Status.NEW, Status.NEW);
        manager.removeSubtaskById(1);
        Assertions.assertNull(manager.getSubtaskById(1));
    }


    public void shouldUpdateTaskWhenThisTaskIsNotThere() {
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 10, 12, 11, 14);
        Task task = new Task(1, TaskType.TASK, "Проверочная задача", Status.NEW, "Описание", timeTask1,
                Duration.ofMinutes(50));
        manager.updateTask(task);
        Assertions.assertNotNull(manager.getTaskById(1));
    }


    public void shouldUpdateEpicWhenThisEpicIsNotThere() {
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 10, 12, 11, 14);
        Epic epic = new Epic(1, TaskType.EPIC, "Проверочная задача", Status.NEW, "Описание", timeTask1,
                Duration.ofMinutes(50));
        manager.updateEpic(epic);
        Assertions.assertNotNull(manager.getEpicById(1));
    }


    public void shouldUpdateSubtaskWhenThisSubtaskIsNotThere() {
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 10, 12, 11, 14);
        Subtask subtask = new Subtask(1, TaskType.SUBTASK, "Проверочная задача", Status.NEW, "Описание", timeTask1,
                Duration.ofMinutes(50), 1);
        manager.updateSubtask(subtask);
        Assertions.assertNull(manager.getTaskById(1));
    }


    public void shouldUpdateTask() {
        manager.add(taskWithTime1);
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 10, 12, 11, 14);
        Task task = new Task(1, TaskType.TASK, "Проверочная задача", Status.NEW, "Описание", timeTask1,
                Duration.ofMinutes(50));
        manager.updateTask(task);
        Assertions.assertEquals(manager.getTaskById(1), task);
    }


    public void shouldUpdateEpic() {
        manager.add(epic1WithTime1);
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 10, 12, 11, 14);
        Epic task = new Epic(1, TaskType.EPIC, "Проверочная задача", Status.NEW, "Описание", timeTask1,
                Duration.ofMinutes(50));
        manager.updateEpic(task);
        Assertions.assertEquals(manager.getEpicById(1), task);
    }


    public void shouldUpdateSubtask() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.NEW, Status.NEW);
        LocalDateTime timeTask1 = LocalDateTime.of(2022, 10, 12, 11, 14);
        Subtask subtask = new Subtask(3, TaskType.SUBTASK, "Проверочная задача", Status.DONE,
                "Описание", timeTask1, Duration.ofMinutes(50), 1);
        manager.updateSubtask(subtask);
        Assertions.assertEquals(manager.getSubtaskById(3), subtask);
    }


    public void shouldReturnListOfEmptySubtask() {
        manager.add(epic1WithTime1);
        Assertions.assertEquals(manager.getSubtaskByEpic(1).size(), 0);
    }


    public void shouldReturnNullWhenEpicIsNotThere() {
        Assertions.assertNull(manager.getSubtaskByEpic(1));
    }


    public void shouldReturnListWithTwoSubtask() {
        manager.add(epic1WithTime1);
        addSubtaskWithDifferentStatus(Status.NEW, Status.NEW);
        Assertions.assertEquals(manager.getSubtaskByEpic(1).size(), 2);
    }


    public void shouldAddTaskInHistoryManager() {
        HistoryManager history = manager.getHistory();
        history.addHistoryTasks(epic1WithTime1);
        Assertions.assertEquals(history.getHistoryTasks().size(), 1);
    }


}
