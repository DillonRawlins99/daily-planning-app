package planning.app.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import planning.model.Task;

public class TaskListViewCellController extends ListCell<Task> {
    @FXML
    private Label taskLabel;

    @FXML
    private JFXCheckBox taskCompletedCheckBox;

    @Override
    protected void updateItem(Task task, boolean empty) {

    }
}
