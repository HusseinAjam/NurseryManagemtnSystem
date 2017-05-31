import java.io.Serializable;
import java.time.LocalDate;

public class HouseKeepingStaff  extends Person implements Serializable {
	
 	private String duty;	
	
	public HouseKeepingStaff (String firstName, String lastName, LocalDate birthday, String address,String gender
			         , String duty )
	{
		super(firstName,   lastName,   birthday,   address,  gender);
		this.duty = duty;
 	}
	
	public void setDuty(String duty)
	{
		this.duty = duty;
	}
	
	public String getDuty ()
	{
		return this.duty;
	}
}
