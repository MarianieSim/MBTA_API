import java.util.LinkedList;
import java.util.List;

public class Subway{
	
	//Name of Subway Line
	public String Name;
	
	//Number of Subway Line Stops
	public int StopsCount = 0;
	
	//List of Subway Line Stops
	public List<String> StopNames = new LinkedList<String>();
	
	//List of Subway Line Connector Stops
	public List<Connector> Connector = new LinkedList<Connector>();
	
	//Subway Beginning Stop
	public Stop Start;
	
	//Subway Ending Stop
	 public Stop End;
	 
	
	//Setting Name of Subway Line
	 public void setName(String N)
	 {
		 Name = N;
	 }
	 
	//Increasing Number of Subway Line Stops
	 public void increaseStops()
	 {
		 StopsCount++;
	 }
	 
	//Adding to List of Subway Line Stops
	 public void addStop(String StopName)
	 {
		 StopNames.add(StopName);
		 
	 }
	 
	//Setting Name of the Beginning Stop
	 public void setBeginningStop(Stop S)
	 {
	    Start = S;
	 }
		 
	//Setting Name of the Ending Stop
    public void setEndingStop(Stop E)
	 {
	    End = E;
	 }
	 
	//Adding to the List of Subway Line Connector Stops
	 public void addConnector(Connector Name)
	 {
		 Connector.add(Name);
	 }
	 
	//Getting Name of Subway Line
	 public String getName()
	 {
		 return Name;
	 }
	 
	//Getting Name of the Beginning Stop
	 public Stop getBeginningStop()
	 {
			 return Start;
	 }
		 
	//Getting Name of the Ending Stop
	 public Stop getEndingStop()
	  {
			 return End;
	  }
	 
	//Getting Number of Subway Line Stops
	 public int getStops()
	 {
		 return StopsCount;
	 }
	 
    //Getting to List of Subway Line Stops
	 public List<String> getStopNames()
	 {
		 return StopNames;
	 }
	 
	 //Getting to List of Subway Line Connector Stops
	 public List<Connector> getConnectors()
	 {
		 return Connector;
	 }
	 
}
