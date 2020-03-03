package planning.app.error;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import planning.app.MainApp;
import planning.model.Editable;

public class ErrorUtil {

    //Could use instance context and pass in main app?

    public static void showErrorMessage(Editable e, Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(primaryStage);
        alert.setTitle("No Selection");
        String editableName = e.getClass().getSimpleName();
        alert.setHeaderText("No " + editableName +  " Selected");
        alert.setContentText("Please select an " + editableName + " in the table.");
        alert.showAndWait();
    }

}
