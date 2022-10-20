package domain;

public class Subtask extends Task {

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    protected int epicId;



    public Subtask(String title, String description, int epicId, Status status) {
        super(title, description);
        this.epicId = epicId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "domain.Subtask{" +
                "epicId=" + epicId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}