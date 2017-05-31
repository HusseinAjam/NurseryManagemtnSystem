import java.io.Serializable;

public class Session implements Serializable{
	private String sessionName;   
	private String sessionStartTime;
	private String sessionEndTime;
	private double ageCate1PerDay;  // These are prices for each age category
	private double ageCate1PerWeek;
	private double ageCate2PerDay;
	private double ageCate2PerWeek;
	private double ageCate3PerDay;
	private double ageCate3PerWeek;
	
	public Session(String sessionName, double ageCate1PerDay, double ageCate1PerWeek, double ageCate2PerDay
   ,double ageCate2PerWeek, double ageCate3PerDay, double ageCate3PerWeek,String sessionStartTime, String sessionEndTime)
	{
		this.sessionName = sessionName; 
		this.ageCate1PerDay = ageCate1PerDay;
		this.ageCate1PerWeek = ageCate1PerWeek;
		this.ageCate2PerDay = ageCate2PerDay;
		this.ageCate2PerWeek = ageCate2PerWeek;
		this.ageCate3PerDay = ageCate3PerDay;
		this.ageCate3PerWeek = ageCate3PerWeek;
		this.sessionStartTime = sessionStartTime;
		this.sessionEndTime = sessionEndTime;
	}
	public Session ()
	{
		
	}
	// Geters
	public String getSessionName ()
	{
		return sessionName;
	}
	
	public double getAgeCate1PerDay()
	{
		return ageCate1PerDay;
	}
	public double getAgeCate2PerDay()
	{
		return ageCate2PerDay;
	}
	public double getAgeCate3PerDay()
	{
		return ageCate3PerDay;
	}
	public double getAgeCate1PerWeek()
	{
		return ageCate1PerWeek;
	}
	public double getAgeCate2PerWeek()
	{
		return ageCate2PerWeek;
	}
	public double getAgeCate3PerWeek()
	{
		return ageCate3PerWeek;
	}
	public String getSessionStartTime ()
	{
		return sessionStartTime;
	}
	public String getSessionEndTime ()
	{
		return sessionEndTime;
	}
	
  // Seters
	public void setAgeCate1PerDay (double amount)
	{
		this.ageCate1PerDay = amount;
	}
	public void setAgeCate2PerDay (double amount)
	{
		this.ageCate2PerDay = amount;
	}
	public void setAgeCate3PerDay (double amount)
	{
		this.ageCate3PerDay = amount;
	}
	public void setAgeCate1PerWeek (double amount)
	{
		this.ageCate1PerWeek = amount;
	}
	public void setAgeCate2PerWeek (double amount)
	{
		this.ageCate2PerWeek = amount;
	}
	public void setAgeCate3PerWeek (double amount)
	{
		this.ageCate3PerWeek = amount;
	}
	

}
