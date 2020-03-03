package planning.app.controller;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.AllDayView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DetailedDayView;
import com.calendarfx.view.VirtualGrid;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import planning.app.MainApp;
import planning.app.error.ErrorUtil;
import planning.model.*;
import planning.util.DateTimeUtil;
import planning.util.QuoteUtil;

import javafx.event.ActionEvent;

import com.calendarfx.model.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.*;
import java.util.Arrays;

public class DailyPageController {

	TreeItem<String> root;
	
	@FXML
	TreeView<String> aimTree;
	
	@FXML
	Text dateText;
	
	@FXML
	Text quoteText;
	
	@FXML
	public JFXListView<Task> taskListView;
	
	@FXML
	JFXListView<Win> winListView;

	@FXML
	DetailedDayView dayViewSchedule;

	@FXML
	JFXDatePicker datePicker;

	Calendar activityCalendar;

	CalendarSource calendarSource;
	
	//Reference to main application
	private MainApp mainApp;
	
	/*
	 * Constructor
	 * Called before initialize() method
	 */
	@SuppressWarnings("unchecked")
	public DailyPageController() {
		
	}
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	//Put on new thread TODO
    	this.handleNewQuote();

    	//Initializes two columns of schedule with time block and activity name
    	//activityNameColumn.setCellValueFactory(cellData -> cellData.getValue().editableNameProperty());
    	//Format TODO
    	//timeBlockColumn.setCellValueFactory(cellData -> cellData.getValue().timeBlockProperty());
    	root = new TreeItem<String>("Aims");
		root.setExpanded(true);
		aimTree.setRoot(root);
		aimTree.setEditable(true);

		dayViewSchedule.setRequestedTime(LocalTime.now());


		//Set thread aside and call separate method TODO

		Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> {
						dayViewSchedule.setToday(LocalDate.now());
						dayViewSchedule.setTime(LocalTime.now());
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

		activityCalendar = new Calendar("Activities");
		calendarSource = new CalendarSource("Calendars");
		calendarSource.getCalendars().addAll(activityCalendar);

		datePicker.setValue(LocalDate.now());
	}

	@FXML
	private void handleDateChange() {
    	mainApp.getDailyPage().setPageDate(datePicker.getValue());
		dateText.setText(DateTimeUtil.format(mainApp.getDailyPage().getPageDate()));

	}
    
    /*
     * Called when Delete activity button is pressed.
     */
    @FXML
    private void handleDeleteActivity() {
    	/*Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
    	if (selectedActivity != null) {
    		mainApp.getDailyPage().getActivities().getActivities().removeIf(activity -> activity.equals(selectedActivity));
    		activityTable.refresh();
    	}
    	else {
    		ErrorUtil.showErrorMessage(new Activity(), mainApp.getPrimaryStage());
    	}*/
    }
    
    /*
     * Handles when user clicks new activity button. Opens a dialog
     * to edit details for activity.
     */
    @FXML 
    private void handleNewActivity() {
    	Activity tempActivity = new Activity();
    	boolean okClicked = mainApp.showEditDialog(tempActivity);
    	if (okClicked) {
    		mainApp.getDailyPage().getActivities().getActivities().add(tempActivity);
    	}
    }
    
    
    /*
     * Handles when user clicks edit activity. Opens a dialog
     * to edit the current details for the activity.
     */
    @FXML
    private void handleEditActivity(ActionEvent event) {
    	//System.out.println(event.getSource());
    	//this.handleEditEditable(event);
    	/*Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
    	if (selectedActivity != null) {
    		boolean okClicked = mainApp.showEditDialog(selectedActivity);
    		if (okClicked) {
    			//mainApp.getDailyPage().getActivities().add(selectedActivity);
    			activityTable.refresh();
    		}
    	}
    	else {
            selectedActivity = new Activity();
			ErrorUtil.showErrorMessage(selectedActivity, mainApp.getPrimaryStage());
    	}*/
    }

	/**
	 * Handles when user clicks an edit button for an Editable
	 */
	@FXML
	private <T extends Editable> void handleEditEditable(ActionEvent event) {
		//JFXButton[id=editActivityButton, styleClass=button jfx-button]'Edit Activity'
		//Better to use Regex or something of like
		String editableClassName = event.getSource().toString();
		System.out.println(editableClassName);
		int i1 = editableClassName.indexOf("edit") + 4;
		System.out.println(i1);
		int i2 = editableClassName.indexOf("Button",4);
		System.out.println(i2);
		editableClassName = editableClassName.substring(i1,i2);
		editableClassName = editableClassName.toLowerCase();
		System.out.println(editableClassName);

		//Would have to think of a way to choose correct ListView to get selected Item from
		try {
			Field field = this.getClass().getField(editableClassName + "ListView");
			JFXListView apropriateListView = new JFXListView();
			//FIX TODO
			field.set(this, apropriateListView);
			Editable selectedEditable = (Editable) apropriateListView.getSelectionModel().getSelectedItem();
			if (selectedEditable != null) {
				boolean okClicked = mainApp.showEditDialog(selectedEditable);
				if (okClicked) {
					winListView.refresh();
				}
			}
			else {
				selectedEditable = new Editable();
				ErrorUtil.showErrorMessage(selectedEditable, mainApp.getPrimaryStage());
			}
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}

	/*
     * Handles when user clicks on new Quote But`ton.
     *  Displays random quote to page layout.
     */
    @FXML 
    private void handleNewQuote() {
    	//Put on new Thread TODO
    	try {
    		String quote = (QuoteUtil.getQuote());
    		quoteText.setText(quote);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Handles when user clicks new aim Button.
     */
    @FXML
    private void handleNewAim() {
    	Aim tempAim = new Aim("","");
    	boolean okClicked = mainApp.showEditDialog(tempAim);
    	if (okClicked) {
    		mainApp.getDailyPage().getAims().getAims().add(tempAim);
    		TreeItem<String> aimStatement = new TreeItem<>(tempAim.getEditableName());
    		root.getChildren().add(aimStatement);
    		aimStatement.setExpanded(true);
    		aimStatement.getChildren().add(new TreeItem<>(tempAim.getEditableDescription()));
    	}
    }
    
	/**
	 * Called when user clicks edit aim
	 */
	@FXML
	private void handleEditAim() {
		TreeItem<String> selectedAim = aimTree.getSelectionModel().getSelectedItem();
		aimTree.edit(selectedAim);
		//Aim selectedAim = aimTree.getSelectionModel().getSelectedItem();
	}

	/**
	 * Called when user clicks delete aim
	 */
	@FXML
	private void handleDeleteAim() {

	}

	/**
	 * Called when user clicks new Task
	 */
	@FXML
	private void handleNewTask() {
		Task newTask = new Task("", "");
		boolean okClicked = mainApp.showEditDialog(newTask);
		if (okClicked) {
			mainApp.getDailyPage().getTasks().getTasks().add(newTask);
		}
	}

	/**
	 * Called when user clicks edit Task
	 */
	@FXML
	private void handleEditTask() {
		Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			boolean okClicked = mainApp.showEditDialog(selectedTask);
			if (okClicked) {
				taskListView.refresh();
			}
		}
		//TODO
		else {
			selectedTask = new Task("", "");
			ErrorUtil.showErrorMessage(selectedTask, mainApp.getPrimaryStage());
		}
	}
	/**
	 * Called when user clicks delete Task
	 */
	@FXML
	private void handleDeleteTask() {
		Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
		if (selectedTask !=  null) {
			mainApp.getDailyPage().getTasks().getTasks().removeIf(task -> task.equals(selectedTask));
			taskListView.refresh();
		}
		else {
			ErrorUtil.showErrorMessage(new Task("",""), mainApp.getPrimaryStage());
		}
	}

	//Refactor handle methods to consolidate to all editables TODO
	/**
	 * Called when user clicks new Win
	 */
	@FXML
	private void handleNewWin() {
		Win tempWin = new Win("","");
		boolean okClicked = mainApp.showEditDialog(tempWin);
		if (okClicked) {
			mainApp.getDailyPage().getWins().getWins().add(tempWin);
			winListView.refresh();
		}
	}

	//TODO add Modular method
	@FXML
	private <T extends Editable> void handleNewEditable() {

	}

	/**
	 * Called when user clicks edit Win
	 */
	@FXML
	private void handleEditWin() {
		Win selectedWin = winListView.getSelectionModel().getSelectedItem();
		if (selectedWin != null) {
			boolean okClicked = mainApp.showEditDialog(selectedWin);
			if (okClicked) {
				//mainApp.getDailyPage().getActivities().add(selectedActivity);
				winListView.refresh();
			}
		}
		else {
			selectedWin = new Win("", "");
			ErrorUtil.showErrorMessage(selectedWin, mainApp.getPrimaryStage());
		}
	}

	/**
	 * Called when user clicks delete Win.
	 */
	@FXML
	private void handleDeleteWin() {
		Win selectedWin = winListView.getSelectionModel().getSelectedItem();
		if (selectedWin != null) {
			mainApp.getDailyPage().getWins().getWins().removeIf(win -> win.equals(selectedWin));
		}
		else {
			ErrorUtil.showErrorMessage(new Win("",""), mainApp.getPrimaryStage());
		}
	}
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        
        //Sets Schedule of activities tableview to correspond to main app data
//        activityTable.setItems(mainApp.getDailyPage().getActivities().getActivities());

        taskListView.setItems(mainApp.getDailyPage().getTasks().getTasks());

        winListView.setItems(mainApp.getDailyPage().getWins().getWins());

        mainApp.getDailyPage().getAims().getAims().stream()
        	.forEach(aim ->  {
        		TreeItem<String> aimStatement = new TreeItem<>(aim.getEditableName());
        		root.getChildren().add(aimStatement);
        		aimStatement.setExpanded(true);
        		aimStatement.getChildren().add(new TreeItem<>(aim.getEditableDescription()));
        	});
        
        //Sets date to be date of daily Page data Format TODO
        dateText.setText(DateTimeUtil.format(mainApp.getDailyPage().getPageDate()));

        //Sets style of Calendar
		activityCalendar.setStyle(Calendar.Style.STYLE2);
		//Adds calendar of daily page to dayView
		dayViewSchedule.getCalendarSources().addAll(calendarSource);

		//dayViewSchedule.addEventHandler(CalendarEvent.ENTRY_CHANGED ,event -> System.out.println(event.toString()));

		mainApp.getDailyPage().getActivities().getActivities().stream()
				.forEach(activity -> {
					//System.out.println(activity);
					Entry entry = new Entry<>(activity.getEditableName());
					entry.setUserObject(activity);
					try {
						//entry.changeStartTime(activity.getActivityStartTime());
						LocalTime endTime = activity.getActivityEndTime();
						entry.setInterval(activity.getActivityStartTime(), endTime);
						//entry.changeEndTime(activity.getActivityStartTime().plus(activity.getActivityDuration()));
						System.out.println(activity.getActivityStartTime().toString());
						System.out.println(activity.getActivityEndTime().toString());
						//e.setInterval(cons.getActivityStartTime(), cons.getActivityStartTime().plus(cons.getActivityDuration()));
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
						//entry.setInterval(activity.getActivityStartTime(), activity.getActivityStartTime().plus(Duration.ofMinutes(60)));
					}
					activityCalendar.addEntry(entry);
				});
		//Experimentation Modularize TODO
		dayViewSchedule.setEntryFactory(param -> {
			System.out.println(mainApp.getDailyPage());
			DateControl control = param.getDateControl();
			VirtualGrid grid = control.getVirtualGrid();
			ZonedDateTime time = param.getZonedDateTime();
			DayOfWeek firstDayOfWeek = control.getFirstDayOfWeek();

			ZonedDateTime lowerTime = grid.adjustTime(time, false, firstDayOfWeek);
			ZonedDateTime upperTime = grid.adjustTime(time, true, firstDayOfWeek);

			if (Duration.between(time, lowerTime).abs().minus(Duration.between(time, upperTime).abs()).isNegative()) {
				time = lowerTime;
			} else {
				time = upperTime;
			}

			Entry<Object> entry = new Entry<>("New Entry EDITED");

			//Maybe bind? properties together?
			Activity a = new Activity();

			entry.setUserObject(a);


			entry.changeStartDate(time.toLocalDate());
			entry.changeStartTime(time.toLocalTime());
			entry.changeEndDate(entry.getStartDate());
			entry.changeEndTime(entry.getStartTime().plusHours(1));

			a.setEditableName(entry.getTitle());
			a.setActivityStartTime(entry.getStartTime());
			a.setActivityDuration(Duration.between(entry.getEndTime(),entry.getStartTime()));
			mainApp.getDailyPage().getActivities().getActivities().add(a);

			if (control instanceof AllDayView) {
				entry.setFullDay(true);
			}

			return entry;
		});
    }
	
}
