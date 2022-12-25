package managers;

import constant.Status;
import constant.TaskType;
import domain.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FileBackedTasksManager extends InMemoryTaskManager {

    File fileWithTasks;

    public FileBackedTasksManager(String pathFile) {
    }

    public FileBackedTasksManager(File fileWithTasks) {
        this.fileWithTasks = fileWithTasks;
        loadFromFile(fileWithTasks);
    }


    public void save() {
        try (BufferedWriter fileTask = new BufferedWriter(new FileWriter(fileWithTasks, StandardCharsets.UTF_8))) {
            fileTask.write("id,type,name,status,description,startTime,duration" + "\n");
            if (!tasks.isEmpty()) {
                for (Integer taskId : tasks.keySet())
                    fileTask.write(toString(tasks.get(taskId)));
            }
            if (!epics.isEmpty()) {
                for (Integer taskId : epics.keySet()) {
                    fileTask.write(toString(epics.get(taskId)));
                }
            }
            if (!subtasks.isEmpty()) {
                for (Integer taskId : subtasks.keySet())
                    fileTask.write(toString(subtasks.get(taskId)));
            }
            fileTask.write("\n");

            fileTask.write(historyToString(history));

        } catch (IOException e) {
            System.out.println("Ошибка записи");
        }

    }


    public static String historyToString(HistoryManager manager) {
        StringBuilder tasksHistoryToString = new StringBuilder();
        List<Integer> historyId = manager.getHistoryId();
        for (Integer taskInHistory : historyId) {
            tasksHistoryToString.append(taskInHistory).append(",");
        }
        return tasksHistoryToString.toString();
    }


    public void loadFromFile(File file) {
        String path = file.getPath();
        String text = readFileContentsOrNull(path);

        if (text == null || text.isEmpty()) {
            System.out.println("Файл с задачами пустой");
            return;
        }

        int taskId = 0;
        String[] lines = text.split("\r?\n");
        for (int i = 1; i < (lines.length-1); i++) {
            if (!lines[i].isEmpty()) {
                int newTaskId = addTaskFromString(lines[i]);
                if (taskId < newTaskId) {
                    taskId = newTaskId;
                }
            }
        }

        if(!lines[lines.length - 1].isEmpty() && lines.length!=1){
            addTaskInHistoryFromString(lines[lines.length - 1]);
        }
        id = ++taskId;




    }

    public void addTaskInHistoryFromString(String line) {

        try {
            String[] tasksHistory = line.split(",");

            for (String elTasksHistory : tasksHistory) {
                int taskId = Integer.parseInt(elTasksHistory);
                if (tasks.containsKey(taskId)) {
                    history.addHistoryTasks(tasks.get(taskId));
                } else if (epics.containsKey(taskId)) {
                    history.addHistoryTasks(epics.get(taskId));
                } else if (subtasks.containsKey(taskId)) {
                    history.addHistoryTasks(subtasks.get(taskId));
                } else {
                    System.out.println("Задачи не найдены");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("История задач в файле пустая");
        }

    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
    }

    public void addTask(Epic task) {
        epics.put(task.getId(), task);
        prioritizedTasks.add(task);
    }

    public void addTask(Subtask task) {
        subtasks.put(task.getId(), task);
        int epicId = task.getEpicId();
        epics.get(epicId).getSubtaskId().add(task.getId());
        Epic epicOfSubtask = epics.get(epicId);
        epicOfSubtask.setStartTime(task.getStartTime());
        epicOfSubtask.setEndTime(task.getEndTime());
        prioritizedTasks.add(task);
    }


    public int addTaskFromString(String value) {
        String[] taskAllInformation = value.split(",");

        int idTask = Integer.parseInt(taskAllInformation[0]);
        Status statusTask = definitionTaskStatus(taskAllInformation[3]);
        LocalDateTime startTime = transformationToLocalDateTime(taskAllInformation[5]);
        long minutesDuration = Long.parseLong(taskAllInformation[6]);

        switch (taskAllInformation[1]) {
            case "TASK": {
                addTask(new Task(idTask, TaskType.TASK, taskAllInformation[2], statusTask, taskAllInformation[4],
                        startTime, Duration.ofMinutes(minutesDuration)));
                break;
            }
            case "EPIC": {
                addTask(new Epic(idTask, TaskType.EPIC, taskAllInformation[2], statusTask, taskAllInformation[4],
                        startTime, Duration.ofMinutes(minutesDuration)));
                break;
            }
            case "SUBTASK": {
                addTask(new Subtask(idTask, TaskType.SUBTASK, taskAllInformation[2], statusTask, taskAllInformation[4],
                        startTime, Duration.ofMinutes(minutesDuration),
                        Integer.parseInt(taskAllInformation[7])));
                break;
            }
            default:
                System.out.println("Некорректные данные в файле в типе задачи");
                break;
        }
        return idTask;
    }

    private LocalDateTime transformationToLocalDateTime(String startTimeString) {
        DateTimeFormatter startTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(startTimeString, startTimeFormatter);
    }


    public Status definitionTaskStatus(String taskStatus) {
        switch (taskStatus) {
            case "NEW":
                return Status.NEW;
            case "DONE":
                return Status.DONE;
            case "IN_PROGRESS":
                return Status.IN_PROGRESS;
            default:
                return null;
        }
    }


    public String toString(Task task) {
        return task.getId() +
                "," + task.getType() +
                "," + task.getTitle() +
                "," + task.getStatus() +
                "," + task.getDescription() +
                "," + task.getStartTime() +
                "," + task.getDuration().toMinutes() +
                "\n";

    }

    public String toString(Epic task) {
        return task.getId() +
                "," + task.getType() +
                "," + task.getTitle() +
                "," + task.getStatus() +
                "," + task.getDescription()+
                "," + task.getStartTime()+
                "," + task.getDuration().toMinutes()+
                "\n";
    }

    public String toString(Subtask task) {
        return task.getId() +
                "," + task.getType() +
                "," + task.getTitle() +
                "," + task.getStatus() +
                "," + task.getDescription() +
                "," + task.getStartTime() +
                "," + task.getDuration().toMinutes() +
                "," + task.getEpicId() +
                "\n";
    }


    @Override
    public void add(Task task) {
        super.add(task);
        save();

    }

    @Override
    public void add(Epic task) {
        super.add(task);
        save();
    }

    @Override
    public void add(Subtask task) {
        super.add(task);
        save();
    }

    public void updateEpicStatus(int epicId) {
        super.updateEpicStatus(epicId);
        save();
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
        save();
    }

    @Override
    public void removeEpics() {
        super.removeEpics();
        save();
    }

    @Override
    public void removeSubtasks() {
        super.removeSubtasks();
        save();

    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public Task getTaskById(int needId) {
        history.addHistoryTasks(tasks.get(needId));
        save();
        return tasks.get(needId);
    }

    @Override
    public Task getSubtaskById(int needId) {
        history.addHistoryTasks(subtasks.get(needId));
        save();
        return subtasks.get(needId);

    }

    @Override
    public Task getEpicById(int needId) {
        history.addHistoryTasks(epics.get(needId));
        save();
        return epics.get(needId);
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasksList = new ArrayList<>();
        for (int id : tasks.keySet()) {
            tasksList.add(tasks.get(id));
            history.addHistoryTasks(tasks.get(id));
        }
        for (int id : epics.keySet()) {
            tasksList.add(epics.get(id));
            history.addHistoryTasks(epics.get(id));
        }
        for (int id : subtasks.keySet()) {
            tasksList.add(subtasks.get(id));
            history.addHistoryTasks(subtasks.get(id));
        }
        save();
        return tasksList;
    }

    @Override
    public void removeTaskById(int needId) {
        super.removeTaskById(needId);
        save();
    }

    @Override
    public void removeEpicById(int needId) {
        super.removeEpicById(needId);
        save();
    }

    @Override
    public void removeSubtaskById(int needId) {
        super.removeSubtaskById(needId);
        save();
    }


    @Override
    public void updateEpic(Epic task) {
        super.updateEpic(task);
    }

    @Override
    public void updateSubtask(Subtask task) {
        super.updateSubtask(task);
    }

    public List<Task> getTasks() {
        return creatureListWithTask(TaskType.TASK, tasks);
    }
    @Override
    public List<Task> getEpics() {
        return creatureListWithTask(TaskType.EPIC, epics);
    }
    @Override
    public List<Task> getSubtasks() {
        return creatureListWithTask(TaskType.SUBTASK, subtasks);
    }
    public List<Task> creatureListWithTask(TaskType taskType, HashMap<Integer, ? extends Task> tasks) {
        List<Task> tasksList = new ArrayList<>();
        for (int id : tasks.keySet()) {
            tasksList.add(tasks.get(id));
            history.addHistoryTasks(tasks.get(id));
        }
        save();
        return tasksList;
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

}
