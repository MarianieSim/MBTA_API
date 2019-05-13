import java.util.LinkedList;
import java.util.List;

public class Connector {
	
	//List of Subway Lines that contain the Connector Stop
	public  List<Subway> Subway = new LinkedList<Subway>();
	
	//Name of Connector Stop
	public String Name;
	
	//Setting the Name of Connector Stop
	public void setName(String N)
	 {
		 Name = N;
	 }
    
	//Adding to the List of Subway Lines that contain the Connector Stop
    public void addSubway(Subway S)
	 {
    	Subway.add(S);
		 
	 }
    
    //Getting the Name of Connector Stop
    public String getName()
	 {
		 return Name;
	 }
    
    //Getting the List of Subway Lines that contain the Connector Stop
    public List<Subway> getSubwayList()
	 {
		 return Subway;
	 }
}
