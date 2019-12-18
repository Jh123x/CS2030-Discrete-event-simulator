abstract class Customer implements ICustomer, Comparable<ICustomer>{
    protected double arrivalTime;
    protected double waitTime = 0;
    protected int ID;
    protected CustomerType type;
    protected double reTime = -1;

    //Constructor
    public Customer(int ID, double arrivalTime){
        this.ID = ID;
        this.arrivalTime = arrivalTime;
    }

    //Getters 
    public int getID(){
        return this.ID;
    }
    public double getWaitTime(){
        return this.waitTime;
    }
    public double getArrivalTime(){
        return this.arrivalTime;
    }
    public void doServed(double time){
        if(this.getArrivalTime() < time){
            this.waitTime += (time - this.getArrivalTime());
        }else if(time == this.getArrivalTime()){
            this.waitTime = 0;
        }else{
            assert false;
        }
    }
    public CustomerType getType(){
        return this.type;
    }
    public double getReTime() {
        return this.reTime;
    }

    //Updater
    @Override
    public void reArrival() {
        if(reTime > 0){
            this.arrivalTime += this.reTime;
        }
    }

    //Overridden function
    @Override
    public int compareTo(ICustomer c){
        int result = this.getType().compareTo(c.getType());
        if(result == 0){
            if (this.getArrivalTime() < c.getArrivalTime()){
                return -1;
            }
            else if(this.getArrivalTime() == c.getArrivalTime()){
                return 0;
            }
            else{
                return 1;
            }
        }
        return result;
    }

    @Override
    public String toString(){
        return String.format("%d", this.getID());
    }
}