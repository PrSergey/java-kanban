package managers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constant.Status;
import constant.TaskType;
import domain.Epic;
import domain.Subtask;
import domain.Task;
import server.KVTaskClient;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager {

    URI uri;
    KVTaskClient kvTaskClient;
    Gson gson;

    public HttpTaskManager(String pathFile) throws IOException, InterruptedException {
        super(pathFile);
        this.uri = URI.create(pathFile);
        this.kvTaskClient=new KVTaskClient(uri);
        gson=Managers.getGson();
        loadFromFile();
    }

    @Override
    public void save(){
        try {


            if (!tasks.isEmpty()) {
                String tasksText=gson.toJson(tasks);
                kvTaskClient.put("tasks", tasksText);
            }
            if (!epics.isEmpty()) {
                String epicsText=gson.toJson(epics);
                kvTaskClient.put("epics", epicsText);
            }
            if (!subtasks.isEmpty()) {

                String subtasksText=gson.toJson(subtasks);
                kvTaskClient.put("subtasks", subtasksText);

            }
            if (!history.getHistoryTasks().isEmpty()) {
                String historyText = gson.toJson(historyToString(history));
                kvTaskClient.put("history", historyText);
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromFile(){
        try {

            String taskInString=kvTaskClient.load("tasks");
            if (!taskInString.isBlank()) {
                tasks = gson.fromJson(taskInString, new TypeToken<Map<Integer, Task>>(){}.getType());
            }
            String epicInString=kvTaskClient.load("epics");
            if(!epicInString.isBlank()) {
                epics = gson.fromJson(epicInString, new TypeToken<Map<Integer, Epic>>(){}.getType());
            }
            String subtaskInString=kvTaskClient.load("subtasks");
            if (!subtaskInString.isBlank()) {
                subtasks = gson.fromJson(subtaskInString, new TypeToken<Map<Integer, Subtask>>(){}.getType());
            }
            String historyInString = kvTaskClient.load("history");
            if (!historyInString.isBlank()) {
                String historyTasksId=gson.fromJson(historyInString, String.class);
                addTaskInHistoryFromString(historyTasksId);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void addTask(Task task) {
        super.addTask(task);
    }

    @Override
    public void addTask(Epic task) {
        super.addTask(task);
    }

    @Override
    public void addTask(Subtask task) {
        super.addTask(task);
    }

    @Override
    public Status definitionTaskStatus(String taskStatus) {
        return super.definitionTaskStatus(taskStatus);
    }


    @Override
    public void add(Task task) {
        super.add(task);
    }

    @Override
    public void add(Epic task) {
        super.add(task);
    }

    @Override
    public void add(Subtask task) {
        super.add(task);
    }

    @Override
    public void updateEpicStatus(int epicId) {
        super.updateEpicStatus(epicId);
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
    }

    @Override
    public void removeEpics() {
        super.removeEpics();
    }

    @Override
    public void removeSubtasks() {
        super.removeSubtasks();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
    }

    @Override
    public Task getTaskById(int needId) {
        return super.getTaskById(needId);
    }

    @Override
    public Task getSubtaskById(int needId) {
        return super.getSubtaskById(needId);
    }

    @Override
    public Task getEpicById(int needId) {
        return super.getEpicById(needId);
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public void removeTaskById(int needId) {
        super.removeTaskById(needId);
    }

    @Override
    public void removeEpicById(int needId) {
        super.removeEpicById(needId);
    }

    @Override
    public void removeSubtaskById(int needId) {
        super.removeSubtaskById(needId);
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
        return super.getTasks();
    }

    @Override
    public List<Task> getEpics() {
        return super.getEpics();
    }

    @Override
    public List<Task> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public List<Task> creatureListWithTask(TaskType taskType, HashMap<Integer, ? extends Task> tasks) {
        return super.creatureListWithTask(taskType, tasks);
    }







}
