package managers;

import domain.Epic;
import domain.Subtask;
import domain.Task;


import java.util.List;
import java.util.Set;

public interface TaskManager {


    void add(Task taskIn);

    void add(Epic taskIn);

    void add(Subtask taskIn);


    void updateEpicStatus(int epicId);

    List<Task> getTasks();

    List<Task> getEpics();

    List<Task> getSubtasks();

    List<Task> getAllTasks();
    void removeTasks();

    void removeEpics();

    void removeSubtasks();
    void removeAllTasks();

    Task getTaskById(int needId);

    Task getSubtaskById(int needId);

    Task getEpicById(int needId);

    void removeTaskById(int needId);

    void removeEpicById(int needId);

    void removeSubtaskById(int needId);

    void updateTask(Task task);

    void updateEpic(Epic task);

    void updateSubtask(Subtask task);

    List<Integer> getSubtaskByEpic(int epicId);

    HistoryManager getHistory();

    Set<Task> getPrioritizedTasks();

    boolean checkIntersectionByTime(Task task);
}

