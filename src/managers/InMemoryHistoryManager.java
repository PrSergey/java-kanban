package managers;

import domain.Task;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    Node firstTask;
    Node lastTask;
    Map<Integer, Node> historyTasks = new HashMap<>();
    List<Task> sortedHistoryTask = new ArrayList<>();

    List<Integer> historyId = new ArrayList<>();


    public List<Task> getSortedHistoryTask() {
        return sortedHistoryTask;
    }

    public List<Integer> getHistoryId() {
        sortedHistoryId();
        return historyId;
    }

    public List<Integer> sortedHistoryId() {
        historyId.clear();
        Node next = firstTask.next;
        historyId.add(firstTask.data.getId());
        Node nextTask = firstTask.next;
        while (next != null) {
            historyId.add(nextTask.data.getId());
            next = nextTask.next;
            nextTask = nextTask.next;
        }
        return historyId;
    }

    @Override
    public List<Task> getHistoryTasks() {
        sortedHistoryTask.clear();
        try {
            Node next = firstTask.next;
            sortedHistoryTask.add(firstTask.data);
            Node nextTask = firstTask.next;
            while (next != null) {
                sortedHistoryTask.add(nextTask.data);
                next = nextTask.next;
                nextTask = nextTask.next;
            }
        }catch (NullPointerException e){
            System.out.println("Нет задач");
        }
        return sortedHistoryTask;
    }


    @Override
    public void addHistoryTasks(Task task) {

        if (task == null || lastTask != null && task == lastTask.data) {
            return;
        }
        int taskId = task.getId();
        if (historyTasks.containsKey(taskId)) {
            remove(taskId);
        }
        linkLast(task);
        historyTasks.put(taskId, lastTask);


    }


    @Override
    public void remove(int id) {
        Node removeTask = historyTasks.get(id);
        if (removeTask == null) {
            return;
        }
        if (removeTask.equals(firstTask)) {
            firstTask = firstTask.next;
            firstTask.prev = null;

        } else {
            removeTask.prev.next = removeTask.next;
            removeTask.next.prev = removeTask.prev;
        }
        historyTasks.remove(id);
    }


    public void linkLast(Task task) {
        Node newNode = new Node(lastTask, task, null);
        if (lastTask == null) {
            firstTask = newNode;
        } else {
            lastTask.next = newNode;
        }
        lastTask = newNode;

    }
//    @Override
//    public String toString() {
//       getHistoryTasks();
//        final StringBuilder sb = new StringBuilder("managers.InMemoryHistoryManager{");
//        sb.append("sortedTHistoryTask=").append(sortedHistoryTask);
//        sb.append('}');
//        return sb.toString();
//    }

    @Override
    public String toString() {
        getHistoryTasks();
        StringBuilder sb = new StringBuilder();
        int numberCallTask = 0;
        for (Task task : sortedHistoryTask) {
            numberCallTask++;
            sb.append(numberCallTask + " была вызвана задача с id: " + task.getId() +
                    "\n");

        }
        return sb.toString();
    }


    static class Node {
        Task data;
        Node next;
        Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}