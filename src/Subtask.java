public class Subtask extends Task {

    protected int epicId;
    protected String status;

    public Subtask(String title, String description, int epicId, String status) {
        super(title, description);
        this.epicId = epicId;
        this.status = status;
    }
}