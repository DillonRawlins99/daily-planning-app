package planning.app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import planning.app.controller.DailyPageController;
import planning.app.controller.EditDialogController;
import planning.app.controller.RootLayoutController;
import planning.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.prefs.Preferences;

/**
 * Planning JavaFX Application
 * @author Dillon
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    private DailyPage dailyPage;

    public MainApp() {
        dailyPage = new DailyPage();
        dailyPage.setPageDate(LocalDate.now());
		/*ObservableList<Activity> aList = FXCollections.observableArrayList();
		Activity a = new Activity("Hiking", "", Duration.ofMinutes(60), LocalTime.now());
		Activity b = new Activity("Reading", "", Duration.ofMinutes(60), LocalTime.NOON);
		aList.addAll(a,b);
		ObservableList<Aim> aimList = FXCollections.observableArrayList();
		aimList.add(new Aim("Career", "Work on future outlook"));
		aimList.add(new Aim("Fitness", "Develop mobility and functional strength"));
		dailyPage.setActivities(new ActivityListWrapper(aList));
		dailyPage.setAims(new AimListWrapper(aimList));
		ObservableList<Task> taskList = FXCollections.observableArrayList();
		taskList.addAll(new Task("Vacuum Car Out","alsdfkj"), new Task("Talk to Larkin about Yard Work",""));
		dailyPage.setTasks(new TaskListWrapper(taskList));
		ObservableList<Win> winList = FXCollections.observableArrayList();
		winList.addAll(new Win("Hello", "alskdfjasjldf"), new Win("Helo","Blaksdfj"));
		dailyPage.setWins(new WinListWrapper(winList));*/
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        initRootLayout();
        showDailyPage();

		/*CalendarView calendarView = new CalendarView();

        Calendar birthdays = new Calendar("Birthdays");
        Calendar holidays = new Calendar("Holidays");

        birthdays.setStyle(Style.STYLE1);
        holidays.setStyle(Style.STYLE2);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(birthdays, holidays);

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
                @Override
                public void run() {
                        while (true) {
                                Platform.runLater(() -> {
                                        calendarView.setToday(LocalDate.now());
                                        calendarView.setTime(LocalTime.now());
                                });

                                try {
                                        // update every 10 seconds
                                        sleep(10000);
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }

                        }
                };
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        Scene scene = new Scene(calendarView);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        primaryStage.show();*/
    }

    /*
     * Initializes BorderPane root layout from FXML
     */
    private void initRootLayout() {
        try {
            // Load root layout from FXML File
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            //Sets controller
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Initializes base Daily Page layout
     */
    private void showDailyPage() {
        try {
            //Loads Daily Page layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DailyPage.fxml"));
            FlowPane dailyPage = (FlowPane) loader.load();

            //Adds Daily Page to root layout
            rootLayout.setCenter(dailyPage);

            //Give controller access to main app.
            DailyPageController controller = loader.getController();
            controller.setMainApp(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Refactor both show edit dialog methods to showEditDialog(Editable view) TODO
    //Use inheritance and potentially generics

    /**
     * Opens dialog to edit an editable object.
     * @param <T> Generic controller inheriting from an Editable controller
     * @param e Editable object
     * @return true if edit was successful, false otherwise
     */
    public <T extends EditDialogController> boolean showEditDialog(Editable e) {
        try {
            String className = e.getClass().getSimpleName();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/" + className + "EditDialog.fxml"));
            VBox editDialog = null;
            editDialog = (VBox) loader.load();
            //REFACTOR TODO CONSOLIDATE WITH ALL EDITING WINDOWS, Activity etc.
            //Create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit " + className);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(editDialog);
            dialogStage.setScene(scene);

            //Set the activity in the controller
            T controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setEditable(e);

            //Shows the dialog and waits until user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException error) {
            // TODO Auto-generated catch block
            error.printStackTrace();
            return false;
        }
    }

    /**
     * Loads daily page data from specified file. The current page displayed
     * will be replaced
     *
     * @param file
     */
    public void loadDailyPageDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(DailyPage.class);
            Unmarshaller um = context.createUnmarshaller();

            //Reading XML from file and unmarshalling
            um.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            DailyPage dailyPageWrapper = (DailyPage) um.unmarshal(file);

            //Sets dailyPage instance var to daily page loaded from file
            this.clearDailyPage();
            this.dailyPage = dailyPageWrapper;
            //this.dailyPage = new DailyPage(dailyPageWrapper);

            System.out.println(dailyPage);

            showDailyPage();

            //Saves file path to registry
            setDailyPageFilePath(file);
        } catch (Exception e) {
            System.out.println("Hellow");
            e.printStackTrace();
            //ErrorUtil.showErrorMessage();
        }
    }


    /**
     * Saves current Daily Page data to specified file
     */
    public void savePageDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(DailyPage.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Read XML

            //Marshall and save XML to file
            m.marshal(dailyPage, file);
        }
        catch (Exception e) {

        }
    }

    public DailyPage getDailyPage() {
        return dailyPage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getDailyPageFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is stored in
     * the OS registry
     *
     * @param file the file or null to remove the path
     */
    public void setDailyPageFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("PlanningApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("PlanningApp");
        }
    }

    public void clearDailyPage() {
        this.dailyPage = new DailyPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
