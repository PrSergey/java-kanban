package managers;

import domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.function.Predicate;
public class InMemoryTaskManager implements TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int id = 1;
    HistoryManager history = Managers.getDefaultHistory();

    @Override
    public HistoryManager getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        task.setId(id++);
        task.setType(TaskType.TASK);
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);

    }

    @Override
    public void add(Epic task) {
        task.setId(id++);
        task.setType(TaskType.EPIC);
        epics.put(task.getId(), task);
        task.setStatus(Status.NEW);
    }

    @Override
    public void add(Subtask task, int epicId) {
        task.setId(id++);
        task.setType(TaskType.SUBTASK);
        subtasks.put(task.getId(), task);
        epics.get(epicId).getSubtaskId().add(task.getId());
        updateEpicStatus(epicId);
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Status status;
        Epic epic = epics.get(epicId);
        List<Integer> subtasksOfTheTask = epic.getSubtaskId();
        if (subtasksOfTheTask.isEmpty()) {
            return;
        }
        status = subtasks.get(subtasksOfTheTask.get(0)).getStatus();


        for (Integer idSubtask : subtasksOfTheTask) {
            Status statusSubtask = subtasks.get(idSubtask).getStatus();
            if (!status.equals(statusSubtask)) {
                epic.setStatus(Status.IN_PROGRESS);
            } else if (status.equals(Status.DONE) && (statusSubtask).equals(Status.DONE)) {
                status = Status.DONE;
                epic.setStatus(status);
            } else {
                epic.setStatus(status);
            }

        }
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasksList = new ArrayList<>();
        for (int id : tasks.keySet()) {
            tasksList.add(tasks.get(id));
            history.addHistoryTasks(tasks.get(id));
        }
        return tasksList;
    }

    public List<Task> getEpics() {
        List<Task> epicsList = new ArrayList<>();
        for (int id : epics.keySet()) {
            epicsList.add(epics.get(id));
            history.addHistoryTasks(epics.get(id));
        }
        return epicsList;
    }

    public List<Task> getSubtasks() {
        List<Task> subtasksList = new ArrayList<>();
        for (int id : subtasks.keySet()) {
            subtasksList.add(subtasks.get(id));
            history.addHistoryTasks(subtasks.get(id));
        }
        return subtasksList;
    }

    public void removeTasks() {
        for (Integer taskId : tasks.keySet()) {
            history.remove(taskId);
        }
        tasks.clear();
    }


    public void removeEpics() {
        for (Integer taskId : epics.keySet()) {
            history.remove(taskId);
        }
        epics.clear();
    }

    public void removeSubtasks() {
        for (Integer taskId : subtasks.keySet()) {
            history.remove(taskId);
        }
        subtasks.clear();

    }

    public Task getTaskById(int needId) {
        history.addHistoryTasks(tasks.get(needId));
        return tasks.get(needId);
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
        tasks.remove(needId);
        history.remove(needId);
    }

    public void removeEpicById(int needId) {
        epics.remove(needId);
        history.remove(needId);
    }

    public void removeSubtaskById(int needId) {
        int epicId = subtasks.get(needId).getEpicId();
        subtasks.remove(needId);
        if (epics.containsValue(epicId)) {
            epics.get(epicId).getSubtaskId().remove(needId);
        }
        history.remove(needId);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);

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