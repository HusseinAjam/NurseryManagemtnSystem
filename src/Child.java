import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Child extends Person implements Serializable {
	
	private String AlergyInformation;
	private String Session;
	private String CarerName;
	private String CarerEmail;
	private String CarerContactNo;
	private Parents Father;
	private Parents Mother;
	private String status;   // If child still attending or left 
	private LocalDate RegistrationDate;

	


	
	public Child (String firstName, String lastName, LocalDate birthday, String address,String gender , Parents Father , Parents Mother
			      , String AlergyInformation, String CarerName, String CarerEmail, String CarerContactNo, String Session , LocalDate RegistrationDate)
	{
		super(firstName, lastName, birthday, address, gender);
		this.AlergyInformation = AlergyInformation;
		this.Session = Session;
		this.CarerName = CarerName;
		this.CarerEmail =CarerEmail;
		this.CarerContactNo = CarerContactNo;
		this.Father = Father;
		this.Mother = Mother;
		this.status = "Attending"; // The default status value of a child is attending the nursery.
		this.RegistrationDate = RegistrationDate;
 	}
	
	
	public void setSession(String Session)
	{
		this.Session = Session;
	}
	
	public void setStatus(String status)   // Set status as "left" when child leave the nursery
	{
		this.status = status;
	}
	
	public String getAlergyInformation ()
	{
		return AlergyInformation;
	}
	
	public String getCarerName ()
	{
		return CarerName;
	}
	
	public String getCarerEmail ()
	{
		return CarerEmail;
	}
	public String getCareContactNumber()
	{
		return CarerContactNo;
	}
	
	public String getSession()
	{
		return this.Session;
	}
	
	public String getFather()
	{
		return Father.getFullName();
	}
	
	public String getMother()
	{
		return Mother.getFullName();
	}
	
	public String getStatus()
	{
		return this.status;
	}
	
	public LocalDate getRegistrationDate()
	{
		return this.RegistrationDate;
	}
	
	public void setRegistrationDate(LocalDate registerationDate)
	{
		 this.RegistrationDate = registerationDate;
	}
	
}
