import java.util.*;

public class Vertex {
    private ArrayList<Vertex> adjVertex = new ArrayList<>();
    private ArrayList<Integer> milesFromAdj = new ArrayList<>();
    private boolean visted = false;
    private String name;

    public boolean getVisit() {
        return visted;
    }

    public void setVisit(boolean checker) {
        visted = checker;
    }

    public ArrayList<Vertex> getAdjVertex() {
        return adjVertex;
    }

    public void addAdjVertex(Vertex v) {
        adjVertex.add(v);
    }
    public ArrayList<Integer> getMilesFromAdj(){
        return milesFromAdj;
    }
    public void addMiles(int miles){
        milesFromAdj.add(miles);
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
