import java.io.Serializable;
import java.time.LocalDate;

public class ChangeSessionWaitingList implements Serializable {
	
	private LocalDate DateOfSet;
	private Child child;
	private String NewSession;
	
	public ChangeSessionWaitingList(LocalDate DateOfSet,Child child,String NewSession)
	{
		this.child = child;
		this.DateOfSet = DateOfSet;
		this.NewSession = NewSession;
	}
	
	
	public LocalDate getDateOfSet()
	{
		return this.DateOfSet;
	}
	public String getChildName()
	{
		return child.getFullName();
	}
	
	public String getNewSession()
	{
		return this.NewSession;
	}
}
