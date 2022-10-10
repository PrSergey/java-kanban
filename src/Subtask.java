public class Subtask extends Task {

    protected int epicId;


    public Subtask(String title, String description, int epicId, String status) {
        super(title, description);
        this.epicId = epicId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}