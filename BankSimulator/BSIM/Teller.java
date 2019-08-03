/**
 * Teller class, whos purpose is to serve customers
 *
 * @author James Clark
 */
class Teller {

    // teller id and current customer which is served by this cashier
    private int tellerID;
    private Customer customer;

    // start time and end time of current free/busy interval
    private int startFreeTime;
    private int endFreeTime;
    private int startBusyTime;
    private int endBusyTime;

    // for keeping statistical data
    private int totalFreeTime;
    private int totalBusyTime;
    private int totalCustomers;

    /**
     * Default Constructor
     */
    Teller() {
        this(-1);
    }

    /**
     * Constructor with parameter
     *
     * @param tellerId ID number of teller
     */
    Teller(int tellerId) {
        tellerID = tellerId;
        startFreeTime = 0;
        totalFreeTime = 0;
        totalBusyTime = 0;
        totalCustomers = 0;
    }

    /**
     * Teller ID number getter
     *
     * @return teller ID
     */
    int getTellerID() {
        return tellerID;
    }

    /**
     * Gets customer that teller is helping
     *
     * @return Teller's customer
     */
    Customer getCustomer() {
        return customer;
    }

    /**
     * Gets time when transaction completes
     *
     * @return time of transaction completion
     */
    int getEndBusyTime() {
        return endBusyTime;
    }

    /**
     * Sets customer to teller
     *
     * @param newCustomer customer to be assigned
     */
    void setCustomer(Customer newCustomer) {
        customer = newCustomer;
    }

    /**
     * Sets time when teller was made free
     *
     * @param time when made free
     */
    void setStartFreeTime(int time) {
        startFreeTime = time;
    }

    /**
     * Sets time when teller becomes busy
     *
     * @param time when made busy
     */
    void setStartBusyTime(int time) {
        startBusyTime = time;
    }

    /**
     * Sets time when teller was assigned
     *
     * @param time when no longer free
     */
    void setEndFreeTime(int time) {
        endFreeTime = time;
    }

    /**
     * Sets time when teller finished transaction
     *
     * @param time when no longer busy
     */
    void setEndBusyTime(int time) {
        endBusyTime = time;
        customer.setFinishTime(time);
    }

    /**
     * Updates the total amount of free time teller has had
     */
    void updateTotalFreeTime() {
        totalFreeTime += (endFreeTime - startFreeTime);
    }

    /**
     * Updates the total amount of busy time teller has had
     */
    void updateTotalBusyTime() {
        totalBusyTime += (endBusyTime - startBusyTime);
    }

    /**
     * Updates total number of customers teller has helped
     */
    void updateTotalCustomers() {
        totalCustomers++;
    }

    /**
     * Handles transistion from free to busy
     *
     * @param newCustomer customer to be assigned to teller
     * @param currentTime current time step
     */
    void freeToBusy(Customer newCustomer, int currentTime) {
        // assign new customer to teller
        customer = newCustomer;

        // Update statistical data
        setEndFreeTime(currentTime);
        updateTotalFreeTime();
        setStartBusyTime(currentTime);
        setEndBusyTime(currentTime + customer.getTransactionTime());
        updateTotalCustomers();
    }

    /**
     * Updates transition from busy to free
     *
     * @return customer who was helped
     */
    Customer busyToFree() {
        // update statistical data
        updateTotalBusyTime();
        setStartFreeTime(endBusyTime);

        // return customer
        return customer;

    }

    /**
     * Prints statistical data for simulation
     */
    void printStatistics() {

        // print teller statistics, see project statement
        System.out.println("\t\tTeller ID                : " + tellerID);
        System.out.println("\t\tTotal free time          : " + totalFreeTime);
        System.out.println("\t\tTotal busy time          : " + totalBusyTime);
        System.out.println("\t\tTotal # of customers     : " + totalCustomers);
        if (totalCustomers > 0) {
            System.out.format("\t\tAverage transaction time : %.2f%n\n", (totalBusyTime * 1.0) / totalCustomers);
        }
    }

    /**
     * Prints out data for testing
     *
     * @return teller stats
     */
    public String toString() {
        return "tellerID=" + tellerID
                + ":startFreeTime=" + startFreeTime + ":endFreeTime=" + endFreeTime
                + ":startBusyTime=" + startBusyTime + ":endBusyTime=" + endBusyTime
                + ":totalFreeTime=" + totalFreeTime + ":totalBusyTime=" + totalBusyTime
                + ":totalCustomer=" + totalCustomers + ">>customer:" + customer;
    }

    /**
     * main () for testing Teller class
     */
    public static void main(String[] args) {
        // quick check
        Customer mycustomer = new Customer(1, 5, 15);
        Teller myteller = new Teller(5);
        myteller.setStartFreeTime(0);
        System.out.println(myteller);
        myteller.freeToBusy(mycustomer, 20);
        System.out.println("\n" + myteller);
        myteller.busyToFree();
        System.out.println("\n" + myteller);
        System.out.println("\n\n");
        myteller.printStatistics();

    }

};
