import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class MonthlyInvoice implements InvoiceEssentials, Serializable {
	
	private Person child;
	private LocalDate dateOfIssue;
	private double totalAmount;      // 4 Weeks of the child's age category and session
	private double fineAmmount;		//  Early Dropping and Late Pickup
	private boolean status;          // false when invoice not paid yet, true when paid
	
	public MonthlyInvoice()
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
	
	public void SetStatus(boolean status)
	{
		this.status = status;
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
	
	public boolean GetStatus()
	{
		return this.status;
	}
	
	public void SetFineAmount(double fineAmmount)
	{
		this.fineAmmount = fineAmmount;
	}
	
	public double GetFineAmount()
	{
		return this.fineAmmount;
	}
}
