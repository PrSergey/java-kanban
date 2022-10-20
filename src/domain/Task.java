package domain;

import java.util.Objects;

public class Task {


    protected String title;
    protected String description;

    protected int id;



    protected Status status;
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
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