import domain.Epic;
import domain.Status;
import domain.Subtask;
import domain.Task;
import managers.FileBackedTasksManager;
import managers.Managers;
import managers.TaskManager;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        File file = new File("task.csv");

        TaskManager manager = Managers.getDefaultMemory();



        LocalDateTime timeTask1= LocalDateTime.of(2022, 11, 14,16,15);
        LocalDateTime timeTask2= LocalDateTime.of(2022, 11, 14,15,14);
        LocalDateTime timeTask3= LocalDateTime.of(2022, 11, 14,19,14);
        Task taskWithTime1=new Task("пробная", "эпик номер 1", Status.NEW, timeTask1, Duration.ofMinutes(63));

        manager.add(taskWithTime1);

//        System.out.println( "Начало таски: " +  manager.getTasks().get(0).getStartTime().toString()+
//                                "\n"+"Duration: "+ manager.getTasks().get(0).getDuration().toString()+
//                                    "\n"+"Endtime: "+manager.getTasks().get(0).getEndTime().toString());

        Epic epic1WithTime1 = new Epic("пробная", "эпик номер 1", Status.NEW, timeTask2, Duration.ofMinutes(59));
        manager.add(epic1WithTime1);
        Subtask subtask2WithTime1 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", Status.NEW,
                timeTask1, Duration.ofMinutes(50), epic1WithTime1.getId());
        manager.add(subtask2WithTime1, epic1WithTime1.getId());
        Subtask subtask3WithTime1 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", Status.NEW,
                timeTask3, Duration.ofMinutes(63), epic1WithTime1.getId());
        manager.add(subtask3WithTime1, epic1WithTime1.getId());


//        System.out.println( "Начало таски: " +  manager.getEpics().get(0).getStartTime().toString()+
//                "\n"+"Duration: "+ manager.getEpics().get(0).getDuration().toString()+
//                "\n"+"Endtime: "+manager.getEpics().get(0).getEndTime().toString());
//
//
//        System.out.println( "Начало таски: " +  manager.getEpics().get(0).getStartTime().toString()+
//                "\n"+"Duration: "+ manager.getEpics().get(0).getDuration().toString()+
//                "\n"+"Endtime: "+manager.getEpics().get(0).getEndTime().toString());
//
//        System.out.println( "Начало таски: " +  manager.getSubtasks().get(0).getStartTime().toString()+
//                "\n"+"Duration: "+ manager.getSubtasks().get(0).getDuration().toString()+
//                "\n"+"Endtime: "+manager.getSubtasks().get(0).getEndTime().toString());
        System.out.println("Сортировка по времени "+manager.getPrioritizedTasks());

//        Subtask subtask2 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", epic1.getId(), Status.NEW);
//        Subtask subtask3 = new Subtask("пробная подзадача 2", "подзадача для эпик 1", epic1.getId(), Status.NEW);
//        manager.add(subtask2, epic1.getId());
//        manager.add(subtask3, epic1.getId());
//        manager.updateEpicStatus(1);
//        Epic epic2 = new Epic("пробная", "эпик номер 2");
//        manager.add(epic2);
//        Subtask subtask5 = new Subtask("пробная подзадача 3", "подзадача для эпик 4", epic2.getId(), Status.DONE);
//        manager.add(subtask5, epic2.getId());
//        manager.updateEpicStatus(epic2.getId());
//        Epic epic3 = new Epic("пробная", "эпик номер 3");
//        manager.add(epic3);
//        System.out.println(epic1);
//        System.out.println(epic2);
//        System.out.println(epic3);
//        System.out.println(subtask2);
//        System.out.println(subtask3);
//        System.out.println(subtask5);
//        System.out.println("Все эпики " + manager.getEpics() + "\n" + "Все подзадачи " + manager.getSubtasks());
//        System.out.println(manager.subtaskByEpic(1));
//        subtask2.setStatus(Status.DONE);
//        manager.updateEpic(epic1);
//        System.out.println("\n" + "Изменение статуса подзадачи и следовательно эпика" + "\n" + epic1.toString());
//        System.out.println(subtask2);
//        System.out.println("Просмотр эпиков");
//        manager.getEpicById(epic3.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getSubtaskById(subtask2.getId());
//        manager.getEpicById(epic1.getId());
//        manager.getSubtaskById(subtask3.getId());
//        manager.getEpicById(epic2.getId());
//        System.out.println("Вывод на экран истории просмотров: "+ manager.getHistory().getHistoryTasks());


    }

}