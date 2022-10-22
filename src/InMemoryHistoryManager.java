import domain.Task;


import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    LinkedList<Task> historyTasks = new LinkedList<>();

    @Override

    public List<Task> getHistoryTasks() {
        return historyTasks;
    }

    @Override
    public void addHistoryTasks(Task task) {
        if (historyTasks.size() < 10) {
            historyTasks.add(task);
        } else {
            historyTasks.removeFirst();
            historyTasks.add(task);
        }
    }
}
