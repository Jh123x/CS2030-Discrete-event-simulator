import java.util.*;

public class Simulator {
  public static final int MAX_EVENTS = 1100; // 1000 + 100

  public static String simulate(ArrayList<ICustomer> customer,int numArrival, int servMaxCust,int numHumanServer, int numMachineServer, int servQueueSize, ServerRandomizer serverRandomizer, double probRest) {
    /** Local Variables */
    allServer server = new allServer();

    //Event Queue
    IQueue<IEvent> eventQueue = new SortedQueue<IEvent>(Simulator.MAX_EVENTS);

    //Logger
    Log log = new Log();

    /** Initialization  */
    //Servers
    int i = 0;
    for(i = 0; i < numHumanServer; i++){
      server.add(new HumanServer(i, servMaxCust, servQueueSize,probRest));
    }
    for(i = numHumanServer; i<numHumanServer+numMachineServer; i++){
      server.add(new MachineServer(i,servMaxCust,servQueueSize));
    }
    Server.randomizer = serverRandomizer;

    //Customers
    customer.stream().map(x -> (IEvent) new Event(State.ARRIVES,x.getArrivalTime(),x)) //Generate each event for customer
                     .forEach(x -> eventQueue.insert(Optional.of(x))); // Insert all the events into the Queue


    // RUN
    while(!eventQueue.isEmpty()) {
      IEvent next = eventQueue.remove();

      switch(next.getState()) {
        case ARRIVES:
          Optional<IEvent> ev =arriveEvt(next.getCustomer(), next.getTime(), server, log);
          eventQueue.insert(ev);
          break;
        case WAITS:
          eventQueue.insert(waitEvt(next.getServer().orElseGet(null), next.getCustomer(), next.getTime(), server, log));
          break;
        case SERVED:
          eventQueue.insert(servedEvt(next.getServer().orElseGet(null), next.getCustomer(), next.getTime(), server, log));
          break;
        case LEAVES:
          eventQueue.insert(leavesEvt(next.getCustomer(), next.getTime(), server, log));
          break;
        case DONE:
          eventQueue.insert(doneEvt(next.getServer().get(), next.getCustomer(), next.getTime(), server, log));
          break;
        case REST:
          assert next.getServer().get().getType() == ServerType.HUMAN;
          eventQueue.insert(restEvt((HumanServer)next.getServer().get(),next.getTime(), server,log));
          break;
        case BACK:
          assert next.getServer().get().getType() == ServerType.HUMAN;
          Optional<ArrayList<IEvent>> x = backEvt(next.getServer().get(),next.getTime(),server,log);
          if(x.isPresent()){
            for(IEvent evt: x.get()){
              eventQueue.insert(Optional.of(evt));
            }
          }
          break;
      }
    }
    return log.toString();
  }

  /** Event Handler */
  private static Optional<IEvent> restEvt(HumanServer serv, double time, allServer allserver, Log log){
    assert serv.currServing() == 0: "The Server is taking a break before serving everyone in serving queue";
    log.logRest(serv,time);
    serv.doRest();
    assert serv.getRestTime() > 0: "The rest time for the server is less than 0";
    return Optional.of(new Event(State.BACK,time+serv.getRestTime(),serv));
  }
  private static Optional<ArrayList<IEvent>> backEvt(IServer serv, double time, allServer allServer, Log log){
    assert serv.getType() == ServerType.HUMAN;
    log.logBack(serv,time);
    if(serv.getType() == ServerType.HUMAN){
      HumanServer s = (HumanServer) serv;
      s.doBack();
    }
    ArrayList<IEvent> ls = new ArrayList<>();
    int timediff = 0;
    while(!serv.isQueueEmpty() && serv.currServing() + timediff < serv.getMaxServ()){
      ls.add(new Event(State.SERVED, time+timediff*0.001,serv.getFrontCustomer(), serv));
      timediff += 1;
    }
    return Optional.of(ls);
  }

  private static Optional<IEvent> arriveEvt(ICustomer cust, double time, allServer allServer, Log log) {
    log.logArrives(cust, time);

    if(!allServer.isBusy()) {
      IEvent ev = new Event(State.SERVED, time,cust, allServer.getFreeServer().get());
      assert ev.getServer().isPresent();
      return Optional.of(ev);
    } 
    
    else if(allServer.isQueueFull()) {
      return Optional.of(new Event(State.LEAVES, time, cust));
    } 
    
    else if(!allServer.isQueueFull()) {
      assert allServer.isBusy(): "The serving queue of the server is not full";
      IEvent ev = new Event(State.WAITS, time, cust, allServer.getFreeQueue(cust.getType()).get());
      assert ev.getServer().isPresent();
      return Optional.of(ev);
    } 
    
    else {
      assert false;
      return Optional.empty(); // should not come to this
    }
  }

  private static Optional<IEvent> waitEvt(IServer serv, ICustomer cust, double time, allServer allServer, Log log) {
    log.logWaits(serv, cust, time);
    serv.doWaits(cust, time);
    return Optional.empty();
  }

  private static Optional<IEvent> servedEvt(IServer serv, ICustomer cust, double time, allServer allServer, Log log) {
    assert serv.currServing() <= serv.getMaxServ();
    log.logServed(serv, cust, time);
    serv.doServed(cust, time);
    return Optional.of(new Event(State.DONE, serv.getDoneTime(cust), cust, serv));
  }
  
  private static Optional<IEvent> leavesEvt(ICustomer cust, double time, allServer allServer, Log log) {
    log.logLeaves(cust, time);
    if(cust.getType() == CustomerType.GENERAL){
      cust.reArrival();
      return Optional.of(new Event(State.ARRIVES,cust.getArrivalTime(),cust));
    }
    return Optional.empty();
  }

  private static Optional<IEvent> doneEvt(IServer serv, ICustomer cust, double time, allServer allServer, Log log) {
    assert serv.currServing() > 0;
    log.logDone(serv, cust, time);
    serv.doDone(cust, time);
    if(!serv.getResting() && serv.getRestRate() > 0){
      if(Server.randomizer.toRest() < serv.getRestRate()){
        serv.initRest();
      }
    }

    if(serv.getResting() && serv.currServing() == 0){
      return Optional.of(new Event(State.REST, time, serv));
    }

    if(!serv.isQueueEmpty() && !serv.getResting()) {
      return Optional.of(new Event(State.SERVED, time, serv.getFrontCustomer(),serv));
    }
    else {
      return Optional.empty();
    }
    
  }
  /******************/
}