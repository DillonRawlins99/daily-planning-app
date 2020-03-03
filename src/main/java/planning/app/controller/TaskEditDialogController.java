package planning.app.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import planning.model.Editable;
import planning.model.Win;
import planning.model.Task;

public class TaskEditDialogController extends EditDialogController {

    @FXML
    private JFXTextField taskTitleField;

    @FXML
    private JFXTextArea taskDescriptionField;

    /**
     * Sets win to be edited in dialog
     */
    public void setEditable(Editable task) {
        super.setEditable(task);

        taskTitleField.setText(task.getEditableName());
        taskDescriptionField.setText(task.getEditableDescription());
    }

    /**
     * Called when user clicks ok.
     */
    @FXML
    private void handleOk() {
        ((Task) editable).setEditableName(taskTitleField.getText());
        ((Task) editable).setEditableDescription(taskDescriptionField.getText());
        okClicked = true;
        dialogStage.close();
    }


}
