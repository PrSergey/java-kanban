package managers;

import constant.Status;
import constant.TaskType;
import domain.*;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int id = 1;
    HistoryManager history = Managers.getDefaultHistory();

    Set<Task> prioritizedTasks = new TreeSet<>();

    @Override
    public HistoryManager getHistory() {
        return history;
    }

    @Override
    public void add(Task task) {
        if (!checkIntersectionByTime(task))
            return;

        task.setId(id++);
        task.setType(TaskType.TASK);
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);

    }

    @Override
    public void add(Epic task) {
        if (!checkIntersectionByTime(task))
            return;

        task.setId(id++);
        task.setType(TaskType.EPIC);
        task.setStatus(Status.NEW);
        epics.put(task.getId(), task);
        prioritizedTasks.add(task);

    }


    @Override
    public void add(Subtask task, int epicId) {
        if (!checkIntersectionByTime(task))
            return;

        task.setId(id++);
        task.setType(TaskType.SUBTASK);
        if (epics.get(epicId) == null) {
            return;
        }
        subtasks.put(task.getId(), task);
        Epic epicOfSubtask = epics.get(epicId);
        epicOfSubtask.setStartTime(task.getStartTime());
        epicOfSubtask.setEndTime(task.getEndTime());
        epicOfSubtask.getSubtaskId().add(task.getId());
        updateEpicStatus(epicId);
        prioritizedTasks.add(task);
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
            } else if (status.equals(Status.DONE)) {
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
        if (!subtasks.containsKey(needId)) {
            return;
        }
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
        if (!epics.containsKey(task.getEpicId())) {
            return;
        }
        subtasks.put(task.getId(), task);
        updateEpicStatus(task.getEpicId());
    }

    public List<Integer> getSubtaskByEpic(int epicId) {
        if (!epics.containsKey(epicId)) {
            return null;
        }
        return epics.get(epicId).getSubtaskId();
    }

    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public boolean checkIntersectionByTime(Task task) {
        boolean result = true;
        for (Task taskInMemory : prioritizedTasks) {
            if ((task.getStartTime().isBefore(taskInMemory.getStartTime())
                    || task.getStartTime().isEqual(taskInMemory.getStartTime()))
                    && task.getEndTime().isAfter(taskInMemory.getStartTime())) {
                result = false;
                break;
            } else if (task.getStartTime().isAfter(taskInMemory.getStartTime()) && task.getEndTime().isBefore(taskInMemory.getEndTime())) {
                result = false;
                break;
            } else if (task.getStartTime().isBefore(taskInMemory.getEndTime())
                    && (task.getEndTime().isAfter(taskInMemory.getEndTime())
                    || task.getEndTime().isEqual(taskInMemory.getEndTime()))) {
                result = false;
                break;
            } else if (task.getStartTime().isEqual(taskInMemory.getStartTime())
                    && task.getEndTime().isEqual(taskInMemory.getEndTime())) {
                result = false;
                break;
            }

        }
        return result;
    }

}