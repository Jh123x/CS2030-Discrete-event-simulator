import java.util.Random;

class ServerRandomizer extends IGenerator{
    private double restRate;
    private Random rand;

    public ServerRandomizer(long seed, double restRate){
        this.rand = new Random(seed+2); 
        this.restRate = restRate;
    }

    public double toRest(){
        return this.rand.nextDouble();
    }

    public double restTime(){
        return this.rng(this.rand,this.restRate);
    }
}