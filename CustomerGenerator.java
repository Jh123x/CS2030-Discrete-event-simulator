import java.util.*;
public class CustomerGenerator extends IGenerator{
    private double probElderly;
    private double arrivalRate;
    private double rearriveRate;
    private Random typeRand;
    private Random custRand;
    private int currID = 0;
    private double preTime = 0;

    public CustomerGenerator(Long seed, double probElderly, double arrivalRate, double rearriveRate){
        this.arrivalRate = arrivalRate;
        this.probElderly = probElderly;
        this.rearriveRate = rearriveRate;
        this.typeRand = new Random(seed);
        this.custRand = new Random(seed + 1);
    }

    public ICustomer nextCustomer(){
        double type = typeRand.nextDouble();
        double arrivalTime = this.preTime + rng(this.custRand,this.arrivalRate);
        this.preTime = arrivalTime;
        if(type > probElderly){
            double rearrival = rng(this.custRand,this.rearriveRate);
            return new GeneralCustomer(this.currID++,arrivalTime,rearrival);
        }else{
            return new ElderlyCustomer(this.currID++, arrivalTime);
        }
    }
}