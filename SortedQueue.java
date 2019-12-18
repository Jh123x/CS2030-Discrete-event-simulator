import java.util.*;
import java.util.stream.Stream;
class SortedQueue<T> implements IQueue<T>{
  private final int maxQueueSize;
  private PriorityQueue<T> queue = new PriorityQueue<>();

  public SortedQueue(int maxSize){
      this.maxQueueSize = maxSize;
  }
  public void insert(Optional<T> elem){
    if(!elem.isPresent()){
      return;
    }
    if(this.queue.contains(elem)){
      return;
    }
    assert this.queue.size() <= this.maxQueueSize : "The size is " + this.queue.size() + " But " + elem.get() + " was inserted";
    this.queue.offer(elem.get());
  }

  public T remove(){
    return this.queue.poll();
  }
  
  public boolean isEmpty(){
    return this.size() == 0;
  }

  public boolean isFull(){
    return this.size() >= this.maxQueueSize;
  }

  public int size(){
    return this.queue.size();
  }

  public T peek(){
    return this.queue.peek();
  }

  public Stream<T> getStream(){
    return this.queue.stream();
  }
}