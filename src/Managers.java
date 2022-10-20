
public class Managers {

    private static TaskManager managerView;
    private static InMemoryHistoryManager historyManager;


    public static TaskManager getDefault(){
        return  managerView;
    }

    public static HistoryManager getDefaultHistory(){
        return  historyManager;
    }
}
