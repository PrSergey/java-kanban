package managers;

import domain.Epic;
import domain.Status;
import domain.Subtask;
import domain.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    File fileWithTasks;



    public FileBackedTasksManager(File fileWithTasks) {
        this.fileWithTasks = fileWithTasks;
        loadFromFile(fileWithTasks);
    }


    public void save() {
        try (BufferedWriter fileTask = new BufferedWriter(new FileWriter(fileWithTasks, StandardCharsets.UTF_8))) {
            fileTask.write("id,type,name,status,description,epic" + "\n");
            for (Integer taskId : tasks.keySet())
                fileTask.write(toString(tasks.get(taskId)));

            for (Integer taskId : epics.keySet())
                fileTask.write(toString(epics.get(taskId)));

            for (Integer taskId : subtasks.keySet())
                fileTask.write(toString(subtasks.get(taskId)));

            fileTask.write("\n");

            fileTask.write(historyToString(history));

        } catch (IOException e) {
            System.out.println("Ошибка записи");
        }catch (NullPointerException e){
            System.out.println("Нет задач");
        }

    }


    public static String historyToString(HistoryManager manager){
        StringBuilder tasksHistoryToString=new StringBuilder();
        List<Integer> historyId=manager.getHistoryId();
        for (Integer taskInHistory: historyId){
            tasksHistoryToString.append(taskInHistory).append(",");
        }
        return tasksHistoryToString.toString();
    }


    public void loadFromFile (File file){
        String path=file.getPath();
        String text=readFileContentsOrNull(path);
        if (text == null || text.isEmpty()){
            System.out.println("Файл с задачами пустой");
            return;
        }
        int taskId=0;
        String[] lines = text.split("\r?\n");
        for (int i = 1; i < (lines.length-1); i++) {
            if(!lines[i].isEmpty()) {
                int newTaskId=addTaskFromString(lines[i]);
                if (taskId<newTaskId){
                    taskId=newTaskId;
                }
            }
        }
            addTaskInHistoryFromString(lines[lines.length - 1]);

        id=++taskId;
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

    public void addTask(Task task){
        tasks.put(task.getId(), task);
    }
    public void addTask(Epic task){
        epics.put(task.getId(), task);
    }
    public void addTask(Subtask task){
        subtasks.put(task.getId(), task);
        int epicId=task.getEpicId();
        List<Integer> subtasksInEpic;
        subtasksInEpic=epics.get(epicId).getSubtaskId();
        subtasksInEpic.add(task.getId());
    }


    public int addTaskFromString(String value) {
        String[] taskAllInformation = value.split(",");

        //Task task = new Task(taskAllInformation[2], taskAllInformation[4]);

        int idTask = Integer.parseInt(taskAllInformation[0]);
        Status statusTask = definitionTaskStatus(taskAllInformation[3]);
        //task.setId(idTask);

        switch (taskAllInformation[1]) {
            case "TASK": {
                addTask(new Task(idTask, TaskType.TASK, taskAllInformation[2], statusTask, taskAllInformation[4]));
                break;
            }
            case "EPIC": {
                addTask(new Epic(idTask, TaskType.EPIC, taskAllInformation[2], statusTask, taskAllInformation[4]));
                break;
            }
            case "SUBTASK": {
                addTask(new Subtask(idTask, TaskType.SUBTASK, taskAllInformation[2], statusTask, taskAllInformation[4],
                        Integer.parseInt(taskAllInformation[5])));
                break;
            }
            default:
                System.out.println("Некорректные данные в файле в типе задачи");
                break;
        }
        return idTask;
    }



    public Status definitionTaskStatus(String taskStatus){
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



    public String toString(Task task){
            return task.getId() +
                    "," + task.getType() +
                    "," + task.getTitle() +
                    "," + task.getStatus() +
                    "," + task.getDescription() +
                    "\n";

    }

    public String toString(Epic task){
            return task.getId() +
                    "," + task.getType() +
                    "," + task.getTitle() +
                    "," + task.getStatus() +
                    "," + task.getDescription() +
                    "\n";
    }
    public String toString(Subtask task){
        return task.getId() +
                "," + task.getType() +
                "," + task.getTitle() +
                "," + task.getStatus() +
                "," + task.getDescription() +
                ","+task.getEpicId()+
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
    public void add(Subtask task, int epicId) {
        super.add(task,epicId);
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

    @Override
    public List<Task> getTasks() {
        List<Task> tasksList = new ArrayList<>();
        for (int id : tasks.keySet()) {
            tasksList.add(tasks.get(id));
            history.addHistoryTasks(tasks.get(id));
        }
        save();
        return tasksList;
    }

    public List<Task> getEpics() {
        List<Task> epicsList = new ArrayList<>();
        for (int id : epics.keySet()) {
            epicsList.add(epics.get(id));
            history.addHistoryTasks(epics.get(id));
        }
        save();
        return epicsList;
    }

    public List<Task> getSubtasks() {
        List<Task> subtasksList = new ArrayList<>();
        for (int id : subtasks.keySet()) {
            subtasksList.add(subtasks.get(id));
            history.addHistoryTasks(subtasks.get(id));
        }
        save();
        return subtasksList;
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
