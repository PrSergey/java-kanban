package domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Epic extends Task {
    List<Integer> subtaskId = new ArrayList<>();
    LocalDateTime endTimeEpic;


    public Epic(String title, String description, Status status, LocalDateTime startTime, Duration duration) {
        super(title, description, status, startTime, duration);
    }

    public Epic(int id, TaskType type, String title, Status status, String description) {
        super(id, type, title, status, description);
    }

    public Epic(int id, TaskType type, String title, Status status, String description, LocalDateTime startTime, Duration duration) {
        super(id, type, title, status, description, startTime, duration);
    }

    public List<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
            this.subtaskId = subtaskId;


    }


    public void setStartTime (LocalDateTime startTimeSubtask){
       if (startTime==null || startTimeSubtask.isBefore(startTime)){
           startTime=startTimeSubtask;
       }
    }

    @Override
    public LocalDateTime getEndTime(){
        LocalDateTime endTime = startTime.plusMinutes(duration.toMinutes());
        if(endTimeEpic==null){
            return endTime;
        }
        if(endTime.isAfter(endTimeEpic)){
            endTimeEpic=endTime;
        }
        return endTimeEpic;

    }

    @Override
    public Duration getDuration (){
        return duration=Duration.between(startTime, endTimeEpic);
    }

    public LocalDateTime setEndTime(LocalDateTime endTimeSubtask){
        if (endTimeEpic==null || endTimeSubtask.isAfter(endTimeEpic)){
            endTimeEpic=endTimeSubtask;
        }
        return endTimeEpic;

    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskId=" + subtaskId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", endTimeEpic=" + endTimeEpic +
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