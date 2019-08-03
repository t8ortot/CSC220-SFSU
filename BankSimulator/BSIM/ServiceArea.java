import java.util.*;

/**
 * Class for comparing Tellers in the priority queue
 *
 * @author James Clark
 */
class CompareTellers implements Comparator<Teller> {

    /**
     * Compares two tellers to determine which one has the priority
     *
     * @param o1 First teller to be compared
     * @param o2 Second teller to be compared
     */
    @Override
    public int compare(Teller o1, Teller o2) {
        return o1.getEndBusyTime() - o2.getEndBusyTime();
    }
}

/**
 * Designed to represent the bank environment with customer queues and teller queues.
 *
 * @author James Clark
 */
class ServiceArea {

    // Private data fields:
    // Busy Teller priority queue
    private PriorityQueue<Teller> busyTellerQ;

    // FIFO customer queue and free Teller queue
    private ArrayDeque<Customer> customerQ;
    private ArrayDeque<Teller> freeTellerQ;

    // Customer queue size limit
    private int customerQLimit;

    /**
     * Default Constructor
     */
    public ServiceArea() {
    }

    /**
     * Cosntructor with parameters:
     *
     * @param numTellers     Number of tellers in the bank
     * @param customerQlimit Maximum number of customers allowed to be in the line
     */
    public ServiceArea(int numTellers, int customerQlimit) {

        customerQLimit = customerQlimit;
        // the line
        customerQ = new ArrayDeque<>(customerQLimit);
        // available tellers
        freeTellerQ = new ArrayDeque<>();
        // a queue of busy tellers in order of when they will become available again
        busyTellerQ = new PriorityQueue<>(numTellers, new CompareTellers());

        // create numTellers amount of tellers and add them to the freeTellerQ
        for (int i = 1; i <= numTellers; i++) {
            freeTellerQ.add(new Teller(i));
        }
    }

    /**
     * Returns teller to be assigned to customer if one is free
     *
     * @return any available tellers
     */
    public Teller removeFreeTellerQ() {
        return freeTellerQ.pollFirst();
    }

    /**
     * Insert a free teller into the queue to wait for more customers
     */
    public void insertFreeTellerQ(Teller teller) {
        freeTellerQ.addLast(teller);
    }

    /**
     * Determines if the Free Teller Queue is empty
     *
     * @return isEmpty?
     */
    public boolean emptyFreeTellerQ() {
        return freeTellerQ.isEmpty();
    }

    /**
     * How many free tellers are in queue
     *
     * @return number of free tellers
     */
    public int numFreeTellers() {
        return freeTellerQ.size();
    }

    /**
     * Returns a teller that is ready to be free
     *
     * @return tellers that are no longer busy.
     */
    public Teller removeBusyTellerQ() {
        return busyTellerQ.poll();
    }

    /**
     * Adds teller to busy queue when it has been assigned to customer
     */
    public void insertBusyTellerQ(Teller teller) {
        busyTellerQ.add(teller);
    }

    /**
     * Determines if the busy teller queue is empty
     *
     * @return isEmpty?
     */
    public boolean emptyBusyTellerQ() {
        return busyTellerQ.isEmpty();
    }

    /**
     * Gets the number of busy tellers
     *
     * @return number of busy tellers
     */
    public int numBusyTellers() {
        return busyTellerQ.size();
    }

    /**
     * Peeks at teller that is closest to finishing transaction
     *
     * @return copy of teller closest to finishing
     */
    public Teller getFrontBusyTellerQ() {
        return busyTellerQ.peek();
    }

    /**
     * Returns the first customer waiting in line
     *
     * @return next customer
     */
    public Customer removeCustomerQ() {
        return customerQ.pollFirst();
    }

    /**
     * Adds customer to back of line
     */
    public void insertCustomerQ(Customer customer) {
        customerQ.addLast(customer);
    }

    /**
     * Determines if the customer line is empty
     *
     * @return isEmpty?
     */
    public boolean emptyCustomerQ() {
        return customerQ.isEmpty();
    }

    /**
     * Gets the number of customers waititng in line
     *
     * @return number of customers in line
     */
    public int numWaitingCustomers() {
        return customerQ.size();
    }

    /**
     * Determines if the customer line is too long to add another customer
     *
     * @return is line to long?
     */
    public boolean isCustomerQTooLong() {
        boolean result = false;
        if (numWaitingCustomers() < customerQLimit) {
            result = false;
        } else if (numWaitingCustomers() >= customerQLimit) {
            result = true;
        }
        return result;
    }

    /**
     * Print statistical data for simulation
     */
    public void printStatistics() {
        System.out.println("\t# waiting customers : " + numWaitingCustomers());
        System.out.println("\t# busy tellers      : " + numBusyTellers());
        System.out.println("\t# free tellers      : " + numFreeTellers());
    }

    /**
     * main() for testing this class
     */
    public static void main(String[] args) {

        // quick check
        // create a ServiceArea and 4 customers
        ServiceArea sc = new ServiceArea(4, 5);
        Customer c1 = new Customer(1, 18, 10);
        Customer c2 = new Customer(2, 33, 11);
        Customer c3 = new Customer(3, 21, 12);
        Customer c4 = new Customer(4, 37, 13);

        // insert customers into customerQ
        sc.insertCustomerQ(c1);
        sc.insertCustomerQ(c2);
        sc.insertCustomerQ(c3);
        sc.insertCustomerQ(c4);
        System.out.println("" + sc.customerQ);
        System.out.println("===============================================");
        System.out.println("Remove customer:" + sc.removeCustomerQ());
        System.out.println("Remove customer:" + sc.removeCustomerQ());
        System.out.println("Remove customer:" + sc.removeCustomerQ());
        System.out.println("Remove customer:" + sc.removeCustomerQ());
        System.out.println("===============================================");

        // remove tellers from freeTellerQ
        System.out.println("freeTellerQ:" + sc.freeTellerQ);
        System.out.println("===============================================");
        Teller p1 = sc.removeFreeTellerQ();
        Teller p2 = sc.removeFreeTellerQ();
        Teller p3 = sc.removeFreeTellerQ();
        Teller p4 = sc.removeFreeTellerQ();
        System.out.println("Remove free teller:" + p1);
        System.out.println("Remove free teller:" + p2);
        System.out.println("Remove free teller:" + p3);
        System.out.println("Remove free teller:" + p4);
        System.out.println("===============================================");
        System.out.println("freeTellerQ:" + sc.freeTellerQ);
        System.out.println("busyTellerQ:" + sc.busyTellerQ);
        System.out.println("===============================================");

        // insert customers to tellers
        p1.freeToBusy(c1, 13);
        p2.freeToBusy(c2, 13);
        p3.freeToBusy(c3, 13);
        p4.freeToBusy(c4, 13);
        System.out.println("Assign customers to free tellers");

        // insert tellers to busyTellerQ
        System.out.println("===============================================");
        System.out.println("Insert tellers to busyTellerQ");
        sc.insertBusyTellerQ(p1);
        sc.insertBusyTellerQ(p2);
        sc.insertBusyTellerQ(p3);
        sc.insertBusyTellerQ(p4);
        System.out.println("busyTellerQ:" + sc.busyTellerQ);
        System.out.println("===============================================");

        // remove tellers from busyTellerQ
        p1 = sc.removeBusyTellerQ();
        p2 = sc.removeBusyTellerQ();
        p3 = sc.removeBusyTellerQ();
        p4 = sc.removeBusyTellerQ();
        p1.busyToFree();
        p2.busyToFree();
        p3.busyToFree();
        p4.busyToFree();
        System.out.println("Remove busy teller:" + p1);
        System.out.println("Remove busy teller:" + p2);
        System.out.println("Remove busy teller:" + p3);
        System.out.println("Remove busy teller:" + p4);

    }

}