public class Log {
  private int served;
  private int leave;
  // private int total;
  private double wait;

  public Log() {
    this.served = 0;
    // this.total = 0;
    this.wait = 0.0;
    this.leave = 0;
  }

  public void logArrives(ICustomer cust, double time) {
    System.out.println(String.format("%.3f", time) + " " + cust + " arrives");
    // this.total++;
  }
  public void logWaits(IServer serv, ICustomer cust, double time) {
    System.out.println(String.format("%.3f", time) + " " + cust + " waits for " + serv);
  }
  public void logServed(IServer serv, ICustomer cust, double time) {
    System.out.println(String.format("%.3f", time) + " " + cust + " served by " + serv);
    this.served += 1;
  }
  public void logLeaves(ICustomer cust, double time) {
    System.out.println(String.format("%.3f", time) + " " + cust + " leaves");
    this.leave += 1;
  }
  public void logDone(IServer serv, ICustomer cust, double time) {
    System.out.println(String.format("%.3f", time) + " " + cust + " done with "+ serv);
    this.wait += cust.getWaitTime();
  }
  public void logRest(IServer serv, double time){
    System.out.println(String.format("%.3f",time) + " " + serv + " is resting");
  }
  public void logBack(IServer serv,double time){
    System.out.println(String.format("%.3f",time) + " " + serv + " is back");
  }

  @Override
  public String toString() {
    return String.format("%.3f", this.wait / this.served) + ", " +
        this.served + ", " + (this.leave);
  }
}