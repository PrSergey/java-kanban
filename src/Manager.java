import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    HashMap<Integer, SimpleTask> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int i = 1;

    public void add(SimpleTask taskIn) {
        taskIn.setId(i++);
        tasks.put(taskIn.getId(), taskIn);

    }

    public void add(Epic taskIn) {
        taskIn.setId(i++);
        epics.put(taskIn.getId(), taskIn);
        taskIn.status = "NEW";
    }

    public void add(Subtask taskIn, int epicId) {
        taskIn.setId(i++);
        subtasks.put(taskIn.getId(), taskIn);
        epics.get(epicId).subtaskId.put(taskIn.getId(), taskIn.status);
        statusEpic(epicId);
    }

    public void statusEpic(int epicId) {
        for (Integer statusSub : epics.get(epicId).subtaskId.keySet()) {
            if (epics.get(epicId).subtaskId.get(statusSub).equals("Done")) {
                epics.get(epicId).status = "Done";
            } else {
                epics.get(epicId).status = "IN_PROGRESS";
            }
        }
    }


    public ArrayList getTasks() {
        ArrayList task = new ArrayList<>();
        for (int id : tasks.keySet()) {
            task.add(tasks.get(id));

        }
        return task;
    }

    public ArrayList getEpics() {
        ArrayList epics = new ArrayList<>();
        for (int id : tasks.keySet()) {
            epics.add(tasks.get(id));
        }
        return epics;
    }

    public ArrayList getSubtasks() {
        ArrayList subtasks = new ArrayList<>();
        for (int id : tasks.keySet()) {
            subtasks.add(tasks.get(id));

        }
        return subtasks;
    }

    public void removeTask() {
        tasks.clear();
    }

    public void removeEpics() {
        epics.clear();
    }

    public void removeSubtasks() {
        subtasks.clear();

    }

    public Task getTaskById(int needId) {
        return tasks.get(needId);
    }

    public Task getSubtaskById(int needId) {
        return subtasks.get(needId);

    }

    public Task getEpicById(int needId) {
        return epics.get(needId);
    }

    public void removeTaskById(int needId) {
        tasks.remove(needId);
    }

    public void removeEpicById(int needId) {
        epics.remove(needId);
    }

    public void removeSubtaskById(int needId) {
        int epicId = subtasks.get(needId).epicId;
        subtasks.remove(needId);
        epics.get(epicId).subtaskId.remove(needId);
        statusEpic(epicId);
    }

    public void updateTask(SimpleTask task) {
        tasks.put(task.id, task);

    }

    public void updateEpic(Epic task) {
        epics.put(task.id, task);
        statusEpic(task.id);
    }

    public void updateSubtask(Subtask task) {
        subtasks.put(task.id, task);
        statusEpic(task.epicId);
    }

    public HashMap SybtaskByEpic(Epic task) {
        return task.subtaskId;
    }

}







