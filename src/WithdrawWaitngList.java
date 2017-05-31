import java.io.Serializable;
import java.time.LocalDate;

public class WithdrawWaitngList implements Serializable {
	
	private LocalDate DateOfSet;
	private Child child;
	
	public WithdrawWaitngList(LocalDate DateOfSet,Child child)
	{
		this.child = child;
		this.DateOfSet = DateOfSet;
	}
	
	
	public LocalDate getDateOfSet()
	{
		return this.DateOfSet;
	}
	public String getChildName()
	{
		return child.getFullName();
	}
}
