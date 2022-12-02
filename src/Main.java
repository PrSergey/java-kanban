import domain.Epic;
import domain.Status;
import domain.Subtask;
import domain.Task;
import managers.FileBackedTasksManager;
import managers.Managers;
import managers.TaskManager;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file=new File("task.csv");

        TaskManager manager = Managers.getDefaultFile(file);

//        manager.add(new Task("задача1", "описание"));
//        System.out.println(manager.getTasks());
//        System.out.println(manager.getEpics());
//        System.out.println(manager.getSubtasks());
//        System.out.println(manager.getHistory().getHistoryTasks());
//        FileBackedTasksManager taskManager2 = (FileBackedTasksManager) Managers.getDefaultFile(file);
//        taskManager2.loadFromFile(file);
//        System.out.println(taskManager2.getTasks());
//        System.out.println(taskManager2.getEpics());
//        System.out.println(taskManager2.getSubtasks());
//        System.out.println(taskManager2.getHistory().getHistoryTasks());



        Epic epic1 = new Epic("пробная", "эпик номер 1");
        manager.add(epic1);
        Subtask subtask2 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", epic1.getId(), Status.NEW);
        Subtask subtask3 = new Subtask("пробная подзадача 2", "подзадача для эпик 1", epic1.getId(), Status.NEW);
        manager.add(subtask2, epic1.getId());
        manager.add(subtask3, epic1.getId());
        manager.updateEpicStatus(1);

        Epic epic2 = new Epic("пробная", "эпик номер 2");
        manager.add(epic2);
        Subtask subtask5 = new Subtask("пробная подзадача 3", "подзадача для эпик 4", epic2.getId(), Status.DONE);
        manager.add(subtask5, epic2.getId());
        manager.updateEpicStatus(epic2.getId());
        Epic epic3 = new Epic("пробная", "эпик номер 3");
        manager.add(epic3);
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(epic3);
        System.out.println(subtask2);
        System.out.println(subtask3);
        System.out.println(subtask5);

        System.out.println("Все эпики " + manager.getEpics() + "\n" + "Все подзадачи " + manager.getSubtasks());
        System.out.println(manager.subtaskByEpic(1));

        subtask2.setStatus(Status.DONE);
        manager.updateEpic(epic1);
        System.out.println("\n" + "Изменение статуса подзадачи и следовательно эпика" + "\n" + epic1.toString());
        System.out.println(subtask2);






        System.out.println("Просмотр эпиков");
        manager.getEpicById(epic3.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask2.getId());
        manager.getEpicById(epic1.getId());
        manager.getSubtaskById(subtask3.getId());
        manager.getEpicById(epic2.getId());

        System.out.println(manager.getHistory().getHistoryTasks());


    }

}