package managers;

import java.io.File;

public class Managers {

    public static TaskManager getDefaultMemory() {
        return new InMemoryTaskManager();
    }
    public static TaskManager getDefaultFile(File fileWithTasks) {

        return new FileBackedTasksManager(fileWithTasks);
    }


    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
