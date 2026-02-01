import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
public class AllPaths {
    private static ArrayList<Vertex> startingCities = new ArrayList<>();
    private static ArrayList<String> startingCitiesStrings = new ArrayList<>();
    private static ArrayList<Vertex> path = new ArrayList<>();
    private static ArrayList<Path> allPath = new ArrayList<>();
    private static ArrayList<String> pathStrings = new ArrayList<>();


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
        InputStream stream = AllPaths.class.getResourceAsStream("/FlightPaths.csv");
        Scanner Freader = new Scanner(stream);
        Freader.useDelimiter("\n");
        while (Freader.hasNextLine()) {
            String[] eachLine = Freader.nextLine().split(",");
            Vertex tempV = new Vertex();
            startingCities.add(tempV);
            tempV.setName(eachLine[0]);
        }
    }
    public static void initiateCitiesStrings() throws FileNotFoundException {
        InputStream stream = AllPaths.class.getResourceAsStream("/FlightPaths.csv");
        Scanner Freader = new Scanner(stream);
        Freader.useDelimiter("\n");
        while (Freader.hasNextLine()) {
            String[] eachLine = Freader.nextLine().split(",");
            startingCitiesStrings.add(eachLine[0]);
        }
    }
    public static void ReadFile(String userSource, String userDest, String userChoice) throws FileNotFoundException {

        InputStream stream = AllPaths.class.getResourceAsStream("/FlightPaths.csv");
        Scanner newReader = new Scanner(stream);
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
            pathStrings.add(allPath.get(i).returnPath());
        }

    }
    public static String starter(String origin,String destination, String seatingClass) throws FileNotFoundException{
        // THIS IS WHATS BREAKING THE CODE
        allPath.clear();
        path.clear();
        pathStrings.clear();

        initiateCities();
      System.out.println("Welcome to Texas All In Airlines");
      Scanner sc = new Scanner(System.in);


      System.out.println("Your input " + origin.toUpperCase());

      System.out.println("Your input " + destination.toUpperCase());

      ReadFile(origin,destination,seatingClass);

      return pathStrings.toString();
    }

    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;

        for (String pair : query.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(
                        URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8)
                );
            }
        }
        return params;
    }

    public static void main(String[] args) throws IOException {
        initiateCitiesStrings();
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/flight-paths", exchange -> {
            if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());

            String origin = params.get("origin");
            String destination = params.get("destination");
            String seatingClass = params.get("class");
            if(!startingCitiesStrings.contains(origin)){
                String error = "Invalid origin";
                exchange.sendResponseHeaders(400, error.getBytes().length);
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();
                return;
            }
            if(!startingCitiesStrings.contains(destination)){
                String error = "invalid destination";
                exchange.sendResponseHeaders(400, error.getBytes().length);
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();
                return;
            }
            // Validate required fields
            if (origin == null || destination == null || seatingClass == null) {
                String error = "Missing required query parameters: origin, destination, class";
                exchange.sendResponseHeaders(400, error.getBytes().length);
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();
                return;
            }

            // THIS is where your existing logic goes
            String response = starter(origin,destination,seatingClass);

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.start();
        System.out.println("API running on http://localhost:8080/flight-paths");
    }

}
