//
// Name: Nguyen, Justin
// Project: 5
// Due: 12/08/2023
// Course: cs-2400-02-f23
//
// Description:
// A class that lets the user enter an airport code and prints out the
// airport information. It also finds the shortest distance between two airports.
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {

	public static void main(String[] args) 
	{
		String input;
		
		DictionaryInterface<String, String> airportDictionary = new HashedDictionary<>();
		DirectedGraph<String> map = new DirectedGraph<>();
		
		try
		{
			Scanner scan = new Scanner(System.in);

			Scanner scan2 = new Scanner(new File("airports.csv"));
			Scanner scan3 = new Scanner(new File("distances.csv"));

			//Reading in airports.csv
            while(scan2.hasNextLine())
			{
				String line = scan2.nextLine();
				String[] parts = line.split(",");
				if (parts.length >= 2) {
                    String airportCode = parts[0].trim();
                    String airportDescription = parts[1].trim();
                    airportDictionary.add(airportCode, airportDescription);
    
				}
			}
            
            //Reading in distances.csv
            while(scan3.hasNextLine())
            {
				String airportsInput = scan3.nextLine();
				String[] parts = airportsInput.split(",");
				if (parts.length >= 3) {
                    String airportFrom = parts[0].trim();
                    String airportTo = parts[1].trim();
                    Double distance = Double.parseDouble(parts[2].trim());
                    map.addVertex(airportFrom);
                    map.addVertex(airportTo);
                    map.addEdge(airportFrom, airportTo, distance);
				}
            }
		
			System.out.println("Airports v0.1 by J. Nguyen\n");
			System.out.print("Command? ");
			input = scan.nextLine();
			
			while(!input.toUpperCase().equals("E"))
			{
			
				if(input.toUpperCase().equals("H"))
				{
					System.out.println("Q Query the airport information by entering the airport code.");
					System.out.println("D Find the minimum distance between two airports.");
					System.out.println("H Display this message.");
					System.out.println("E Exit.");
					System.out.print("Command? ");
				}
				
				else if(input.toUpperCase().equals("Q"))
				{
					System.out.print("Airport code? ");
                    String airportCode = scan.nextLine();
					String airportDescription = airportDictionary.getValue(airportCode);
					
					if(airportDescription != null)
						System.out.println(airportDescription);
					else
						System.out.println("Airport code unknown");
					 
					System.out.print("Command? ");
				
				}
				
				else if(input.toUpperCase().equals("D"))
				{
					System.out.print("Airport codes from to? " );
					StackInterface<String> stack = new LinkedStack<>();
					String airportFrom = "";
					String airportTo = "";
                    String airportCodes = scan.nextLine();
                    String[] parts = airportCodes.split(" ");
    				if (parts.length >= 2) {
                        airportFrom = parts[0].trim();
                        airportTo = parts[1].trim();
    				}
    				
    				if(airportDictionary.getValue(airportTo) == null || airportDictionary.getValue(airportFrom) == null)
    				{
    					System.out.println("Airports not connected");
						System.out.print("Command? ");
    				}
    				else
    				{
	    				int distance = (int)map.getCheapestPath(airportFrom, airportTo, stack);
	    				if(distance == 0)
	    				{
	    					System.out.println("Airports not connected");
							System.out.print("Command? ");
	    				}
	    				else
	    				{
							System.out.println("The minimum distance between " + airportDictionary.getValue(airportFrom) + " and " + 
									airportDictionary.getValue(airportTo) + " is " + distance + " through the route:");
							while(!stack.isEmpty())
								System.out.println(airportDictionary.getValue(stack.pop()));
							System.out.print("Command? ");
	    				}
    				}
				}
				else
				{
					System.out.println("Invalid command");
					System.out.print("Command? ");
				}
				
				input = scan.nextLine();
			}
			scan.close();
		}
		catch(FileNotFoundException  e) 
		{
			 System.out.println("Error reading file: " + e.getMessage());
		}

	}

}