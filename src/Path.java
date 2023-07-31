import java.util.ArrayList;

public class Path {
    private ArrayList<Vertex> path = new ArrayList<>();
    private int miles;
    private double cost;
    public void setPath(ArrayList<Vertex> givenPath){
        for(int i = 0; i < givenPath.size();i++){
            path.add(givenPath.get(i));
        }
    }
    public ArrayList<Vertex> getPath(){
        return path;
    }
    public void printPath(){
        for(int i = 0; i < path.size();i++){
            if(i == path.size()-1){
                System.out.print(path.get(i).getName());
            }
            else
                System.out.print(path.get(i).getName() + " " + "-->" + " ");
        }
        System.out.println(": "+ getMiles() + " miles:" + "$" +getCost());

    }
    public void setMiles(int miles1){
        miles = miles1;
    }
    public int getMiles(){
        return miles;
    }
    public void setCost(double cost1){
        cost = cost1;
    }
    public double getCost(){
        return cost;
    }
}
