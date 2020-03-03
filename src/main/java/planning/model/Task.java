package planning.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task extends Editable {

    private boolean isCompleted;

    public Task() {
        super("", "");
    }

    public Task(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}
