package test;

import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;

public class EpicTestStatus extends TaskManagerTest<InMemoryTaskManager> {

    //Изначально провреку статуса эпика реализовал InMemoryTasksManagerTest. Сейчас перенес в отдельный тест


    @Override
    InMemoryTaskManager getManager() {
        return new InMemoryTaskManager();
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

}
