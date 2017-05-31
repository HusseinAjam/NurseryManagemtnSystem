import java.io.Serializable;
import java.time.LocalDate;

public class Admins  extends Person implements Serializable {
	
 	private String userName;	
 	private String passWord;	

	
	public Admins (String firstName, String lastName, LocalDate birthday, String address,String gender
			         , String userName , String passWrod)
	{
		super(firstName,   lastName,   birthday,   address,  gender);
		this.userName = userName;
		this.passWord = passWrod;
 	}
	
	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}
	
	public String getPassWord ()
	{
		return this.passWord;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
}
