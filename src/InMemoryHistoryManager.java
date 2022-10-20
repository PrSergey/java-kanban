import domain.Task;


import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> historyTasks = new ArrayList<>();
    @Override

    public List<Task> getHistoryTasks() {
        return historyTasks;
    }

    @Override
    public  void addHistoryTasks(Task task) {
        if (historyTasks.size()<10){
            historyTasks.add(task);
        } else {
            for (int i=0; i<9; i++){
                historyTasks.add(i, historyTasks.get(i+1));
            }
            historyTasks.add(9,task);
        }
    }
}
