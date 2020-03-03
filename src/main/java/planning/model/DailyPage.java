package planning.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import planning.util.DateTimeUtil;
import planning.util.LocalDateAdapter;

@XmlRootElement(name = "dailypage")
public class DailyPage {
	private ActivityListWrapper activities;
	private AimListWrapper aims;
	private TaskListWrapper tasks;
	private WinListWrapper wins;
	private ObjectProperty<LocalDate> pageDate;
	/*private Calendar activityCalendar;
	private CalendarSource calendarSource;*/
	//Probably will need a list of entries, bc entry data isn't forward facing. Will have to set entries to calendar
	
	public DailyPage() {
		pageDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		activities = new ActivityListWrapper();
		aims = new AimListWrapper();
		tasks = new TaskListWrapper();
		wins = new WinListWrapper();
		/*activityCalendar = new Calendar("Activities");
		calendarSource = new CalendarSource("Calendars");
		calendarSource.getCalendars().addAll(activityCalendar);*/
	}

	public DailyPage(ActivityListWrapper activities, AimListWrapper aims, TaskListWrapper tasks, WinListWrapper wins, LocalDate date) {
		this();
		this.setPageDate(date);
		this.setActivities(activities);
		this.setAims(aims);
		this.setTasks(tasks);
		this.setWins(wins);
	}

	/**
	 * Creates deep copy of DailyPage
	 */
	public DailyPage(DailyPage dailyPage) {
		this.setPageDate(dailyPage.getPageDate());
		this.setActivities(dailyPage.getActivities());
		this.setAims(dailyPage.getAims());
		this.setTasks(dailyPage.getTasks());
		this.setWins(dailyPage.getWins());
	}

	/**
	 * @return the activities
	 */
	@XmlElement
	public ActivityListWrapper getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(ActivityListWrapper activities) {
		this.activities = activities;
	}

	/**
	 * @return the aims
	 */
	@XmlElement
	public AimListWrapper getAims() {
		return aims;
	}

	/**
	 * @param aims the aims to set
	 */
	public void setAims(AimListWrapper aims) {
		this.aims = aims;
	}

	/**
	 * @return the pageDate
	 */
	@XmlElement(name = "date")
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getPageDate() {
		return pageDate.get();
	}

	/**
	 * @param pageDate the pageDate to set
	 */
	public void setPageDate(LocalDate pageDate) {
		this.pageDate.set(pageDate);
	}

	/*public CalendarSource getCalendarSource() {
		return calendarSource;
	}

	public void setCalendarSource(CalendarSource calendarSource) {
		this.calendarSource = calendarSource;
	}
*/
	@XmlElement
	public TaskListWrapper getTasks() {
		return tasks;
	}

	public void setTasks(TaskListWrapper tasks) {
		this.tasks = tasks;
	}

	@XmlElement
	public WinListWrapper getWins() {
		return wins;
	}

	public void setWins(WinListWrapper wins) {
		this.wins = wins;
	}
	
	public ObjectProperty<LocalDate> pageDateProperty() {
		return pageDate;
	}

	/*public Calendar getActivityCalendar() {
		return activityCalendar;
	}

	public void setActivityCalendar(Calendar activityCalendar) {
		this.activityCalendar = activityCalendar;
	}*/

	@Override
	public String toString() {
		String outString = "Page Date: " + DateTimeUtil.format(this.getPageDate()) + "\n"
				+ "Activities: " + this.getActivities().getActivities().toString() +"\n"
				+ "Aims: " + this.getAims().getAims().toString() + "\n"
				+ "Tasks: " + this.getTasks().getTasks().toString() + "\n"
				+ "Wins: " + this.getWins().getWins().toString() + "\n";
		return outString;
	}

}
