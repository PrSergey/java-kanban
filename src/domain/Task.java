package domain;

import managers.TaskType;

import java.util.Objects;

public class Task {


    protected String title;
    protected String description;

    protected int id;


    protected Status status;
    protected TaskType type;
    public Task( int id, TaskType type, String title, Status status, String description) {

        this.title = title;
        this.description = description;
        this.id = id;
        this.status = status;
        this.type = type;
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }
    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && description.equals(task.description) && status.equals(task.status);
    }


    public int hashCode() {
        return Objects.hash(title, description, id, status);
    }

    public String toString() {
        return "domain.Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}