package managers;

import domain.Task;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface HistoryManager {
    void addHistoryTasks(Task task);

    void remove(int id);

    List<Task> getHistoryTasks();
    List<Task> getSortedHistoryTask();
    List<Integer> getHistoryId();




}