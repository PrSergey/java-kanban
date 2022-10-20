import java.util.HashMap;

public class Epic extends Task {

    HashMap<Integer, String> subtaskId = new HashMap<>();
    protected String status;

    public Epic(String title, String description) {
        super(title, description);
        this.subtaskId = subtaskId;
    }
}
