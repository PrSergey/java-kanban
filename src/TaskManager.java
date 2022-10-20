import domain.Epic;
import domain.Subtask;
import domain.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {




    public void add(Task taskIn);
    public void add(Epic taskIn);

    public void add(Subtask taskIn, int epicId);



    public void updateEpicStatus(int epicId);

    public List getSimpleTasks();

    public List getEpics();

    public List getSubtasks();

    public void removeTask();

    public void removeEpics();

    public void removeSubtasks();

    public Task getTaskById(int needId);

    public Task getSubtaskById(int needId);

    public Task getEpicById(int needId);

    public void removeTaskById(int needId);

    public void removeEpicById(int needId);

    public void removeSubtaskById(int needId);

    public void updateTask(Task task);

    public void updateEpic(Epic task);

    public void updateSubtask(Subtask task);

    public List<Integer> subtaskByEpic(int epicId);

}
