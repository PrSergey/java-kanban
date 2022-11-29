package managers;

import domain.Epic;
import domain.Subtask;
import domain.Task;
import managers.HistoryManager;


import java.util.List;

public interface TaskManager {


    void add(Task taskIn);

    void add(Epic taskIn);

    void add(Subtask taskIn, int epicId);


    void updateEpicStatus(int epicId);

    List<Task> getTasks();

    List<Task> getEpics();

    List<Task> getSubtasks();

    void removeTasks();

    void removeEpics();

    void removeSubtasks();

    Task getTaskById(int needId);

    Task getSubtaskById(int needId);

    Task getEpicById(int needId);

    void removeTaskById(int needId);

    void removeEpicById(int needId);

    void removeSubtaskById(int needId);

    void updateTask(Task task);

    void updateEpic(Epic task);

    void updateSubtask(Subtask task);

    List<Integer> subtaskByEpic(int epicId);

    HistoryManager getHistory();
}
