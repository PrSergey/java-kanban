package domain;

import managers.TaskType;

public class Subtask extends Task {



    protected int epicId;


    public Subtask(String title, String description, int epicId, Status status) {
        super(title, description);
        this.epicId = epicId;
        this.status = status;
    }
    public Subtask(int id, TaskType type, String title, Status status, String description, int epicId) {
        super(id, type, title, status, description);
        this.epicId = epicId;
    }
    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
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