import domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    HashMap<Integer, Task> simpleTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int id = 1;
    HistoryManager history=new InMemoryHistoryManager();




@Override
    public void add(Task taskIn) {
        taskIn.setId(id++);
        simpleTasks.put(taskIn.getId(), taskIn);

    }
    @Override
    public void add(Epic taskIn) {
        taskIn.setId(id++);
        epics.put(taskIn.getId(), taskIn);
        taskIn.setStatus(Status.NEW);
    }
    @Override
    public void add(Subtask taskIn, int epicId) {
        taskIn.setId(id++);
        subtasks.put(taskIn.getId(), taskIn);
        epics.get(epicId).getSubtaskId().add(taskIn.getId());
        updateEpicStatus(epicId);
    }
    @Override
    public void updateEpicStatus(int epicId) {
        Status status;
        if (epics.get(epicId).getSubtaskId().isEmpty()) {
            return;
        }
        status = subtasks.get(epics.get(epicId).getSubtaskId().get(0)).getStatus();

        for (Integer idSubtask : epics.get(epicId).getSubtaskId()) {
            if (!status.equals(subtasks.get(idSubtask).getStatus())) {
                epics.get(epicId).setStatus(Status.IN_PROGRESS);
            } else {
                epics.get(epicId).setStatus(subtasks.get(epics.get(epicId).getSubtaskId().get(0)).getStatus());
            }
        }
    }
    @Override
    public List getSimpleTasks() {
        List simpleTasksList = new ArrayList<>();
        for (int id : simpleTasks.keySet()) {
            simpleTasksList.add(simpleTasks.get(id));
        }
        return simpleTasksList;
    }

    public List getEpics() {
        List epicsList = new ArrayList<>();
        for (int id : epics.keySet()) {
            epicsList.add(epics.get(id));
        }
        return epicsList;
    }

    public List getSubtasks() {
        List subtasksList = new ArrayList<>();
        for (int id : subtasks.keySet()) {
            subtasksList.add(subtasks.get(id));

        }
        return subtasksList;
    }

    public void removeTask() {
        simpleTasks.clear();
    }

    public void removeEpics() {
        epics.clear();
    }

    public void removeSubtasks() {

        subtasks.clear();

    }

    public Task getTaskById(int needId) {
        history.addHistoryTasks(simpleTasks.get(needId));
        return simpleTasks.get(needId);
    }

    public Task getSubtaskById(int needId) {
        history.addHistoryTasks(subtasks.get(needId));
        return subtasks.get(needId);

    }

    public Task getEpicById(int needId) {
        history.addHistoryTasks(epics.get(needId));

        return epics.get(needId);
    }

    public void removeTaskById(int needId) {
        simpleTasks.remove(needId);
    }

    public void removeEpicById(int needId) {
        epics.remove(needId);
    }

    public void removeSubtaskById(int needId) {
        int epicId = subtasks.get(needId).getEpicId();
        subtasks.remove(needId);
        if (epics.containsValue(epicId)) {
            epics.get(epicId).getSubtaskId().remove(needId);
        }
    }

    public void updateTask(Task task) {
        simpleTasks.put(task.getId(), task);

    }

    public void updateEpic(Epic task) {
        epics.put(task.getId(), task);
        updateEpicStatus(task.getId());
    }

    public void updateSubtask(Subtask task) {
        subtasks.put(task.getId(), task);
        updateEpicStatus(task.getEpicId());
    }

    public List<Integer> subtaskByEpic(int epicId) {
        return epics.get(epicId).getSubtaskId();
    }






}