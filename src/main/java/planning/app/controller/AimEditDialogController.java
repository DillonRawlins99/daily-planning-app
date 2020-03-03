package planning.app.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import planning.model.Activity;
import planning.model.Aim;
import planning.model.Editable;

public class AimEditDialogController extends EditDialogController {
	
	@FXML
	private JFXTextField aimNameField;
	
	@FXML
	private JFXTextArea aimDescriptionField;
	
	/**
	 * Sets aim to be edited in dialog
	 */
	public void setEditable(Editable aim) {
		super.setEditable(aim);
		
		aimNameField.setText(aim.getEditableName());
		aimDescriptionField.setText(aim.getEditableDescription());
	}
	
	/**
	 * Called when user clicks ok.
	 */
	@FXML
	private void handleOk() {
		((Aim) editable).setEditableName(aimNameField.getText());
		((Aim) editable).setEditableDescription(aimDescriptionField.getText());
		okClicked = true;
		dialogStage.close();
	}
}
