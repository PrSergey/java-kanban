import domain.Epic;
import domain.Status;
import domain.Subtask;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Epic epic1 = new Epic("пробная", "эпик номер 1");
        manager.add(epic1);
        Subtask subtask2 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", epic1.getId(), Status.NEW);
        Subtask subtask3 = new Subtask("пробная подзадача 2", "подзадача для эпик 1", epic1.getId(), Status.DONE);
        manager.add(subtask2, epic1.getId());
        manager.add(subtask2, epic1.getId());
        manager.updateEpicStatus(1);

        Epic epic2 = new Epic("пробная", "эпик номер 2");
        manager.add(epic2);
        Subtask subtask5 = new Subtask("пробная подзадача 1", "подзадача для эпик 1", epic2.getId(), Status.DONE);
        manager.add(subtask5, epic2.getId());
        manager.updateEpicStatus( epic2.getId());
        Epic epic3 = new Epic("пробная", "эпик номер 3");
        manager.add(epic3);
        System.out.println(epic1.toString());
        System.out.println(epic2.toString());
        System.out.println(epic3.toString());
        System.out.println(subtask2.toString());
        System.out.println(subtask3.toString());
        System.out.println(subtask5.toString());

        System.out.println("Все эпики "+manager.getEpics() + "\n" +"Все подзадачи " + manager.getSubtasks());
        System.out.println(manager.subtaskByEpic(1));

        subtask2.setStatus(Status.DONE);
        manager.updateEpic(epic1);
        System.out.println("\n" + "Изменение статуса пода задачи и следовательно эпика" + "\n" + epic1.toString());
        System.out.println(subtask2.toString());

        /*manager.removeEpicById(1);
        manager.removeSubtaskById(3);
        System.out.println("\n"+"Удаление эпика №1 и подзадачи №3" + "\n" + manager.getEpics() + "\n" +
                manager.getSubtasks());

        manager.removeEpics();
        manager.removeTask();
        manager.removeSubtasks();
        System.out.println("\n"+"Удаление всех эпиков, подзадач и задач" + "\n" +"Все эпики "+ manager.getEpics() + "\n" +
                "Все подзадачи "+manager.getSubtasks()+ "\n" + "Все задачи "+manager.getSimpleTasks());
*/


        manager.getEpicById(4);

        manager.getEpicById(1);
        manager.getEpicById(1);
        manager.getEpicById(1);
        manager.getEpicById(1);
        manager.getEpicById(1);
        manager.getEpicById(1);
        manager.getEpicById(1);
        manager.getSubtaskById(3);
        manager.getSubtaskById(2);
       // manager.getSubtaskById(5);

        System.out.println("\n"+"Просмотр эпиков");
        System.out.println(manager.history.getHistoryTasks());
    }

}