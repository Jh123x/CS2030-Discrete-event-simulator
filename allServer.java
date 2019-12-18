import java.util.*;

class allServer{
    private ArrayList<IServer> allServer;

    public allServer(){
        this.allServer = new ArrayList<>();
    }

    public boolean isBusy(){
        return this.allServer.stream().filter(x -> !x.isBusy() && !x.getResting()).count() == 0;
    }

    public boolean isQueueFull(){
        return this.allServer.stream().filter(x -> !x.isQueueFull()).count() == 0;
    }

    public boolean isQueueEmpty(){
        return !this.isQueueFull();
    }

    public Optional<IServer> getFreeServer(){
        return this.allServer.stream().filter(x -> !x.isBusy() && !x.getResting()).sorted().findFirst();
    }

    public Optional<IServer> getFreeQueue(CustomerType c){
        assert this.isBusy() : "Not all the servers are busy";
        switch(c){
            case GENERAL:
                return this.allServer.stream().filter(x -> !x.isQueueFull())
                                              .sorted(new Comparator<IServer>(){
                                                public int compare(IServer s1, IServer s2){
                                                    if(s1.currInQueue() == s2.currInQueue()){
                                                        if(s1.currElderInQueue() == s2.currElderInQueue()){
                                                            return s1.getID() - s2.getID();
                                                        }
                                                        return s1.currElderInQueue() - s2.currElderInQueue();
                                                    }
                                                    return s1.currInQueue() - s2.currInQueue();
                                                }
                                            }).findFirst();
            case ELDERLY:
                return this.allServer.stream().filter(x -> !x.isQueueFull()).findFirst();
        }
        return Optional.empty(); // Should not reach here if the proper checks are done
    }

    public void add(IServer s){
        this.allServer.add(s);
    }
}