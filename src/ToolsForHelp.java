import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ToolsForHelp {
	
    public int FilesManagerGetVlaue (String Counter)
    {
    	int temp = 0;
        try
        {
        File file1 = new File("Storage Reference.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file1));
        String Currentline = "";
        String originalText = "";
         while((Currentline = fileReader.readLine()) != null)
            {
     	   if(Currentline.startsWith(Counter))
     	   {
         	   originalText += Currentline + "\r\n";
         	   Currentline = fileReader.readLine();
         	   temp = Integer.parseInt(Currentline);
     	   }
     	   originalText += Currentline + "\r\n";
        }
        fileReader.close();
       
    }
    catch (IOException ioe)
        {
        ioe.printStackTrace();
    } return temp;
        
  }
    
    public  void FilesManagerIncrease (String Counter)
    {
        try
        {
        File file1 = new File("Storage Reference.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file1));
        String Currentline = "";
        String originalText = "";
        int temp;
        while((Currentline = fileReader.readLine()) != null)
            {
     	   if(Currentline.startsWith(Counter))
     	   {
         	   originalText += Currentline + "\r\n";
         	   Currentline = fileReader.readLine();
         	   temp = Integer.parseInt(Currentline);
         	   ++temp;
         	   Currentline = temp + "";
     	   }
     	   originalText += Currentline + "\r\n";
        }
        fileReader.close();
        
        FileWriter writer = new FileWriter("Storage Reference.txt");
        writer.write(originalText);
        writer.close();

    }
    catch (IOException ioe)
        {
        ioe.printStackTrace();
    }
    }   

    public  void FilesManagerDecrease (String Counter)
    {
        try
        {
        File file1 = new File("Storage Reference.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file1));
        String Currentline = "";
        String originalText = "";
        int temp;
        while((Currentline = fileReader.readLine()) != null)
            {
     	   if(Currentline.startsWith(Counter))
     	   {
         	   originalText += Currentline + "\r\n";
         	   Currentline = fileReader.readLine();
         	   temp = Integer.parseInt(Currentline);
         	   --temp;
         	   Currentline = temp + "";
     	   }
     	   originalText += Currentline + "\r\n";
        }
        fileReader.close();
        
        FileWriter writer = new FileWriter("Storage Reference.txt");
        writer.write(originalText);
        writer.close();

    }
    catch (IOException ioe)
        {
        ioe.printStackTrace();
    }
    }  
    
    public int ConvertBirthdayToAgeCategory (LocalDate birth) 
    {
    	LocalDate birthday = LocalDate.of(birth.getYear(), birth.getMonth(), birth.getDayOfMonth());
        long years = birthday.until(LocalDate.now(), ChronoUnit.YEARS);
       	if(years == 2 )
       	{
       		return 2;
       	}
       	else if ((years == 1) || (years == 0))
       	{
       		return 0;
       	}
       	else 
       	{
       		return 3;
       	}
    }
    
    public boolean CheckForYoungerSiblingAndAttendingNow(String ChildName) throws ClassNotFoundException, IOException
    {
     	ToolsForHelp tool = new ToolsForHelp();
	     int Counter = tool.FilesManagerGetVlaue("ChildrenCounter");
	    // Read the stored children objects
 		   
 	      FileInputStream inStream2 = new FileInputStream("Children.dat");
	      ObjectInputStream objectInputFile = new ObjectInputStream(inStream2);

	      // Create array of objects
	      Child[]  children = new Child[Counter];
	  
	      // Read the serialized objects from the file.
	      for (int i = 0; i < Counter; i++)
	      {
	    	  children[i] = (Child) objectInputFile.readObject();
	      }
	      objectInputFile.close();
	      
	      // Check for Older Sibling, from either side father or mother
	      
	      // first store the child's mother and father full names
	      String FatherFullName = "";
	      String MotherFullName="";
	      int childPosition = -1;
	      for (int i = 0; i < Counter; i++)
	      {
	    	  if(children[i].getFullName().equals(ChildName))
	    	  {
	    		  FatherFullName = children[i].getFather();
	    		  MotherFullName = children[i].getMother();
	    		  childPosition = i; 
	    	  }
 	      }
	      
	      // second compare all "Attending" children with any of these names
	      // note: when Child status is attending, means he is in the nursery at the moment
	      // in case we found we only want if the child is the older.
	      
	      for (int i = 0; i < Counter; i++)
	      {
	    	  if (children[i].getStatus().equals("Attending")) // Attending
	    	  if(children[i].getFather().equals(FatherFullName) || children[i].getFather().equals(FatherFullName)) // Same father or mother
	    	  {
	    		  if(children[i].getBirthday().isAfter(children[childPosition].getBirthday())) // the Older child, Note this step will make us skip the same child!
	    		  {
	    			  System.out.println("Found it");
	    			  return true;
	    		  }
	    	  }
 	      }
	      return false;     
    }

   
}
