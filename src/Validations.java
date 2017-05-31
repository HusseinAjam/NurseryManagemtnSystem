import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validations {
	
   public static boolean validateName( String name )
   {
      return name.matches( "[A-Z][a-zA-Z]*" );
   } 
   
   public static boolean validateNumber (String number) 
   {
	    return number.matches("^[0-9]+$");
	}
   
  public static boolean validateEmailAddress(String email)
  {  
		  boolean checker = false;
   try {
		InternetAddress emailaddress = new InternetAddress(email);
		emailaddress.validate();
		checker = true;
		  } catch (AddressException e) {
		  }
		  return checker;
	   }
}
