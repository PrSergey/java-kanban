import domain.Task;

import java.util.List;

public interface HistoryManager {
    public void addHistoryTasks(Task task);

    public List<Task> getHistoryTasks();


}
