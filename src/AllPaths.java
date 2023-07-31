import java.util.*;
import java.io.*;
public class AllPaths {
    private static ArrayList<Vertex> startingCities = new ArrayList<>();
    private static ArrayList<Vertex> path = new ArrayList<>();
    private static ArrayList<Path> allPath = new ArrayList<>();


    public static void findPathH(Vertex source, Vertex dest,String userChoice){
        path.add(source);
        int miles = 0;
        findPath(source,dest,miles,userChoice);
    }

    public static void findPath(Vertex source, Vertex dest,int miles,String userChoice) {
        int indexOfSource = startingCities.indexOf(source);;

        //check if we've reached our destination
        if (source.equals(dest)) {
            //temp path object to hold out current path
            Path curPath = new Path();
            curPath.setPath(path);
            //add the path only if its less that 5 stops
            if(curPath.getPath().size() <= 3){
                curPath.setMiles(calcMiles(curPath));
                //System.out.println("in if statement "+ userChoice );
                curPath.setCost(calcCost(curPath,userChoice));
                allPath.add(curPath);
            }
            return;
        }

        //set the source to be visited
        startingCities.get(indexOfSource).setVisit(true);

        //loop through the adj list using temp vertex
        for (Vertex temp : startingCities.get(indexOfSource).getAdjVertex()) {
            //find the index of the adjIndex
            int adjIndex = 0;

            adjIndex = startingCities.get(indexOfSource).getAdjVertex().indexOf(temp);
            if (!startingCities.get(indexOfSource).getAdjVertex().get(adjIndex).getVisit()) {
                path.add(temp);
                miles += startingCities.get(indexOfSource).getMilesFromAdj().get(adjIndex);
                findPath(temp,dest,miles,userChoice);
                path.remove(temp);
            }
        }
        startingCities.get(indexOfSource).setVisit(false);

    }

    public static int calcMiles(Path tPath){
        int miles = 0;
        for(int i = 0; i < tPath.getPath().size() - 1; i++){
            Vertex currentVertex = tPath.getPath().get(i);
            Vertex adjVertex = tPath.getPath().get(i+1);
            int indexOfAdj = currentVertex.getAdjVertex().indexOf(adjVertex);
            miles += currentVertex.getMilesFromAdj().get(indexOfAdj);
        }
        tPath.setMiles(miles);
        return miles;
    }
    public static double calcCost(Path tPath,String userChoice){
        double cost = 0;
        if(userChoice == "ECONOMY"){
            final double baseEconCost = .50;
            cost = (tPath.getMiles() * baseEconCost);
        }
        else{
            final double baseFirstClass = 1.00;
            cost = (tPath.getMiles() * baseFirstClass);
        }
        return cost;
    }
    public static void initiateCities() throws FileNotFoundException {
        Scanner Freader = new Scanner(new File("/Users/moelkhaled/Desktop/TAIAirline/FlightPaths.csv"));
        Freader.useDelimiter("\n");
        while (Freader.hasNextLine()) {
            String[] eachLine = Freader.nextLine().split(",");
            Vertex tempV = new Vertex();
            startingCities.add(tempV);
            tempV.setName(eachLine[0]);
        }
    }
    public static void ReadFile(String userSource, String userDest, String userChoice) throws FileNotFoundException {

        Scanner newReader = new Scanner(new File("/Users/moelkhaled/Desktop/TAIAirline/FlightPaths.csv"));
        newReader.useDelimiter("\n");
        int indexOfCurrentStartingCity = 0;
        while (newReader.hasNextLine()) {
            try {
                //Split each line into an array using ","
                String[] eachLine = newReader.nextLine().split(",");
                //startingCities are always the first of each line
                String vertexName = eachLine[0];
                Vertex v = new Vertex();
                v.setName(vertexName);
                int index = 0;
                for (int i = 1; i < eachLine.length; i = i + 2) {
                    for (int k = 0; k < startingCities.size(); k++) {
                        if (startingCities.get(k).getName().equals(eachLine[i])) {
                            index = k;
                        }
                    }
                    startingCities.get(indexOfCurrentStartingCity).addAdjVertex(startingCities.get(index));
                    startingCities.get(indexOfCurrentStartingCity).addMiles(Integer.parseInt(eachLine[i+1]));

                }
            }

            catch (NoSuchElementException e) {
                System.out.println("Error caught" + e + "\ncheck for empty lines");
            }
            indexOfCurrentStartingCity++;
        }

        int sourceIndex = 0;
        int destIndex = 0;
        //get the index of the source/dest using their names
        for (int i = 0; i < startingCities.size(); i++) {
            if (startingCities.get(i).getName().equals(userSource)) {
                sourceIndex = i;
            }
            if (startingCities.get(i).getName().equals(userDest)) {
                destIndex = i;
            }
        }

        findPathH(startingCities.get(sourceIndex), startingCities.get(destIndex),userChoice);
        for(int i = 0; i < allPath.size();i++){
            allPath.get(i).printPath();
        }
    }
    public static void starter() throws FileNotFoundException{
      initiateCities();

      System.out.println("Welcome to Texas All In Airlines");
      Scanner sc = new Scanner(System.in);
      String origin = "";
      int indexCheck = -1;
      while(true) {
          System.out.println("Please enter an origin: ");
          origin = sc.nextLine();
          for (int k = 0; k < startingCities.size(); k++) {
              if (startingCities.get(k).getName().equals(origin)) {
                  indexCheck = k;
              }
          }
          if(indexCheck >= 0){
              break;
          }
          else{
              System.out.println("Sorry! The entered city is not supported by Texas All In Airlines!");
              System.out.println("Please enter a new city");
          }
      }

      System.out.println("Your input " + origin.toUpperCase());
      String destination = "";
      indexCheck = -1;
      while(true) {
          System.out.println("Please enter a destination: ");
          destination = sc.nextLine();
          for (int k = 0; k < startingCities.size(); k++) {
              if (startingCities.get(k).getName().equals(destination)) {
                  indexCheck = k;
              }
          }
          if(indexCheck >= 0){
              break;
          }
          else{
              System.out.println("Sorry! The entered city is not supported by Texas All In Airlines!");
              System.out.println("Please enter a new city");
          }
      }
      System.out.println("Your input " + destination.toUpperCase());
      System.out.println("Would you like Economy or First Class seating? ");
      String seatingClass = " ";
      int userChoice = 0;
      while(true) {
          System.out.println("Please Enter 1 for Economy or 2 for First Class.");
          userChoice = sc.nextInt();
          if(userChoice != 1 && userChoice != 2){
              System.out.println("Sorry please enter 1 or 2 only");
          }
          else if(userChoice == 1){
              seatingClass = "ECONOMY";
              break;
          }
          else if(userChoice == 2){
              seatingClass = "FIRST CLASS";
              break;
          }
      }
      ReadFile(origin,destination,seatingClass);
    }
    public static void main(String[] args) throws FileNotFoundException {
        starter();
    }

}
