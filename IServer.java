public interface IServer {
  public static final double SERVICE_TIME = 1.0;

  public boolean isBusy();
  public boolean isQueueFull();
  public boolean isQueueEmpty();

  public int getID();
  public double getDoneTime(ICustomer c);
  public ICustomer getFrontCustomer();
  public int currInQueue();
  public int currServing();
  public int currElderInQueue();
  public ServerType getType();
  public int getMaxServ();
  public boolean getResting();
  public double getRestRate();
  public void initRest();
  public double getRestTime();

  // doArrives has nothing to do with Server
  public void doWaits(ICustomer cust, double time);
  public void doServed(ICustomer cust, double time);
  // doLeaves has nothing to do with Server
  public void doDone(ICustomer cust, double time);
}