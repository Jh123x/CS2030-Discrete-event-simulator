import java.util.Optional;

class Event implements IEvent{
  private State state;
  private double time;
  private IServer server = null;
  private ICustomer customer;

  public Event(State state, double time, ICustomer customer,IServer server){
      this.state = state;
      this.time = time;
      this.server = server;
      this.customer = customer;
  }

  public Event(State state, double time, ICustomer customer){
    this.state = state;
    this.time = time;
    this.customer = customer;
    this.server = null;
  }

  public Event(State state, double time, IServer server){
    this.state = state;
    this.time = time;
    this.server = server;
    this.customer = null;
  }

  public State getState(){
      return this.state;
  }
  public double getTime(){
      return this.time;
  }
  public Optional<IServer> getServer(){
    if(this.server == null){
      return Optional.empty();
    }
    return Optional.of(this.server);
  }
  public ICustomer getCustomer(){
      return this.customer;
  }
  public int compareTo(IEvent e){
      Double d = this.getTime();
      if(d.compareTo(e.getTime()) == 0){
        return e.getState().compareTo(this.getState());
      }
      else{
        return d.compareTo(e.getTime());
      }
  }
}