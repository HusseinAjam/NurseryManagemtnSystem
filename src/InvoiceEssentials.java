import java.time.LocalDate;
import java.util.Date;

public interface InvoiceEssentials {
	
	// We have 3 types of Invoices Registration, Monthly and Leaving Invoice. They have different parameters these parameters are compulsory for all 3 types
	public void SetDateOfIssue(LocalDate dateOfIssue);
 	public void SetChild(Person child);
 	public void SetTotalAmount(double totalAmount);
	public LocalDate GetDateOfIssue();
 	public String GetChildName();
 	public double GetTotalAmount();
}
