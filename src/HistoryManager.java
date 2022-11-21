import domain.Task;


import java.util.Map;


public interface HistoryManager {
    void addHistoryTasks(Task task);

    void remove(int id);

    Map<Integer, InMemoryHistoryManager.Node> getHistoryTasks();


}
