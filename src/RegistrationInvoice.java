import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class RegistrationInvoice implements InvoiceEssentials, Serializable{

	private Person child;
	private LocalDate dateOfIssue;
	private double totalAmount;      // One week fees + 25 pounds
 	private static int RegistrationFee = 25;
 	
	public RegistrationInvoice()
	{
		
	}
	

	@Override
	public void SetDateOfIssue(LocalDate dateOfIssue) {

		this.dateOfIssue = dateOfIssue;
	}
 

	@Override
	public void SetChild(Person child) {
		
 		this.child = child;
	}
	
	
	@Override
	public void SetTotalAmount(double totalAmount) 
	{
 		this.totalAmount = totalAmount; 
	}
	
	@Override
	public LocalDate GetDateOfIssue() 
	{
 		return this.dateOfIssue;
	}


	@Override
	public String GetChildName()
	{
 		return this.child.getFullName();
	}

	@Override
	public double GetTotalAmount()
	{
 		return this.totalAmount;
	}

	public int GetRegistrationFee()
	{
		return this.RegistrationFee;
	}

}
