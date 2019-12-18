import java.util.*;
abstract class Server implements IServer, Comparable<Server>{
    public static ServerRandomizer randomizer;
    protected SortedQueue<ICustomer> custQueue;
    protected SortedQueue<ICustomer> currServing; 
    protected int ID;
    protected ServerType type;
    protected int maxSCust;

    public Server(int id,int maxSCust, int qSize, ServerType type){
        this.custQueue = new SortedQueue<ICustomer>(qSize);
        this.currServing = new SortedQueue<ICustomer>(maxSCust);
        this.ID = id;
        this.type = type;
        this.maxSCust = maxSCust;
    }

    public double getEarliestServed(){
        Optional<ICustomer> c = this.currServing.getStream().min(new Comparator<ICustomer>(){
            public int compare(ICustomer c1, ICustomer c2){
                double sTime1 = c1.getArrivalTime() + c1.getWaitTime();
                double sTime2 = c2.getArrivalTime() + c2.getWaitTime();
                if (sTime1 > sTime2){
                    return 1;
                }else if(sTime2 == sTime1){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        return c.get().getArrivalTime() + c.get().getWaitTime();
    }

    public int getMaxServ(){
        return this.maxSCust;
    }

    public boolean isBusy(){
        return this.currServing.isFull() && !this.getResting() ;
    }

    public ServerType getType(){
        return this.type;
    }

    public boolean isQueueFull(){
        return custQueue.isFull();
    }

    public boolean isQueueEmpty(){
        return custQueue.isEmpty();
    }
    public int currInQueue(){
        return this.custQueue.size();
    }

    public int currServing(){
        return this.currServing.size();
    }

    public int getID(){
        return this.ID;
    }
    public double getDoneTime(ICustomer c){
        return c.getArrivalTime() + c.getWaitTime() + IServer.SERVICE_TIME;
    }
    public ICustomer getFrontCustomer(){
        return custQueue.remove();
    }

    public int currElderInQueue(){
        return (int) this.custQueue.getStream().filter(x -> x.getType() == CustomerType.ELDERLY).count();
    }
  
    // doArrives has nothing to do with Server
    public void doWaits(ICustomer cust, double time){
        custQueue.insert(Optional.of(cust));
    }
    public void doServed(ICustomer cust, double time){
        this.currServing.insert(Optional.of(cust));
        cust.doServed(time);
    }
    // doLeaves has nothing to do with Server
    public void doDone(ICustomer cust, double time){
        this.currServing.remove();
    }

    @Override
    public String toString(){
        return String.format("%d", this.getID());
    }

    @Override
    public int compareTo(Server s1){
        return this.getID() - s1.getID();
    }
}