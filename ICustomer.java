public interface ICustomer extends Comparable<ICustomer> {
  public int getID();
  public double getWaitTime();
  public double getArrivalTime();
  public void doServed(double time);
  public CustomerType getType();
  public double getReTime();
  public void reArrival();
  public int compareTo(ICustomer c);
  // doArrives, doLeaves, doDone, doWait
  // either involves Server or do nothing
}