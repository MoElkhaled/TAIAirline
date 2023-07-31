class Edges implements Comparable<Edges>{
    private String startingCity;
    private int weight;
    private String connectedCity;

    public void setStartingCity(String startingCity) {
        this.startingCity = startingCity;
    }
    public void setConnectedCity(String connectedCity) {
        this.connectedCity = connectedCity;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }
    public String getConnectedCity() {
        return connectedCity;
    }
    public String getstartingCity() {
        return startingCity;
    }
    @Override
    public int compareTo(Edges o) {
        if(this.weight > o.weight){
            return 1;
        }
        else if(this.weight < o.weight){
            return -1;
        }
        else
            return 0;
    }
}

