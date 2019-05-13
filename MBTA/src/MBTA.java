import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MBTA {
	
	
	//List of all the Subway Routes
		public static List<Subway> SubwayLines = new LinkedList<Subway>();
		
		//List of all the Connector Stops
		public static List<Connector> Connector = new LinkedList<Connector>();
		
		 //List To Store Subway Stops
		public static List<Stop> Stops = new LinkedList<Stop>();
		
		
		//Main Function executing the Question functions
	    public static void main(String args[])  
	    {  
	    	
	    	//Introduction
	    	System.out.println("This is Marianie Take Home Interview Questions");
	    	System.out.println("");
	    	
	    	//Function for Question 1
	          MBTA.Question1();
	        
	      //Functions for Question 2
	          MBTA.Question2a();
	          MBTA.Question2b();
	        
	      //Functions for Question 3
	          MBTA.Question3();
	         
	    
	    }
	    
		public static void Question1() {
			//Introduction
			System.out.println("The Answer to Question 1 -");
			System.out.println("List of Subways:");
	        
			//MBTA API URL with my API Key
	        String query_url = "https://api-v3.mbta.com/routes?filter[type]=0,1,3?api_key=743506fff518480b83a66ec29614eb64";
	        
	        //Connecting to MBTA API Server 
	        try {
	        URL url = new URL(query_url);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setRequestMethod("GET");
	       
	        //Reading the API Response 
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = IOUtils.toString(in, "UTF-8");
	     
	       //Parsing API JSON File       
	        JsonParser parser = new JsonParser();
	        JsonElement jsonTree = parser.parse(result);    
	        if(jsonTree.isJsonObject()){
	            JsonObject jsonObject = jsonTree.getAsJsonObject();
	            JsonElement data = jsonObject.get("data");
	           
	            //Getting Values from the MBTA Data Array
	            if(data.isJsonArray()){
	                JsonArray jsonData = data.getAsJsonArray();
	                for (JsonElement jsonDataElement : jsonData)
	                {
	                     if(jsonDataElement.isJsonObject()){
	                     JsonObject jsonDataElementObject = jsonDataElement.getAsJsonObject();
	                     JsonElement attributes = jsonDataElementObject.get("attributes");
	                     
	                     //Getting Values from the MBTA Data:Attributes Object
	                     	if(attributes.isJsonObject()){
	                     		JsonObject jsonAttributes = attributes.getAsJsonObject();
	                      
	                     		//Getting Values from the MBTA Data:Attributes:Long_Name String
	                     		JsonElement long_name = jsonAttributes.get("long_name");
	                     		
	                     		//Getting Values from the MBTA Data:Attributes:Direction_Destinations String
	                     		JsonElement direction_destinations = jsonAttributes.get("direction_destinations");
	                            JsonArray jsondirection_destinations = direction_destinations.getAsJsonArray();
	                     		
	                            //Convert Green Line format from Green Line E to Green Line - (E) for easier Json parsing
	                            Subway add = new Subway();       
	                            String Longname = long_name.getAsString();
	                            	  if(Longname.contains("Green Line"))
	                            	  {
	                            		  char letter = Longname.charAt(11);
	                            		  Longname = "Green Line - ("+ letter + ")";
	  	                               }
	                   
	                            //Adding the Starting and Ending Stops to each Green Line Subway Class's Stop List
	                     		add.setName(Longname);
	                            if(Longname.contains("Green Line"))
	                            {	
	                		        
	                		      //Adding Subway Line Starting and Ending Stop Name to Stop List
	                		        add.addStop(jsondirection_destinations.get(0).getAsString());
	                            	add.increaseStops();
	                            	add.addStop(jsondirection_destinations.get(1).getAsString());
	                            	add.increaseStops();
	                             	Stop newAdd = new Stop();
	                		        newAdd.setName(jsondirection_destinations.get(0).getAsString());
	         						newAdd.setSubwayLine(Longname);
	         						Stops.add(newAdd);
	         						newAdd = new Stop();
	         						newAdd.setName(jsondirection_destinations.get(1).getAsString());
	         						newAdd.setSubwayLine(Longname);
	         						Stops.add(newAdd);
	                            }
	                     		
	                            //Add SubwayLine to Subway Line list
	                     		SubwayLines.add(add);
	                       
	                     		//Answer To Question 1
	                     		System.out.println(Longname);  
	                     	}     
	                     }

	                }
	            }
	        }
	        //Closing Input Stream
	        in.close();
	       
	        //Closing Connection
	        conn.disconnect();
	    	
	       
	        } 
	        //Checking if the API Key Works
	        catch (Exception e) {
				System.out.println(e);
			}
	        //Space between Questions
	        System.out.println("");  
		}

		public static void Question2a() {
			//Introduction
			System.out.println("The Answer to Question 2 -");
			
			//MBTA API URL with my API Key
	        String query_url = "https://api-v3.mbta.com/stops?api_key=743506fff518480b83a66ec29614eb64";
	        
	       
	    
	        //Connecting to MBTA API Server 
	        try {
	        URL url = new URL(query_url);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setConnectTimeout(5000);
	        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        conn.setDoOutput(true);
	        conn.setRequestMethod("GET");
	       
	        //Reading the API Response 
	        InputStream in = new BufferedInputStream(conn.getInputStream());
	        String result = IOUtils.toString(in, "UTF-8");
	        
	      //Parsing API JSON File       
	        JsonParser parser = new JsonParser();
	        JsonElement jsonTree = parser.parse(result);    
	        if(jsonTree.isJsonObject()){
	            JsonObject jsonObject = jsonTree.getAsJsonObject();
	            JsonElement data = jsonObject.get("data");
	           
	            //Getting Values from the MBTA Data Array
	            if(data.isJsonArray()){
	                JsonArray jsonData = data.getAsJsonArray();
	                for (JsonElement jsonDataElement : jsonData)
	                {
	                     if(jsonDataElement.isJsonObject()){
	                     JsonObject jsonDataElementObject = jsonDataElement.getAsJsonObject();
	                     JsonElement attributes = jsonDataElementObject.get("attributes");
	                     
	                     //Getting Values from the MBTA Data:Attributes Object
	                     	if(attributes.isJsonObject()){
	                     		JsonObject jsonAttributes = attributes.getAsJsonObject();
	                     		
	                     		//Getting Values from the MBTA Data:Attributes:Description String
	                     		JsonElement description = jsonAttributes.get("description");
	                     		
	                     		//Getting Values from the MBTA Data:Attributes:Name String
	                     		JsonElement name = jsonAttributes.get("name");
	                     		
	                     		//Checking is Subway Stops has a Description, because Stops' Description contains it's Associated Subway Line
	                     		if (!description.isJsonNull())
	                     		{
	                     			//Runs through all the Subway Lines names for a match in the Subway Stops' Description
	                     			for (Subway Subwayline : SubwayLines)
	                     	        {    				
	                     				if(description.getAsString().contains(Subwayline.getName()))
	                     				{ 	
	                     					//If the Subway Stops' Description matches the Subway Line, it is added to the Subway Line's Stop Class
	                     					if(!Subwayline.getStopNames().contains(name.getAsString()))
	                     					{
	                     						//Stop is added to MBTA's and Subway Line Stop List
	                     						Stop newAdd = new Stop();
	                     						Subwayline.addStop(name.getAsString());
	                         					Subwayline.increaseStops();
	                         					newAdd = new Stop();
	                         					newAdd.setName(name.getAsString());
	                       						newAdd.setSubwayLine(Subwayline.getName());
	                     						Stops.add(newAdd);
	                     					}
	                     				}
	                     				
	                     				//Add Green Line Stops past Park Street to Green Lines E and C
	                     				else if(((Subwayline.getName().contains("Green Line - (E)")) || ((Subwayline.getName().contains("Green Line - (C)"))) ) && (description.getAsString().contains("Green Line - Copley & West")))
	                 					{
	                     					if((!Subwayline.getStopNames().contains(name.getAsString())) && !name.getAsString().equalsIgnoreCase("Lechmere") && !name.getAsString().equalsIgnoreCase("Science Park"))
	                     					{	
	                     						Subwayline.addStop(name.getAsString());
	                     						Subwayline.increaseStops();
	                     						Stop newAdd = new Stop();
	                     						newAdd.setName(name.getAsString());
	                     						newAdd.setSubwayLine(Subwayline.getName());
	                     						Stops.add(newAdd);
	                     					}
	                     					
	                     					//Six Stop on the Green Line didn't contain any association to a Subway Line in their description, so they were added manually to the Subway line Lines
	                     					//Adding Science Park Stop to Green Line E
	                     					else if(name.getAsString().equalsIgnoreCase("Science Park") && Subwayline.getName().contains("Green Line - (E)"))
	                     					{
	                     							
	                         						Subwayline.addStop(name.getAsString());
	                         						Subwayline.increaseStops();
	                         						Stop newAdd = new Stop();
	                         						newAdd.setName(name.getAsString());
	                         						newAdd.setSubwayLine(Subwayline.getName());
	                         						Stops.add(newAdd);
	                         				
	                     					}
	                   
	                 					}
	                     				//Adding Boylston, Arlington, and Hynes Convention Center to all Green Line except Green Line E
	                     				else if(((name.getAsString().equalsIgnoreCase("Boylston")) || (name.getAsString().equalsIgnoreCase("Arlington")) || (name.getAsString().equalsIgnoreCase("Hynes Convention Center"))) && (Subwayline.getName().contains("Green Line")) && (!Subwayline.getName().equalsIgnoreCase("Green Line - (E)")) )
	                 					{	

	                     					if(!Subwayline.getStopNames().contains(name.getAsString()))
	                     					{	
	                     						Subwayline.addStop(name.getAsString());
	                     						Subwayline.increaseStops();
	                     						Stop newAdd = new Stop();
	                     						newAdd.setName(name.getAsString());
	                     						newAdd.setSubwayLine(Subwayline.getName());
	                     						Stops.add(newAdd);
	                     					}
	                 					}
	                     				//Adding Copley Stop to all Green Lines 
	                     				else if((name.getAsString().equalsIgnoreCase("Copley")) && (Subwayline.getName().contains("Green Line")) )
	                 					{	

	                     					if(!Subwayline.getStopNames().contains(name.getAsString()))
	                     					{	
	                     						Subwayline.addStop(name.getAsString());
	                     						Subwayline.increaseStops();
	                     						Stop newAdd = new Stop();
	                     						newAdd.setName(name.getAsString());
	                     						newAdd.setSubwayLine(Subwayline.getName());
	                     						Stops.add(newAdd);
	                     					}
	                 					}
	                     				//Adding Kenmore To Green Line D
	                     				else if((name.getAsString().equalsIgnoreCase("Kenmore")) && (Subwayline.getName().contains("Green Line - (D)")) )
	                 					{	
	                     					if(!Subwayline.getStopNames().contains(name.getAsString()))
	                     					{	
	                     						Subwayline.addStop(name.getAsString());
	                     						Subwayline.increaseStops();
	                     						Stop newAdd = new Stop();
	                     						newAdd.setName(name.getAsString());
	                     						newAdd.setSubwayLine(Subwayline.getName());
	                     						Stops.add(newAdd);
	                     					}
	                 					}
	                     				
	                     	        }
	                     			
	                     		}
	                     	}
	                     }
	                  }
	                }
	            }
	           //The Max Number of Stops in a Subway Train
	           int MostStopscount = SubwayLines.get(0).getStops();
	           
	           //The Least Number of Stops in a Subway Train
	           int LeastStopscount = SubwayLines.get(0).getStops();
	           
	           //Find the Max and Least Number of Stops in a Subway Train
				for (Subway Subwayline : SubwayLines)
		        {    		
					int temp = Subwayline.getStops();
					if(temp > MostStopscount)
					{
						MostStopscount = temp;
					}
					
					if(temp < LeastStopscount)
					{
						LeastStopscount = temp;
					}
		        }
				
				//Answer To Question 2
				for (Subway Subwayline : SubwayLines)
		        {    		
					
					if(MostStopscount ==  Subwayline.getStops())
					{
						System.out.println("The Subway Routes with the most amount of stops is " + Subwayline.getName());
						System.out.println("with " + Subwayline.getStops() + " Stops");
						System.out.println("");
					}
					
					if(LeastStopscount == Subwayline.getStops())
					{
						System.out.println("The Subway Routes with the least amount of stops is " + Subwayline.getName());
						System.out.println("with " + Subwayline.getStops() + " Stops");
						System.out.println("");
					}
		        }
	        } 
	            //Checking if the API Key Works
	            catch (Exception e) {
				System.out.println(e);
			}
		}
		
		public static void Question2b() {
			//List of Connector Stop Names
			List<String> stopsnames = new LinkedList<String>();
			
			//Comparing 2 Subways' Stops List for Name Matches
			for (Subway Subwayline : SubwayLines)
	        {    		
				for (Subway Sub : SubwayLines)
		        {    		
					if(!Sub.equals(Subwayline))
					{
						for (String Stop : Subwayline.getStopNames())
				        { 
							for (String S : Sub.getStopNames())
					        { 
								//If there is a Match, this code will run
								if(Stop.equalsIgnoreCase(S))
								{
									//Check if the Stop Name was added to the List Already
									
									if(!stopsnames.contains(Stop))
									{
										//If Stop Name is not added, the Stop Name and Associate Subway Route will be add to Connector List
										Connector add = new Connector();
									     add.setName(Stop);
												if(!add.getSubwayList().contains(Sub))
												{
													 add.addSubway(Sub);
												}
												if(!add.getSubwayList().contains(Subwayline))
												{
													 add.addSubway(Subwayline);
												}
												Sub.addConnector(add);
												Subwayline.addConnector(add);
												Connector.add(add);
										        stopsnames.add(Stop);
									}
									//If Stop Name is added, just Associate Subway Route will be add to Connector List
									else
									{
										for (Connector Connect: Connector)
									    {
										   if(Connect.getName().equalsIgnoreCase(Stop))
										   {
											   if(!Connect.getSubwayList().contains(Sub))
												{
												   Connect.addSubway(Sub);
												   Sub.addConnector(Connect);
												}
												if(!Connect.getSubwayList().contains(Subwayline))
												{
													Connect.addSubway(Subwayline);
													Subwayline.addConnector(Connect);
												}
										   }
										  
										}
									}
								}
					        }
				        }
						
					}
					
		        }
				
	        }
			//Answer To Question 2
			System.out.println("Connector Stops and Their Associated Subway Routes:");
			for (Connector Connect : Connector)
		        { 
					System.out.println(Connect.getName()+":");
					List<Subway> ConnectorSubwayList = Connect.getSubwayList();
					for (Subway SubwayList : ConnectorSubwayList)
			        {
						System.out.println(SubwayList.getName());
			        }
					System.out.println("");
		        }
				//Space between Questions
		        System.out.println("");  
		}
		
		public static void Question3() {
			//Introduction
			 System.out.println(""); 
			 System.out.println("The Answer to Question 3 -");  
			 
			//List of Subways need to get from the Beginning stop to the Destination Stop
			 List<Subway> SubwayTravel = new LinkedList<Subway>();
			 
			//Check if User input Beginning Stop is valid Stop
			 boolean StartStopNameIsVaild = false;  
			 
			//Check if User input Destination Stop is valid Stop
			 boolean DestinationStopNameIsVaild = false; 
			 
			//Check in User Beginning Stop is a Connector Stop
			 boolean StartStopNameIsConnector = false; 
			 
			//Check in User Destination Stop is a Connector Stop
			 boolean DestinationStopNameIsConnector = false; 
			
			//User input Beginning Stop 
			 String StartStopName = ""; 
			 
			//User input Destination Stop 
			 String DestinationStopName = "";  
			    
			while(!StartStopNameIsVaild)
			{ 
				 // Create a Scanner object for user input
				    Scanner myObj = new Scanner(System.in); 
				    System.out.println("Enter the Starting Stop");
				     
				    // Read user input
				    StartStopName = myObj.nextLine();
			        
			    
			    //Checking if Beginning Stop is in the master(MBTA) Stop list     
				for (Stop S : Stops)
		        { 
					if(S.getName().equalsIgnoreCase(StartStopName))
					{
						StartStopNameIsVaild = true;
					}   
		        }
				
				//Informs the User the Stop Name is Invalid
				if(!StartStopNameIsVaild)
				{
					System.out.println(""); 
					System.out.println("Stop Name is Invalid");
					System.out.println(""); 
				}
				
			}
			System.out.println(""); 
			while(!DestinationStopNameIsVaild)
			{
				 // Create a Scanner object for user input
				   Scanner myObj = new Scanner(System.in); 
				    System.out.println("Enter the Destination Stop");
				    
				    // Read user input
				    DestinationStopName = myObj.nextLine();
				       
			                
			        
			   //Checking if Destination Stop is in the master(MBTA) Stop list  
			        for (Stop S : Stops)
		        { 
					if(S.getName().equalsIgnoreCase(DestinationStopName))
					{
						DestinationStopNameIsVaild = true;
					}
		        }
				
				//Informs the User the Stop Name is Invalid
				if(!DestinationStopNameIsVaild)
				{
					System.out.println("");  
					System.out.println("Stop Name is Unvaild");
					System.out.println(""); 
				}
			}
			
			//Checking if Beginning Stop is a Connector Stop
			for(Connector C: Connector)
			{
				if(C.getName().equalsIgnoreCase(StartStopName))
				{
					StartStopNameIsConnector = true;
				}
			}
			
			 //Checking if Destination Stop is a Connector Stop
			for(Connector C: Connector)
			{
				if(C.getName().equalsIgnoreCase(DestinationStopName))
				{
					DestinationStopNameIsConnector = true;
				}
			}
				
			//Using the format: (Beginning Stop, Destination Stop); This is the code for: (Normal Stop, Normal Stop) and (Normal Stop, Connector Stop)
			if(!StartStopNameIsConnector)
			{
				//Getting the Subway Line Associated with the Beginning Stop
				String StartSubwayLine = "";
				for (Stop S : Stops)
		        { 
					if(S.getName().equalsIgnoreCase(StartStopName))
					{
						StartSubwayLine = S.getSubwayLine();
					}   
		        }
				
				//List of a List of Subways need to get from the Beginning stop to the Destination Stop, using different Destination Stops
				List<List <Subway>> DestinationSubways = new LinkedList<List <Subway>>();
				
				//This is the code for: (Normal Stop, Normal Stop)
				if(!DestinationStopNameIsConnector)
				{
					//Getting the Subway Line Associated with the Destination Stop
					String DestinationSubwayLine = "";
					for (Stop S : Stops)
			        { 
						if(S.getName().equalsIgnoreCase(DestinationStopName))
						{
							DestinationSubwayLine = S.getSubwayLine();
						}   
			        }
					
					//Checking if Beginning stop and Destination Stop are in the same line
					for (Stop S : Stops)
			        { 
						if(S.getName().equalsIgnoreCase(DestinationStopName))
						{
							if(S.getSubwayLine().equalsIgnoreCase(StartSubwayLine))
							{
								for(Subway Subway : SubwayLines)
								{
									if(Subway.getName().equalsIgnoreCase(StartSubwayLine))
									{ 
										SubwayTravel.add(Subway);
									}
								}				
							}
							else
							{
								//Entering the Beginning stop's Subway Line and Destination Stop's Subway Line in Find Route Function
								 Subway Start = new Subway();
								 Subway End = new Subway();
								 for(Subway Subway : SubwayLines)
									{
										if(Subway.getName().equalsIgnoreCase(StartSubwayLine))
										{
											Start = Subway;
											
										}
										if(Subway.getName().equalsIgnoreCase(DestinationSubwayLine))
										{
											End = Subway;
											
										}
									}
								 List<Subway> R = new LinkedList<Subway>();
								 SubwayTravel = MBTA.FindRoute(Start, End, R);	
							}
						}   
			        }
				}
				
				//This is the code for: (Normal Stop, Connector Stop)
				else
				{	
					//Getting the Subway Lines List Associated with the Destination Stop	
					for(Connector Connect: Connector)
					{
						if(Connect.getName().equalsIgnoreCase(DestinationStopName))
						{
							for(Subway SC: Connect.getSubwayList())
							{
								
								//Checking if Beginning stop and Destination Stop are in the same line
								if(SC.getName().equalsIgnoreCase(StartSubwayLine))
								{
									for(Subway Subway : SubwayLines)
									{
										if(Subway.getName().equalsIgnoreCase(StartSubwayLine))
										{
											SubwayTravel.add(Subway);
										}
									}				
								}
								else
								{
									//Entering the Beginning stop's Subway Line and Destination Stop's Subway Line(s) in Find Route Function
									 Subway Start = new Subway();
									 Subway End = new Subway();
									 for(Subway Subway : SubwayLines)
										{
											if(Subway.getName().equalsIgnoreCase(StartSubwayLine))
											{
												Start = Subway;
											}
											if(Subway.getName().equalsIgnoreCase(SC.getName()))
											{
												End = Subway;
											}
										}
									
									//New List of Subways need to get from the Beginning stop to the Destination Stop using different Destination Stop's Subway Line(s)
									 List<Subway> R = new LinkedList<Subway>();
									 List<Subway> DestinationSubway = new LinkedList<Subway>();
									 DestinationSubway = MBTA.FindRoute(Start, End, R);	
									 SubwayTravel = DestinationSubway;
									 DestinationSubways.add(DestinationSubway);
								}
							}
						}
					}
					
					//Setting the smallest Subway Route result as the main SubwayTravel
					for (List<Subway> DS :  DestinationSubways)
					{
						if(DS.size() < SubwayTravel.size() )
						{
							SubwayTravel = DS;
						}
					}
				}
			}
			
			//This is the code for: (Connector Stop, Normal Stop)
			else
			{
				//List of a List of Subways need to get from the Beginning stop to the Destination Stop, using different Beginning and Destination Stops 
				List<List <Subway>> StartSubways = new LinkedList<List <Subway>>();
				if(!DestinationStopNameIsConnector)
				{
					//Getting the Subway Line Associated with the Destination Stop
					String DestinationSubwayLine = "";
					for (Stop S : Stops)
			        { 
						if(S.getName().equalsIgnoreCase(DestinationStopName))
						{
							DestinationSubwayLine = S.getSubwayLine();
						}   
			        }
					
					for(Connector Connect: Connector)
					{
						if(Connect.getName().equalsIgnoreCase(StartStopName))
						{
							for(Subway DC: Connect.getSubwayList())
							{
								//Checking if Beginning stop and Destination Stop are in the same line
								if(DC.getName().equalsIgnoreCase(DestinationSubwayLine))
								{
									for(Subway Subway : SubwayLines)
									{
										if(Subway.getName().equalsIgnoreCase(DestinationSubwayLine))
										{
											SubwayTravel.add(Subway);
										}
									}				
								}
								else
								{
									//Entering the Beginning stop's Subway Line(s) and Destination Stop's Subway Line in Find Route Function
									 Subway Start = new Subway();
									 Subway End = new Subway();
									 for(Subway Subway : SubwayLines)
										{
											if(Subway.getName().equalsIgnoreCase(DC.getName()))
											{
												Start = Subway;
											}
											if(Subway.getName().equalsIgnoreCase(DestinationSubwayLine))
											{
												End = Subway;
											}
										}
									
									 //New List of Subways need to get from the Beginning stop to the Destination Stop using different Beginning Stop's Subway Line(s)
									 List<Subway> R = new LinkedList<Subway>();
									 List<Subway> StartSubway = new LinkedList<Subway>();
									 StartSubway = MBTA.FindRoute(Start, End, R);	
									 SubwayTravel = StartSubway;
									 StartSubways.add(StartSubway);
								}
							}
						}
					}
					//Setting the smallest Subway Route result as the main SubwayTravel
					for (List<Subway> SS :  StartSubways)
					{
						if(SS.size() < SubwayTravel.size() )
						{
							SubwayTravel = SS;
						}
					}
				}
				
				//This is the code for: (Connector Stop, Connector Stop)
				else
				{
					for(Connector StartConnect: Connector)
					{
						if(StartConnect.getName().equalsIgnoreCase(StartStopName))
						{
							for(Connector DestinationConector : Connector)
							{
								if(DestinationConector.getName().equalsIgnoreCase(DestinationStopName))
								{
									for(Subway SC: StartConnect.getSubwayList())
										{
										for(Subway DC: DestinationConector.getSubwayList())
										{
											//Checking if Beginning stop and Destination Stop are in the same line
											if(DC.getName().equalsIgnoreCase(SC.getName()))
												{ 
													for(Subway Subway : SubwayLines)
														{
															if(Subway.getName().equalsIgnoreCase(SC.getName()))
																{
																   List<Subway> StartSubway = new LinkedList<Subway>();
																   StartSubway.add(Subway);
																   SubwayTravel = StartSubway;
																   StartSubways.add(SubwayTravel);
																}
														}				
													}
												else
													{
													//Entering the Beginning Stop's Subway Line(s) and Destination Stop's Subway Line(s) in Find Route Function	
														Subway Start = new Subway();
														Subway End = new Subway();
														for(Subway Subway : SubwayLines)
														{
														 if(Subway.getName().equalsIgnoreCase(SC.getName()))
														 {
															 Start = Subway;
														 }
														 if(Subway.getName().equalsIgnoreCase(DC.getName()))
														 {
															 End = Subway;
														 }
														}
									
													 //New List of Subways need to get from the Beginning stop to the Destination Stop using different Beginning and Destination Stop's Subway Line(s)
														List<Subway> R = new LinkedList<Subway>();
														List<Subway> StartSubway = new LinkedList<Subway>();
														StartSubway = MBTA.FindRoute(Start, End, R);	
														SubwayTravel = StartSubway;
														StartSubways.add(StartSubway);
													}
										}
										}
									}					
							 }
						 }
					 }
				 }
				//Setting the smallest Subway Route result as the main SubwayTravel
				for (List<Subway> SS :  StartSubways)
				{
					if(SS.size() < SubwayTravel.size() )
					{
						SubwayTravel = SS;
					}
				}
			}
			
			//Answer to Number 3
			System.out.println(""); 
			System.out.print(StartStopName + " to " + DestinationStopName + " -> ");  
			for (Subway SubTravel : SubwayTravel)
	        { 
				System.out.print(SubTravel.getName()); 
				if( SubwayTravel.size() > 1)
				{
					System.out.print(", "); 
				}
	        }
			System.out.println(""); 

			//Asking the User if they finish their search:
			
			//Create a Scanner object for user input
		      Scanner myObj = new Scanner(System.in); 
		      System.out.println("Do you want to enter another Start/Destination Stop? (Y/N) "); 
		    
		    // Read user input
		      String redo = myObj.nextLine();
		     
		      //Check if User wanted to repeat the function
		      if(redo.contains("Y"))
		      {
		    	//Restarts Question 3  
		        MBTA.Question3(); 
		      }
		     else
		      {
		    	 //Conclusion
		    	 myObj.close();
		    	 System.out.println("Goodbye"); 
		      }
		    
		}
		
		//Function that takes the Beginning Subway Line and Destination Subway Line and returns a the list that display the Subways needed to get from one line to the other
		public static List<Subway> FindRoute(Subway Start, Subway End, List<Subway> R)
		{
			//List of Subways needed to get from the Beginning Subway Line to the Destination Subway Line
			List<Subway> Route = R;
			
			//Adding the Start Subway Stop to the List
			if(!Route.contains(Start))
			{
				Route.add(Start);
			}
			
			//The Check for if the Recursion Loop can exit
			boolean exit = false;
			
			for(Connector Connect: Connector)
			{  
				//Check if a Connector Stop contains both Beginning Subway Line to the Destination Subway Line
				if(Connect.getSubwayList().contains(Start) && Connect.getSubwayList().contains(End))
				{
					if(!Route.contains(End))
					{
						Route.add(End);
					}
					exit = true;
				}
			}
			//Checks if the Recursion Loop can exit
			if(exit)
			{
				return Route;
			}
			else
			{
				for (Connector C : Connector)
				{
					//Check if there is a Connect that contains the Beginning Subway Line but not the Destination Subway Line
					if(C.getSubwayList().contains(Start) && !exit)
					{
						for(Subway SC : C.getSubwayList())
						{
							//Check the Connector Stop is not a Previous Connector Stop that was added to the Route List
							if(!SC.equals(Start) && !exit && !Route.contains(SC))
							{   
								//Calls the Function again
								Route = MBTA.FindRoute(SC, End, Route);
								
								//Check if a Previous Recursion completed and added the Destination Subway Line to the Route List
								if(Route.contains(End))
								{
									return Route;
								}
								else
								{
									//If the Previous Recursion failed, the connector stop is removed from the List
									Route.remove(SC);
								}
							}
							
						}
					}
				}
			}
			
			//Return Route
			return Route;
		}
		
	}
