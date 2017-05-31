import java.io.Serializable;
import java.time.LocalDate;

public class DroppingAndPickUp implements Serializable{
	
	private int TimeDifferenceinMinutes;
	private Child child;
	private String date;
	private String situationType;  // Late or Early?
	private LocalDate dateOfActivity;
	
	public DroppingAndPickUp(int TimeDifferenceinMinutes,Child child, String date, String situationType, LocalDate dateOfActivity)
	{
		this.TimeDifferenceinMinutes = TimeDifferenceinMinutes;
		this.child = child;
		this.date = date;
		this.dateOfActivity = dateOfActivity;
	}
	
	public DroppingAndPickUp(Child child)
	{
		this.child = child;
	}
	
	public DroppingAndPickUp()
	{
		
	}
	
	public int getTimeDifferenceinMinutes()
	{
		return TimeDifferenceinMinutes;
	}
	
	public String getDateOfDroppingOrPickUp()
	{
		return this.date;
	}
	
	public String getChildName()
	{
		return child.getFullName();
	}
	
	public LocalDate getActivityDate()
	{
		return this.dateOfActivity;
	}
}
