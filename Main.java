import java.util.*;

public class Main {

  public static void main(String[] args) {
    // INPUT
    try(Scanner scanner = new Scanner(System.in);){


      //Scanning for the input based on the inputs in the in file.
      int numArrival = scanner.nextInt();

      int servMaxCust = scanner.nextInt();
      int numHumanServer = scanner.nextInt();
      int servQueueSize = scanner.nextInt();
      int numMachineServer = scanner.nextInt();

      long seed = scanner.nextLong();
      double probElderly = scanner.nextDouble();
      double arrivalRate = scanner.nextDouble();
      double rearriveRate = scanner.nextDouble();
      double probRest = scanner.nextDouble();
      double restRate = scanner.nextDouble();

      //List of customers
      ArrayList<ICustomer> customer = new ArrayList<>();
      
      //Initialising the customer generator
      CustomerGenerator custGen = new CustomerGenerator(seed, probElderly, arrivalRate, rearriveRate);

      //Init serverRandomizer
      ServerRandomizer serverRandomizer = new ServerRandomizer(seed, restRate);

      //Adding the customers to the list
      for(int i = 0; i < numArrival; i++){
        customer.add(custGen.nextCustomer());
      }


      // PROCESS
      String stats = Simulator.simulate(customer, numArrival, servMaxCust, numHumanServer, numMachineServer, servQueueSize, serverRandomizer, probRest);

      // OUTPUT
      System.out.println(stats);
    }
  }
}