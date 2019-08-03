import java.util.*;
import java.io.*;

/**
 * Simulates a bank environment.
 * This file contains the main() for the project.
 *
 * @author James Clark
 */
class BankSimulator {

    // input parameters
    private int numTellers, customerQLimit;
    private int simulationTime, dataSource;
    private int chancesOfArrival, maxTransactionTime;

    // statistical data
    private int numGoaway, numServed, totalWaitingTime;

    // internal data
    // customer ID counter
    private int customerIDCounter;
    // service area object
    private ServiceArea servicearea;
    // get customer data from file
    private Scanner dataFile;
    // get customer data using random function
    private Random dataRandom;

    // most recent customer arrival info, see getCustomerData()
    private boolean anyNewArrival;
    private int transactionTime;

    // initialize data fields
    private BankSimulator() {
        totalWaitingTime = 0;
        customerIDCounter = 1;
        numGoaway = 0;
        numServed = 0;
    }

    private void setupParameters() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);

        // read input parameters
        try {
            System.out.println("Please give the simulation parameters:\n");
            System.out.print("Enter simulation time (0 < integer <= 10000):");
            simulationTime = input.nextInt();
            while (simulationTime <= 0 || simulationTime > 10000) {
                System.out.print("Invalid input. Enter simulation time (0 < intger <= 10000):");
                simulationTime = input.nextInt();
            }

            System.out.print("Enter the number of tellers (0 < integer <= 10):");
            numTellers = input.nextInt();
            while (numTellers <= 0 || numTellers > 10) {
                System.out.print("Invalid input. Enter number of tellers (0 < integer <= 10):");
                numTellers = input.nextInt();
            }

            System.out.print("Enter chance (0% < integer <= 100%) of new customer:");
            chancesOfArrival = input.nextInt();
            while (chancesOfArrival <= 0 || chancesOfArrival > 100) {
                System.out.print("Invalid input. Enter the chance of a new customer (0% < integer <= 100%):");
                chancesOfArrival = input.nextInt();
            }

            System.out.print("Enter the maximum transaction time of customers (0 < integer <= 500):");
            maxTransactionTime = input.nextInt();
            while (maxTransactionTime <= 0 || maxTransactionTime > 500) {
                System.out.print("Invalid input. Enter number of tellers (0 < integer <= 500):");
                maxTransactionTime = input.nextInt();
            }

            System.out.print("Enter the customer queue limit (0 < integer <= 50):");
            customerQLimit = input.nextInt();
            while (customerQLimit <= 0 || customerQLimit > 50) {
                System.out.print("Invalid input. Enter the customer queue limit (0 < integer <= 50):");
                customerQLimit = input.nextInt();
            }

            System.out.print("Enter 0 or 1 to get data from Random or File:");
            dataSource = input.nextInt();
            while (dataSource < 0 || dataSource > 1) {
                System.out.print("Invalid input! Enter 0 or 1 to get data from Random or File:");
                dataSource = input.nextInt();
            }

        } catch (Exception e) {
            System.out.println("Value given was not an integer!");
            System.out.println();
            System.out.println("Please re-enter your parameters");
            setupParameters();
        }

        // setup dataFile or dataRandom
        switch (dataSource) {
            case 0:
                dataRandom = new Random();
                break;

            case 1:
                try {
                    System.out.print("Enter filename: ");
                    String name = input.next();
                    File file = new File(name);
                    dataFile = new Scanner(file);

                } catch (Exception e) {
                    System.out.println("File not found. Defaulting to random data.");
                    dataSource = 0;
                    dataRandom = new Random();

                }
                break;

        }
    }

    /**
     * Used for Step 1 in doSimulation() to get new customer information.
     */
    private void getCustomerData() {
        // get next customer data : from file or random number generator
        switch (dataSource) {
            //random
            case 0:
                anyNewArrival = ((dataRandom.nextInt(100) + 1) <= chancesOfArrival);
                transactionTime = dataRandom.nextInt(maxTransactionTime) + 1;
                break;

            //controlled
            case 1:
                if (dataFile.hasNextInt()) {
                    anyNewArrival = (((dataFile.nextInt() % 100) + 1) <= chancesOfArrival);
                    transactionTime = (dataFile.nextInt() % maxTransactionTime) + 1;
                } else {
                    System.out.println("There are no more values in the specified file!");
                }
                break;
        }
    }

    /**
     * Starts simulation
     */
    private void doSimulation() {
        System.out.println("*** Start Simulation ***");
        // Initialize ServiceArea
        servicearea = new ServiceArea(numTellers, customerQLimit);
        System.out.println("Tellers #1 to #" + numTellers + " are ready...");
        // Time driver simulation loop
        for (int currentTime = 0; currentTime < simulationTime; currentTime++) {
            System.out.println("---------------------------------------------");
            System.out.println("Time : " + currentTime);

            // Step 1: any new customer enters the bank?
            getCustomerData();

            if (anyNewArrival) {

                // Step 1.1: setup customer data
                Customer newCustomer = new Customer(customerIDCounter, transactionTime, currentTime);
                customerIDCounter++;
                System.out.println("Customer #" + newCustomer.getCustomerID() + " arrives with a transaction time of " + newCustomer.getTransactionTime() + " units");
                // Step 1.2: check customer waiting queue too long?
                if (servicearea.isCustomerQTooLong()) {
                    // increment numGoaway if line is full
                    numGoaway++;
                    System.out.println("Customer #" + newCustomer.getCustomerID() + "sees that the queue is too long and leaves");
                } else {
                    // place customer in queue if space is available
                    servicearea.insertCustomerQ(newCustomer);
                    System.out.println("Customer #" + newCustomer.getCustomerID() + " waits in the customer queue");
                }

            } else {
                System.out.println("\tNo new customer!");
            }

            // Step 2: free busy tellers that are done at currentTime, add to free TellerQ
            for (int i = 1; i <= servicearea.numBusyTellers(); i++) {
                //if the time matches when the customer should be done, free teller
                if (servicearea.getFrontBusyTellerQ().getEndBusyTime() == currentTime) {
                    Teller tempT = servicearea.removeBusyTellerQ();
                    System.out.println("Customer #" + tempT.busyToFree().getCustomerID() + " is done");


                    servicearea.insertFreeTellerQ(tempT);
                    System.out.println("Teller #" + tempT.getTellerID() + " is free");
                }
            }

            // Step 3: get free tellers to serve waiting customers at currentTime
            while (!servicearea.emptyFreeTellerQ() && !servicearea.emptyCustomerQ()) {
                Teller tempT = servicearea.removeFreeTellerQ();
                Customer tempC = servicearea.removeCustomerQ();
                System.out.println("Customer #" + tempC.getCustomerID() + " gets a teller");
                tempT.freeToBusy(tempC, currentTime);
                servicearea.insertBusyTellerQ(tempT);
                numServed++;
                System.out.println("Teller #" + tempT.getTellerID() + " starts serving customer #" + tempC.getCustomerID() + " for " + tempC.getTransactionTime() + " units");

            }

            // increment total waiting time by 1 for every customer in line
            if (servicearea.numWaitingCustomers() != 0) {
                totalWaitingTime += servicearea.numWaitingCustomers();
            }

        }
        System.out.println("============================================");
    }

    /**
     * Prints collected statistical data for simulation
     */
    private void printStatistics() {

        // print out simulation results
        System.out.println("End of simulation report: \n");

        System.out.println("# total arrival customers: " + customerIDCounter);
        System.out.println("# customers gone-away: " + numGoaway);
        System.out.println("# customers served: " + numServed);

        System.out.println("*** Current Tellers Info ***");

        servicearea.printStatistics();

        System.out.println("Total waiting time: " + totalWaitingTime);
        int avgWaitTime = totalWaitingTime / (customerIDCounter - numGoaway);
        System.out.println("Average waiting time: " + avgWaitTime + "\n");

        System.out.println("*** Busy Tellers Info ***\n");

        while (servicearea.numBusyTellers() != 0) {
            Teller tempT = servicearea.removeBusyTellerQ();
            tempT.printStatistics();
        }

        System.out.println("*** Free Tellers Info ***");
        while (servicearea.numFreeTellers() != 0) {
            Teller tempT = servicearea.removeFreeTellerQ();
            tempT.printStatistics();
        }

    }

    /**
     * Main method to run the simulation and print the summary.
     */
    public static void main(String[] args) throws FileNotFoundException {
        BankSimulator runBankSimulator = new BankSimulator();
        runBankSimulator.setupParameters();
        runBankSimulator.doSimulation();
        runBankSimulator.printStatistics();
    }

}
