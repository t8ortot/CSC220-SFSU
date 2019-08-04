package VP;

import java.util.*;

/**
 * Used for errors related to Card and Deck objects.
 *
 * @author James Clark
 */
class PlayingCardException extends Exception {

    /**
     * Constructor to create a PlayingCardException object with message
     */
    PlayingCardException(String reason) {
        super(reason);
    }
}

/**
 * Creates playing card objects.
 * Rank: valid values are 1 to 13
 * Suit: valid values are 0 to 4
 *
 * @author James Clark
 */
class Card {

    // constant suit and rank arrays
    private static final String[] SUIT = {"Joker", "Clubs", "Diamonds", "Hearts", "Spades"};
    private static final String[] RANK = {"", "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    // Card's info
    // Rank values: 1-13 (see Rank[] above)
    private int cardRank;
    // Suit values: 0-4  (see Suit[] above)
    private int cardSuit;

    /**
     * Constructor to create a card
     *
     * @param suit Specifies suit of card
     * @param rank Specifies which number or face card the card is
     * @throws PlayingCardException if rank or suit is invalid
     */
    public Card(int suit, int rank) throws PlayingCardException {

        // suit = 0 is joker, rank must be 1 or 2
        if (suit == 0) {
            if ((rank < 1) || (rank > 2)) {
                throw new PlayingCardException("Invalid rank for Joker:" + rank);
            }
            cardRank = rank;
            cardSuit = 0;
            // all normal suits
        } else {

            // check if valid rank
            if ((rank < 1) || (rank > 13)) {
                throw new PlayingCardException("Invalid rank:" + rank);
            } else {
                cardRank = rank;
            }

            // check if valid suit
            if ((suit < 1) || (suit > 4)) {
                throw new PlayingCardException("Invalid suit:" + suit);
            } else {
                cardSuit = suit;
            }
        }
    }

    /**
     * Getter for rank
     *
     * @return card's rank
     */
    public int getRank() {
        return cardRank;
    }

    /**
     * Getter for suit
     *
     * @return card's suit
     */
    public int getSuit() {
        return cardSuit;
    }

    /**
     * Outputs the card's information as a string
     */
    @Override
    public String toString() {
        if (cardSuit == 0) {
            return SUIT[cardSuit] + " #" + cardRank;
        } else {
            return RANK[cardRank] + " " + SUIT[cardSuit];
        }
    }

    /**
     * main() for testing the card class
     */
    public static void main(String[] args) {
        try {
            Card c1 = new Card(4, 1);    // A Spades
            System.out.println(c1);
            c1 = new Card(1, 10);   // 10 Clubs
            System.out.println(c1);
            c1 = new Card(0, 2);        // Joker #2
            System.out.println(c1);
            c1 = new Card(5, 10);        // generate exception here
        } catch (PlayingCardException e) {
            System.out.println("PlayingCardException: " + e.getMessage());
        }
    }
}

/**
 * Represents : n decks of 52 (or 54) playing cards
 *
 * @author James Clark
 */
class Decks {

    //used to keep track of original n*52 or n*54 cards
    private List<Card> originalDecks;

    //this starts with copying cards from originalDecks to refresh the game for each hand
    //see reset(): resets gameDecks to originalDecks
    private List<Card> gameDecks;

    // number of decks in this object
    private int numberDecks;
    // with jokers?
    private boolean withJokers;

    /**
     * Creates n decks (54 or 52 cards each deck - with or without Jokers) of playing cards in originalDecks and copy them to gameDecks.
     *
     * @param n          number of decks to be created
     * @param withJokers specifies if jokers should be in deck.
     */
    public Decks(int n, boolean withJokers) {
        // stores number of decks
        this.numberDecks = n;
        //stores original set of cards to reset to
        originalDecks = new ArrayList<>();

        try {
            // create n number of decks
            for (int k = 0; k < numberDecks; k++) {
                // make two jokers if requested
                if (withJokers) {
                    this.withJokers = withJokers;
                    this.originalDecks.add(new Card(0, 1));
                    this.originalDecks.add(new Card(0, 2));
                }

                // creates full 52 card deck
                for (int i = 1; i <= 4; i++) {
                    for (int j = 1; j <= 13; j++) {
                        this.originalDecks.add(new Card(i, j));
                    }
                }
            }
        } catch (PlayingCardException e) {
            System.out.println("PlayingCardException: " + e.getMessage());
        }
        // copies original deck to the game deck for play
        this.gameDecks = new ArrayList<>(originalDecks);
    }

    /**
     * Shuffles cards in gameDecks
     */
    public void shuffle() {
        Collections.shuffle(gameDecks);
    }

    /**
     * Deals cards from the gameDecks.
     *
     * @param numberCards number of cards to deal
     * @return a list containing cards that were dealt
     * @throws PlayingCardException if numberCard > number of remaining cards
     */
    public List<Card> deal(int numberCards) throws PlayingCardException {
        //List of dealt cards
        List<Card> dealt = new ArrayList<>();

        // if requested number of cards is greater than what is in deck
        if (numberCards > remainSize()) {
            throw new PlayingCardException("There are no more cards in the deck!");

            // deal numberCards amount
        } else {
            for (int i = 0; i < numberCards; i++) {
                dealt.add(gameDecks.remove(0));
            }
        }
        return dealt;
    }

    /**
     * Resets gameDecks by getting all cards from the originalDecks.
     */
    public void reset() {
        this.gameDecks.clear();
        this.gameDecks = new ArrayList<>(originalDecks);

    }

    /**
     * Getter for number of decks
     *
     * @return number of decks
     */
    public int getNumberDecks() {
        return numberDecks;
    }

    /**
     * Getter for is there are jokers in the deck
     *
     * @return with Jokers?
     */
    public boolean getWithJokers() {
        return withJokers;
    }

    /**
     * Gets number of remaining cards in gameDecks.
     *
     * @return size of game deck
     */
    public int remainSize() {
        return gameDecks.size();
    }

    /**
     * A string representing cards in the gameDecks
     *
     * @return string of information
     */
    @Override
    public String toString() {
        return "" + gameDecks;
    }

    /**
     * main() to test Decks class
     */
    public static void main(String[] args) {

        System.out.println("*******    Create 2 decks of cards      ********\n");
        Decks decks = new Decks(2, true);
        System.out.println("getNumberDecks:" + decks.getNumberDecks());
        System.out.println("getWithJokers:" + decks.getWithJokers());

        for (int j = 0; j < 2; j++) {
            System.out.println("\n************************************************\n");
            System.out.println("Loop # " + j + "\n");
            System.out.println("Before shuffle:" + decks.remainSize() + " cards");
            System.out.println("\n\t" + decks);
            System.out.println("\n==============================================\n");

            int numHands = 5;
            int cardsPerHand = 27;

            for (int i = 0; i < numHands; i++) {
                decks.shuffle();
                System.out.println("After shuffle:" + decks.remainSize() + " cards");
                System.out.println("\n\t" + decks);
                try {
                    System.out.println("\n\nHand " + i + ":" + cardsPerHand + " cards");
                    System.out.println("\n\t" + decks.deal(cardsPerHand));
                    System.out.println("\n\nRemain:" + decks.remainSize() + " cards");
                    System.out.println("\n\t" + decks);
                    System.out.println("\n==============================================\n");
                } catch (PlayingCardException e) {
                    System.out.println("*** In catch block:PlayingCardException:Error Msg: " + e.getMessage());
                }
            }

            decks.reset();
        }
    }

}
