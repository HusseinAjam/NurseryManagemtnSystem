import java.io.Serializable;
import java.time.LocalDate;

public class SecurityGuards  extends Person implements Serializable {
	
 	private String shift;	
	
	public SecurityGuards (String firstName, String lastName, LocalDate birthday, String address,String gender
			         , String shift )
	{
		super(firstName,   lastName,   birthday,   address,  gender);
		this.shift = shift;
 	}
	
	public void setShift(String session)
	{
		this.shift = shift;
	}
	
	public String getShift ()
	{
		return this.shift;
	}
}
