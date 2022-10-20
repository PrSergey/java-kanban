package domain;

import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {
    List<Integer> subtaskId=new ArrayList<>();



    public Epic(String title, String description) {
        super(title, description);

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
}