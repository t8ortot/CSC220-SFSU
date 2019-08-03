/**
 * Customer object used to track customer data
 *
 * @author James Clark
 */
class Customer {

    // customer data
    private int customerID;
    private int transactionTime;
    private int arrivalTime;
    private int finishTime;

    /**
     * Default constructor
     */
    Customer() {
    }

    /**
     * Constructor to set customerID, transactionTime, arrivalTime
     *
     * @param customerid      The ID number to differentiate customer
     * @param transactiontime The amount of time this customer will take to complete transaction
     * @param arrivaltime     When the customer enters the bank
     */
    Customer(int customerid, int transactiontime, int arrivaltime) {
        customerID = customerid;
        transactionTime = transactiontime;
        arrivalTime = arrivaltime;
    }

    /**
     * Gets transaction time
     */
    int getTransactionTime() {
        return transactionTime;
    }

    /**
     * Gets arrival time
     */
    int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets finish time
     */
    int getFinishTime() {
        return finishTime;
    }

    /**
     * Gets customer's ID
     */
    int getCustomerID() {
        return customerID;
    }

    /**
     * Sets finish time
     */
    void setFinishTime(int finishtime) {
        finishTime = finishtime;
    }

    /**
     * Overrides toString to provide testing data
     */
    @Override
    public String toString() {
        return "customerID=" + customerID + ":transactionTime="
                + transactionTime + ":arrivalTime=" + arrivalTime
                + ":finishTime=" + finishTime;
    }

    /**
     * main() for testing customer class
     */
    public static void main(String[] args) {
        Customer mycustomer = new Customer(1, 5, 18);
        mycustomer.setFinishTime(28);
        System.out.println("Customer Info:" + mycustomer);

    }
}
