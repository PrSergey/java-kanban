import domain.Task;

import java.util.HashMap;

import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    Node firstTask;
    Node lastTask;
    Map<Integer, Node> historyTasks = new HashMap<>();

    @Override

    public Map<Integer, Node> getHistoryTasks() {
        return historyTasks;
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
        if (removeTask==null){
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