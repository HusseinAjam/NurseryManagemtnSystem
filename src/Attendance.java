import java.io.Serializable;

public class Attendance implements Serializable{
	
	private Person person;
	private String date;
	private String Status;
	
	public Attendance (Person person, String status, String date)
	{
		// Set attendance
		this.person = person;
		this.Status = status;
		this.date = date;
	}
	public Attendance ()
	{
		
	}
	public String getPersonForAtendance ()
	{
		return person.getFullName();
	}
	
	public String getAttendance ()
	{
		return Status+" On "+date ;	
	}

}
