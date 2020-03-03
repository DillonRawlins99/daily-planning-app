package planning.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import planning.app.MainApp;

import java.io.File;

/**
 * The controller for the root layout.
 */
public class RootLayoutController {

    //Reference to the main application
    private MainApp mainApp;

    /**
     * Called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates a blank Daily Page.
     */
    @FXML
    private void handleNew() {
        mainApp.clearDailyPage();
        mainApp.setDailyPageFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select a Daily Page to load
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadDailyPageDataFromFile(file);
        }
    }

    /**
     * Saves the file to the Daily Page file that is currently open.
     * If there's no open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        File dailyPageFile = mainApp.getDailyPageFilePath();
        if (dailyPageFile != null) {
            mainApp.savePageDataToFile(dailyPageFile);
        }
        else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChoser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePageDataToFile(file);
        }
    }

    /**
     * Opens an about dialog
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("PlanningApp");
        alert.setHeaderText("About");
        alert.setContentText("Author: Dillon Rawlins \n");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
