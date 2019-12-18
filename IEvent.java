import java.util.*;
public interface IEvent extends Comparable<IEvent> {
  public State getState();
  public double getTime();
  public Optional<IServer> getServer();
  public ICustomer getCustomer();
}