package VP;
import java.util.*;

/**
 * Uses Decks and Card objects to implement video poker game.
 *
 * @author James Clark
 */
public class VideoPoker {

    // default constant values
    private static final int STARTINGBALANCE = 100;
    private static final int NUMBEROFCARDS = 5;

    // default constant payout value and playerHand types
    private static final int[] MULTIPLIERS = {1, 2, 3, 5, 6, 10, 25, 50, 1000};
    private static final String[] GOODHANDTYPES = {
            "One Pair", "Two Pairs", "Three of a Kind", "Straight", "Flush",
            "Full House", "Four of a Kind", "Straight Flush", "Royal Flush"};

    // must use only one deck
    private final Decks oneDeck;

    // holding current poker 5-card hand, balance, bet
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /**
     * Default constructor. sets balance = startingBalance
     */
    public VideoPoker() {
        this(STARTINGBALANCE);
    }

    /**
     * Constructor that sets given balance
     *
     * @param balance amount of money to start with
     */
    public VideoPoker(int balance) {
        this.playerBalance = balance;
        oneDeck = new Decks(1, false);
    }

    /**
     * This displays the payout table based on the multipliers and goodHandTypes arrays
     */
    private void showPayoutTable() {
        System.out.println("\n");
        System.out.println("Payout Table          Multiplier   ");
        System.out.println("=======================================");
        int size = MULTIPLIERS.length;
        for (int i = size - 1; i >= 0; i--) {
            if (!"Flush".equals(GOODHANDTYPES[i])) {
                System.out.println(GOODHANDTYPES[i] + "\t|\t" + MULTIPLIERS[i]);
            } else {
                System.out.println(GOODHANDTYPES[i] + "\t\t|\t" + MULTIPLIERS[i]);
            }
        }
        System.out.println("\n");
    }

    /**
     * Check current playerHand using multipliers and goodHandTypes arrays. Prints yourHandType (default is "Sorry, you lost") at the end of function.
     */
    private void checkHands() {
        // holds ranks of cards
        List<Integer> tempR = new ArrayList<>();
        // holds suits of cards
        List<Integer> tempS = new ArrayList<>();
        // winning level
        int winning = -1;
        // stragiht is true until proven false
        boolean straight = true;
        // flush is true until proven false
        boolean flush = true;
        //adds card rank and suit to lists
        for (int i = 0; i < NUMBEROFCARDS; i++) {
            tempR.add(playerHand.get(i).getRank());
            tempS.add(playerHand.get(i).getSuit());

        }

        // sorts ranks
        Collections.sort(tempR);

        //straight
        for (int i = NUMBEROFCARDS - 1; i > 0; i--) {
            if (straight) {
                if (tempR.get(i) - tempR.get(i - 1) != 1) {
                    straight = false;
                }
            }
        }

        if (straight) {
            winning = 3;
        }

        //flush
        for (int i = 0; i < NUMBEROFCARDS - 1; i++) {
            if (flush) {
                if (tempS.get(i) - tempS.get(i + 1) != 0) {
                    flush = false;
                }
            }
        }

        if (flush) {
            winning = 4;
        }

        if (flush && straight) {
            winning = 7;
        }

        //royal
        if (tempR.get(0) == 1 && tempR.get(1) == 10 && tempR.get(2) == 11 && tempR.get(3) == 12 && tempR.get(4) == 13 && flush) {
            winning = 8;
        }

        for (int i = 0; i < NUMBEROFCARDS - 1; i++) {

            if (Objects.equals(tempR.get(i), tempR.get(i + 1))) {

                //two pair
                if (winning == 0) {
                    winning = 1;
                }
                //one pair
                if (0 > winning) {
                    winning = 0;
                }

                //full house
                if (winning == 2) {
                    winning = 5;
                }
                i++;

                //3 of a kind
                if (NUMBEROFCARDS > i + 1 && Objects.equals(tempR.get(i), tempR.get(i + 1))) {

                    //reversed full house
                    if (winning == 1) {
                        winning = 5;
                        i++;
                    }
                    if (2 > winning) {
                        winning = 2;
                        i++;
                    }

                    //4 of a kind
                    if (NUMBEROFCARDS > i + 1 && Objects.equals(tempR.get(i), tempR.get(i + 1))) {
                        if (6 > winning) {
                            winning = 6;
                        }
                    }

                }
            }
        }

        // winner
        if (winning != -1) {
            //add winnings
            playerBalance += (playerBet * MULTIPLIERS[winning]);
            //announce winning type and new balance
            System.out.println("\n\n" + GOODHANDTYPES[winning] + "!");
            System.out.println("New balance: $" + playerBalance);

            // loser
        } else {
            // announce loss and new balance
            System.out.println("\nYou Lose!");
            System.out.println("New Balance: $" + playerBalance);
        }

    }

    private void displayHand(){
        System.out.print("\nPlayer Hand: ");
        for (int i = 0; i < NUMBEROFCARDS; i++) {
            System.out.print((i + 1) + ".<" + playerHand.get(i).toString() + ">  ");
        }
    }

    /**
     * Plays the game.
     */
    public void play() {

        try {
            //user input
            Scanner input = new Scanner(System.in);

            // keep playing?
            String play = "y";
            // print payout table?
            String table = "y";

            //while game can continue
            while ("y".equals(play) && playerBalance > 0) {
                //print payout table
                if ("y".equals(table)) {
                    showPayoutTable();
                }
                //print player balance
                System.out.println("\nPlayer balance: $" + playerBalance);
                // prompt for bet
                System.out.print("Please enter your bet: $");
                playerBet = input.nextInt();

                // verify bet is less than player's balance
                while (playerBet > playerBalance) {
                    System.out.print("Your bet exceeds your balance! Please re-enter bet: $");
                    playerBet = input.nextInt();
                }

                // remove bet from balance
                playerBalance -= playerBet;
                // shuffle and deal deck
                oneDeck.shuffle();
                playerHand = oneDeck.deal(NUMBEROFCARDS);

                //display hand
                displayHand();

                //prompt for which cards should be removed
                System.out.print("\n\nPlease list all cards (1 through 5) you'd like to replace separated by spaces"
                        + "\nReplace (enter -1 to keep all cards): ");
                input.nextLine();
                // retrieve user's input
                String replace = input.nextLine();
                Scanner replacing = new Scanner(replace);
                // replace requested cards
                while (replacing.hasNextInt()) {
                    int replacer = replacing.nextInt();
                    if (replacer >= 1) {
                        playerHand.remove(replacer - 1);

                        playerHand.add(replacer - 1, oneDeck.deal(1).remove(0));

                    }
                }

                // display new hand
                displayHand();

                // determine if winner, initiates payout
                checkHands();

                // ask to continue if user still has money
                if (playerBalance > 0) {
                    System.out.print("\nDo you want to play again? (y or n): ");
                    play = input.next();

                    if ("y".equals(play)) {
                        System.out.print("\nDo you want to see the payout table? (y or n): ");
                        table = input.next();
                    }
                } else {
                    System.out.println("You have run out of money!");
                }
            }

            // cash out
            System.out.println("You cash out with: $" + playerBalance);
        } catch (PlayingCardException e) {
            System.out.println("PlayingCardException: " + e.getMessage());
        }
    }

    /**
     * used in main() to test the VideoPoker class
     */
    private void testCheckHands() {
        try {
            playerHand = new ArrayList<>();

            // set Royal Flush
            playerHand.add(new Card(3, 1));
            playerHand.add(new Card(3, 10));
            playerHand.add(new Card(3, 12));
            playerHand.add(new Card(3, 11));
            playerHand.add(new Card(3, 13));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set Straight Flush
            playerHand.set(0, new Card(3, 9));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set Straight
            playerHand.set(4, new Card(1, 8));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set Flush
            playerHand.set(4, new Card(3, 5));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush   ",
            // "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };
            // set Four of a Kind
            playerHand.clear();
            playerHand.add(new Card(4, 8));
            playerHand.add(new Card(1, 8));
            playerHand.add(new Card(4, 12));
            playerHand.add(new Card(2, 8));
            playerHand.add(new Card(3, 8));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set Three of a Kind
            playerHand.set(4, new Card(4, 11));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set Full House
            playerHand.set(2, new Card(2, 11));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set Two Pairs
            playerHand.set(1, new Card(2, 9));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set One Pair
            playerHand.set(0, new Card(2, 3));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set One Pair
            playerHand.set(2, new Card(4, 3));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");

            // set no Pair
            playerHand.set(2, new Card(4, 6));
            System.out.println(playerHand);
            checkHands();
            System.out.println("-----------------------------------");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * main() to test class, uses function above.
     */
    public static void main(String [] args) {
        VideoPoker pokergame = new VideoPoker();

        pokergame.testCheckHands();
    }
}