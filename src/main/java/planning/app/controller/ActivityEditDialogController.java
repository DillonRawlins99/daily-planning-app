package planning.app.controller;

import java.time.Duration;

import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import planning.model.Activity;
import planning.model.Editable;

/*
 * Dialog to edit details of an activity
 */
public class ActivityEditDialogController extends EditDialogController {
	
	@FXML
	private TextField activityNameField;
	
	@FXML
	private JFXTimePicker activityStartTimeField;
	
	@FXML
	private TextField activityDurationField;
	
    
	/*
	 * Sets activity to be edited in dialog
	 * 
	 * @param activity
	 */
	public void setEditable(Editable activity) {
		super.setEditable(activity);

		activityNameField.setText(activity.getEditableName());
		//Format TODO
		//activityStartTimeField.setText(DateTimeUtil.format(activity.getActivityStartTime()));
		activityStartTimeField.setValue(((Activity) activity).getActivityStartTime());
		//Format TODO
		activityDurationField.setText(String.valueOf(((Activity) activity).getActivityDuration().toMinutes()));
	}

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
    	((Activity) editable).setEditableName(activityNameField.getText());
    	((Activity) editable).setActivityStartTime(activityStartTimeField.getValue());
    	((Activity) editable).setActivityDuration(Duration.ofMinutes(Long.parseLong(activityDurationField.getText())));
    	okClicked = true;
    	dialogStage.close();
    }
}
