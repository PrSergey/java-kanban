package domain;

import constant.Status;
import constant.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String title, String description, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(int id, TaskType type, String title, Status status, String description, int epicId) {
        super(id, type, title, status, description);
        this.epicId=epicId;
    }

    public Subtask(int id, TaskType type, String title, Status status, String description, LocalDateTime startTime, Duration duration, int epicId) {
        super(id, type, title, status, description, startTime, duration);
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
        return "Subtask{" +
                "epicId=" + epicId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}