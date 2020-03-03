package planning.model;

import java.time.Duration;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import planning.util.DateTimeUtil;
import planning.util.DurationAdapter;
import planning.util.LocalTimeAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Model class for an activity.
 * 
 * @author Dillon
 */

public class Activity extends Editable {

	private StringProperty timeBlock;
	private ObjectProperty<Duration> activityDuration;
	private ObjectProperty<LocalTime> activityStartTime;
	
	public Activity() {
		this("","",Duration.ofMinutes(60),LocalTime.NOON);
	}
	
	/**
	 * Constructor of activity object
	 * @param aName Name of activity
	 * @param aDes Description of activity
	 * @param aDur Duration of activity
	 * @param aStart Activity start time
	 */
	public Activity(String aName, String aDes, Duration aDur, LocalTime aStart) {
		super(aName, aDes);
		this.activityDuration = new SimpleObjectProperty<Duration>(aDur);
		this.activityStartTime = new SimpleObjectProperty<LocalTime>(aStart);
		updateTimeBlock();
	}

	/**
	 * @return the activityDuration
	 */
	@XmlJavaTypeAdapter(DurationAdapter.class)
	public Duration getActivityDuration() {
		return activityDuration.get();
	}
	
	/**
	 * @param activityDuration the activityDuration to set
	 */
	
	public void setActivityDuration(Duration activityDuration) {
		this.activityDuration.set(activityDuration);
		updateTimeBlock();
	}
	
	public ObjectProperty<Duration> activityDurationProperty() {
		return activityDuration;
	}
	
	/**
	 * @return the activityStartTime
	 */
	@XmlJavaTypeAdapter(LocalTimeAdapter.class)
	public LocalTime getActivityStartTime() {
		return activityStartTime.get();
	}
	
	/**
	 * @param activityStartTime the activityStartTime to set
	 */
	public void setActivityStartTime(LocalTime activityStartTime) {
		this.activityStartTime.set(activityStartTime);
		updateTimeBlock();
	}

	public LocalTime getActivityEndTime() {
		LocalTime timeCopy = LocalTime.from(getActivityStartTime());
		return timeCopy.plus(getActivityDuration());
	}
	
	public ObjectProperty<LocalTime> activityStartTimeProperty() {
		return activityStartTime;
	}
	
	/*
	 * @return Formatted Time block to pass into page view; Could outsource
	 */
	public String getTimeBlock() {
		return timeBlock.get();
	}
	
	public void updateTimeBlock() {
		String startTime = DateTimeUtil.format(activityStartTime.get());
		String endTime = DateTimeUtil.format(activityStartTime.get().plus(activityDuration.get()));
		this.timeBlock = new SimpleStringProperty(startTime + " - " + endTime);
	}
	
	public StringProperty timeBlockProperty() {
		return timeBlock;
	}

	public String toString() {
		return this.getEditableName() + "\n"
				+ "Activity Start Time: " + this.getActivityStartTime().toString() + "\n"
				+ "Activity Duration: " + this.getActivityDuration().toString() + "\n";
	}
}
