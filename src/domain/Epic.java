package domain;

import managers.TaskType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Epic extends Task {
    List<Integer> subtaskId = new ArrayList<>();


    public Epic(String title, String description) {
        super(title, description);

    }

    public Epic(int id, TaskType type, String title, Status status, String description) {
        super(id, type, title, status, description);
    }

    public List<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
            this.subtaskId = subtaskId;


    }

    @Override
    public String toString() {
        return "domain.Epic{" +
                "subtaskId=" + subtaskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return subtaskId.equals(epic.subtaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskId);
    }
}