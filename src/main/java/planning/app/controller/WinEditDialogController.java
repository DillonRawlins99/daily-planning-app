package planning.app.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import planning.model.Aim;
import planning.model.Editable;
import planning.model.Win;

public class WinEditDialogController extends EditDialogController {

    @FXML
    private JFXTextField winTitleField;

    @FXML
    private JFXTextArea winDescriptionField;

    /**
     * Sets win to be edited in dialog
     */
    public void setEditable(Editable win) {
        super.setEditable(win);

        winTitleField.setText(win.getEditableName());
        winDescriptionField.setText(win.getEditableDescription());
    }

    /**
     * Called when user clicks ok.
     */
    @FXML
    private void handleOk() {
        ((Win) editable).setEditableName(winTitleField.getText());
        ((Win) editable).setEditableDescription(winDescriptionField.getText());
        okClicked = true;
        dialogStage.close();
    }

}
