package domain;

import managers.TaskType;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
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