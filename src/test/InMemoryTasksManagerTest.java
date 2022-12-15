package test;

import constant.Status;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {


    @Override
    InMemoryTaskManager getManager() {
        return new InMemoryTaskManager();
    }


    @Override
    public void addSubtaskWithDifferentStatus(Status StatusSubtask1, Status StatusSubtask2) {
        super.addSubtaskWithDifferentStatus(StatusSubtask1, StatusSubtask2);
    }

    @Override
    @Test
    public void shouldAddTaskInManager() {
        super.shouldAddTaskInManager();
    }

    @Override
    @Test
    public void shouldAddEpicInManager() {
        super.shouldAddEpicInManager();
    }

    @Override
    @Test
    public void shouldAddSubtaskInManager() {
        super.shouldAddSubtaskInManager();
    }

    @Override
    @Test
    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusNew() {
        super.shouldUpdateStatusEpicWhenAllSubTaskWithStatusNew();
    }

    @Override
    @Test
    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusDone() {
        super.shouldUpdateStatusEpicWhenAllSubTaskWithStatusDone();
    }

    @Override
    @Test
    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusDoneAndNew() {
        super.shouldUpdateStatusEpicWhenAllSubTaskWithStatusDoneAndNew();
    }

    @Override
    @Test
    public void shouldUpdateStatusEpicWhenAllSubTaskWithStatusInProgress() {
        super.shouldUpdateStatusEpicWhenAllSubTaskWithStatusInProgress();
    }

    @Override
    @Test
    public void shouldNotAddSubtaskWithoutEpic() {
        super.shouldNotAddSubtaskWithoutEpic();
    }

    @Override
    @Test
    public void shouldReturnAnEmptyListTasks() {
        super.shouldReturnAnEmptyListTasks();
    }

    @Override
    @Test
    public void shouldGetTasksWithTask() {
        super.shouldGetTasksWithTask();
    }

    @Override
    @Test
    public void shouldReturnAnEmptyListEpics() {
        super.shouldReturnAnEmptyListEpics();
    }

    @Override
    @Test
    public void shouldGetTaskWithEpics() {
        super.shouldGetTaskWithEpics();
    }

    @Override
    @Test
    public void shouldReturnAnEmptyListSubtasks() {
        super.shouldReturnAnEmptyListSubtasks();
    }

    @Override
    @Test
    public void shouldGetListWithSubtaskWithTwoTasks() {
        super.shouldGetListWithSubtaskWithTwoTasks();
    }

    @Override
    @Test
    public void shouldRemoveTasksListWithoutTask() {
        super.shouldRemoveTasksListWithoutTask();
    }

    @Override
    @Test
    public void shouldRemoveTasksWithoutEpic() {
        super.shouldRemoveTasksWithoutEpic();
    }

    @Override
    @Test
    public void shouldRemoveAllTasksWithoutSubtask() {
        super.shouldRemoveAllTasksWithoutSubtask();
    }

    @Override
    @Test
    public void shouldRemoveAllTasks() {
        super.shouldRemoveAllTasks();
    }

    @Override
    @Test
    public void shouldRemoveAllEpics() {
        super.shouldRemoveAllEpics();
    }

    @Override
    @Test
    public void shouldRemoveAllSubtasks() {
        super.shouldRemoveAllSubtasks();
    }

    @Override
    @Test
    public void shouldReturnNullWhenTasksIsEmpty() {
        super.shouldReturnNullWhenTasksIsEmpty();
    }

    @Override
    @Test
    public void shouldReturnNullWhenEpicsIsEmpty() {
        super.shouldReturnNullWhenEpicsIsEmpty();
    }

    @Override
    @Test
    public void shouldReturnNullWhenSubtasksIsEmpty() {
        super.shouldReturnNullWhenSubtasksIsEmpty();
    }

    @Override
    @Test
    public void shouldReturnTaskAfterAddTask() {
        super.shouldReturnTaskAfterAddTask();
    }

    @Override
    @Test
    public void shouldReturnTaskAfterAddEpic() {
        super.shouldReturnTaskAfterAddEpic();
    }

    @Override
    @Test
    public void shouldReturnTaskAfterAddSubtask() {
        super.shouldReturnTaskAfterAddSubtask();
    }

    @Override
    @Test
    public void shouldRemoveTaskByIdWithoutTask() {
        super.shouldRemoveTaskByIdWithoutTask();
    }

    @Override
    @Test
    public void shouldRemoveEpicsByIdWithoutTask() {
        super.shouldRemoveEpicsByIdWithoutTask();
    }

    @Override
    @Test
    public void shouldRemoveSubtaskByIdWithoutTask() {
        super.shouldRemoveSubtaskByIdWithoutTask();
    }

    @Override
    @Test
    public void shouldRemoveTaskById() {
        super.shouldRemoveTaskById();
    }

    @Override
    @Test
    public void shouldRemoveEpicById() {
        super.shouldRemoveEpicById();
    }

    @Override
    @Test
    public void shouldRemoveSubtaskById() {
        super.shouldRemoveSubtaskById();
    }

    @Override
    @Test
    public void shouldUpdateTaskWhenThisTaskIsNotThere() {
        super.shouldUpdateTaskWhenThisTaskIsNotThere();
    }

    @Override
    @Test
    public void shouldUpdateEpicWhenThisEpicIsNotThere() {
        super.shouldUpdateEpicWhenThisEpicIsNotThere();
    }

    @Override
    @Test
    public void shouldUpdateSubtaskWhenThisSubtaskIsNotThere() {
        super.shouldUpdateSubtaskWhenThisSubtaskIsNotThere();
    }

    @Override
    @Test
    public void shouldUpdateTask() {
        super.shouldUpdateTask();
    }

    @Override
    @Test
    public void shouldUpdateEpic() {
        super.shouldUpdateEpic();
    }

    @Override
    @Test
    public void shouldUpdateSubtask() {
        super.shouldUpdateSubtask();
    }

    @Override
    @Test
    public void shouldReturnListOfEmptySubtask() {
        super.shouldReturnListOfEmptySubtask();
    }

    @Override
    @Test
    public void shouldReturnNullWhenEpicIsNotThere() {
        super.shouldReturnNullWhenEpicIsNotThere();
    }

    @Override
    @Test
    public void shouldReturnListWithTwoSubtask() {
        super.shouldReturnListWithTwoSubtask();
    }

    @Override
    @Test
    public void shouldAddTaskInHistoryManager() {
        super.shouldAddTaskInHistoryManager();
    }
}

