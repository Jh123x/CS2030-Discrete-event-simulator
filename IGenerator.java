import java.util.Random;

abstract class IGenerator{
    public double rng(Random rand, double rate){
        return -Math.log(rand.nextDouble())/rate;
    }
}