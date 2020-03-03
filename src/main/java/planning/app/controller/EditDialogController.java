package planning.app.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import planning.model.Editable;

public abstract class EditDialogController {
	
	protected Stage dialogStage;
	protected Editable editable;
	protected boolean okClicked = false;

	 /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;	
	}

	public <T extends Editable> void setEditable(T e) {
		this.editable = e;
	}
	
	/**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /*
     * Called when user clicks cancel.
     */
    @FXML
    private void handleCancel() {
    	dialogStage.close();
    }
}
