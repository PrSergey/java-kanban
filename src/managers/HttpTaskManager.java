package managers;

import com.google.gson.Gson;
import server.KVTaskClient;

import java.io.IOException;
import java.net.URI;

public class HttpTaskManager extends FileBackedTasksManager {

    URI uri;
    KVTaskClient kvTaskClient;
    Gson gson;

    public HttpTaskManager(String pathFile) throws IOException, InterruptedException {
        super(pathFile);
        this.uri = URI.create(pathFile);
        KVTaskClient kvTaskClient=new KVTaskClient(uri);
        gson=Managers.getGson();
        loadFromFile();
    }

    @Override
    public void save(){
        try {

            if (!tasks.isEmpty()) {
                String tasksText=gson.toJson(getTasks());

                kvTaskClient.put("tasks", tasksText);
            }
            if (!epics.isEmpty()) {
                String epicsText=gson.toJson(getEpics());
                kvTaskClient.put("epics", epicsText);
            }
            if (!subtasks.isEmpty()) {
                String subtasksText=gson.toJson(getSubtasks());
                kvTaskClient.put("subtasks", subtasksText);

            }
            if (!history.getHistoryTasks().isEmpty()) {
                String historyText = gson.toJson(history.getHistoryTasks());
                kvTaskClient.put("history", historyText);
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void loadFromFile(){
        try {
            kvTaskClient.load("tasks");
            kvTaskClient.load("epics");
            kvTaskClient.load("subtasks");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ex){
            System.out.println("Задачи в базе отсутсвуют");
        }
    }



}
