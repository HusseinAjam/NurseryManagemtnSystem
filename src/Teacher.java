import java.io.Serializable;
import java.time.LocalDate;

public class Teacher  extends Person implements Serializable {
	
 	private String session;	
	
	public Teacher (String firstName, String lastName, LocalDate birthday, String address,String gender
			         , String session )
	{
		super(firstName,   lastName,   birthday,   address,  gender);
		this.session = session;
 	}
	
	public void setSession(String session)
	{
		this.session = session;
	}
	
	public String getSession ()
	{
		return this.session;
	}
}
