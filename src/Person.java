import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
	
	private String firstName;
	private String lastName;
	private LocalDate birthday;
	private String address;
	private String gender;

	
	public Person (String firstName, String lastName, LocalDate birthday, String address, String gender)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.address = address;
		this.gender = gender;
	}
	
	public Person (String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Person ()
	{

	}
	
	
	public String getFullName ()
	{
		return firstName+" "+lastName;
	}

	public String getAddress ()
	{
		return address;
	}
	
	public LocalDate getBirthday ()
	{
		return birthday;
	}
	public String getGender ()
	{
		return gender;
	}

}
