package managers;

import domain.Epic;
import domain.Status;
import domain.Subtask;
import domain.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

//            for (int i = 0; i < history.getSortedHistoryTask().size(); i++) {
//                Task task = history.getSortedHistoryTask().get(i);
//                int idTask = task.getId();
//                String id = Integer.toString(idTask);
//                fileTask.write(id + ",");
//            }
            fileTask.write(historyToString(history));

        } catch (IOException e) {
            System.out.println("Ошибка записи");
        }catch (NullPointerException e){
            System.out.println("Нет задач");
        }

    }


    public static String historyToString(HistoryManager manager){
        String tasksHistoryToString="";
        List<Integer> historyId=manager.getHistoryId();
        for (Integer taskInHistory: historyId){
            String taskIdInHistory=Integer.toString(taskInHistory);
            tasksHistoryToString+=taskIdInHistory+",";
        }
        return tasksHistoryToString;
    }


    public void loadFromFile (File file){
        String path=file.getPath();
        String text=readFileContentsOrNull(path);
        if (text.isEmpty()){
            System.out.println("Файл с задачами пустой");
            return;
        }
        int taskId=0;
        String[] lines = text.split("\r?\n");
        for (int i = 1; i < (lines.length-1); i++) {
            if(!lines[i].isEmpty()) {
                addTaskFromString(lines[i]);
                if (taskId<addTaskFromString(lines[i])){
                    taskId=addTaskFromString(lines[i]);
                }
            }
        }
        addTaskInHistoryFromString(lines[lines.length-1]);
        id=++taskId;
    }

    public void addTaskInHistoryFromString(String line) {

        String[] tasksHistory=line.split(",");

        for (String elTasksHistory: tasksHistory){
            int taskId=Integer.parseInt(elTasksHistory);
            if (tasks.containsKey(taskId)){
                history.addHistoryTasks(tasks.get(taskId));
            }else if(epics.containsKey(taskId)){
                history.addHistoryTasks(epics.get(taskId));
            } else if (subtasks.containsKey(taskId)) {
                history.addHistoryTasks(subtasks.get(taskId));
            }else{
                System.out.println("Задачи не найдено");
            }
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

        Task task = new Task(taskAllInformation[2], taskAllInformation[4]);

        int idTask = Integer.parseInt(taskAllInformation[0]);
        task.setId(idTask);

        if (taskAllInformation[1].equals("TASK")) {
            Task newTask=createTask(taskAllInformation, idTask);
            addTask(newTask);
        } else if (taskAllInformation[1].equals("EPIC")) {
            Epic newTask=createEpic(taskAllInformation, idTask);
            addTask(newTask);
        } else if (taskAllInformation[1].equals("SUBTASK"))  {
            Subtask newTask=createSubtask(taskAllInformation, idTask);
            addTask(newTask);
        }else {
            System.out.println("Некорректные данные в файле в типе задачи");

        }
        return idTask;
    }



    public Status definitionTaskStatus(String taskStatus){
        if (taskStatus.equals("NEW")) {
            return Status.NEW;
        } else if (taskStatus.equals("DONE")) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    public Task createTask (String[] taskInString, int id){
        Status statusEpic=definitionTaskStatus(taskInString[3]);
        Task task=new Task(id, TaskType.TASK, taskInString[2], statusEpic, taskInString[4]);
        return task;
    }

    public Epic createEpic (String[] taskInString, int id){
        Status statusEpic=definitionTaskStatus(taskInString[3]);
        Epic epic=new Epic(id, TaskType.EPIC, taskInString[2], statusEpic, taskInString[4]);
        return epic;
    }

    public Subtask createSubtask (String[] taskInString, int id){
        Status statusEpic=definitionTaskStatus(taskInString[3]);
        int subtaskForEpic=Integer.parseInt(taskInString[5]);
        Subtask subtask=new Subtask(id, TaskType.SUBTASK, taskInString[2], statusEpic, taskInString[4], subtaskForEpic);
        return subtask;
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


    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

}
