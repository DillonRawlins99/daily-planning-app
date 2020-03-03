package planning.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import planning.model.Task;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tasks")
public class TaskListWrapper {

    private ObservableList<Task> tasks;

    public TaskListWrapper() {
        this(FXCollections.observableArrayList());
    }

    public TaskListWrapper(ObservableList<Task> taskObservableList) {
        this.tasks = taskObservableList;
    }

    @XmlElement(name="task")
    public ObservableList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ObservableList<Task> tasks) {
        this.tasks = tasks;
    }
}
