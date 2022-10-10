import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    HashMap<Integer, Task> simpleTasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    int id = 1;

    public void add(Task taskIn) {
        taskIn.setId(id++);
        simpleTasks.put(taskIn.getId(), taskIn);

    }

    public void add(Epic taskIn) {
        taskIn.setId(id++);
        epics.put(taskIn.getId(), taskIn);
        taskIn.status = "NEW";
    }

    public void add(Subtask taskIn, int epicId) {
        taskIn.setId(id++);
        subtasks.put(taskIn.getId(), taskIn);
        epics.get(epicId).subtaskId.add(taskIn.getId());
        statusEpic(epicId);
    }

    public void statusEpic(int epicId) {
        String status="";
        if (epics.get(epicId).subtaskId.isEmpty()){
            status="NEW";
            return;
        }
        status=subtasks.get(epics.get(epicId).subtaskId.get(0)).status;

        for (Integer idSubtask: epics.get(epicId).subtaskId){
            if (!status.equals(subtasks.get(idSubtask).status)){
                epics.get(epicId).status="IN_PROGRESS";
            }else{
                epics.get(epicId).status= subtasks.get(epics.get(epicId).subtaskId.get(0)).status;
            }
        }
    }


    public ArrayList getSimpleTasks() {
        ArrayList simpleTasksList = new ArrayList<>();
        for (int id : simpleTasks.keySet()) {
            simpleTasksList.add(simpleTasks.get(id));
        }
        return simpleTasksList;
    }

    public ArrayList getEpics() {
        ArrayList epicsList = new ArrayList<>();
        for (int id : epics.keySet()) {
            epicsList.add(epics.get(id));
        }
        return epicsList;
    }

    public ArrayList getSubtasks() {
        ArrayList subtasksList = new ArrayList<>();
        for (int id : subtasks.keySet()) {
            subtasksList.add(subtasks.get(id));

        }
        return subtasksList;
    }

    public void removeTask() {
        simpleTasks.clear();
    }

    public void removeEpics() {
        epics.clear();
    }

    public void removeSubtasks() {
        subtasks.clear();

    }

    public Task getTaskById(int needId) {
        return simpleTasks.get(needId);
    }

    public Task getSubtaskById(int needId) {
        return subtasks.get(needId);

    }

    public Task getEpicById(int needId) {
        return epics.get(needId);
    }

    public void removeTaskById(int needId) {
        simpleTasks.remove(needId);
    }

    public void removeEpicById(int needId) {
        epics.remove(needId);
    }

    public void removeSubtaskById(int needId) {
        int epicId = subtasks.get(needId).epicId;
        subtasks.remove(needId);
        if (epics.containsValue(epicId)){
            epics.get(epicId).subtaskId.remove(needId);
        }
    }

    public void updateTask(Task task) {
        simpleTasks.put(task.id, task);

    }

    public void updateEpic(Epic task) {
        epics.put(task.id, task);
        statusEpic(task.id);
    }

    public void updateSubtask(Subtask task) {
        subtasks.put(task.id, task);
        statusEpic(task.epicId);
    }

    public ArrayList<Integer> sybtaskByEpic(int epicId) {
        return epics.get(epicId).subtaskId;
    }

}







