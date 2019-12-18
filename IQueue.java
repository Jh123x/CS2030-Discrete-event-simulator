import java.util.*;
public interface IQueue<T> {
  public void insert(Optional<T> elem);
  public T remove();
  
  public boolean isEmpty();
  public boolean isFull();

  public int size();
}