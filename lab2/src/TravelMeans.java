public class TravelMeans{
    private String name;
    private double lowestSpeed;
    private double highestSpeed;

    public TravelMeans(String name, double lowestSpeed, double highestSpeed){
        this.name = name;
        //швидкості в км/год
        this.lowestSpeed = lowestSpeed;
        this.highestSpeed = highestSpeed;
    }

    public String getName(){
        return this.name;
    }

    public double getLowestSpeed(){
        return this.lowestSpeed;
    }

    public double getHighestSpeed(){
        return this.highestSpeed;
    }

    @Override
    public String toString(){
        return name + ", lowest speed:" + lowestSpeed + ", highest speed:" + highestSpeed;
    }
}
