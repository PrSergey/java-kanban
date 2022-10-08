public class SimpleTask extends Task {
    protected String status;

    public SimpleTask(String title, String description, String status) {
        super(title, description);
        this.status = status;
    }
}
