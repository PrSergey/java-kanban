package managers;

import domain.Task;


import java.util.List;


public interface HistoryManager {
    void addHistoryTasks(Task task);

    void remove(int id);

    List<Task> getHistoryTasks();


}