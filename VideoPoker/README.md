# Video Poker

A video poker game in Java.

Short Description and Poker rules:

Video poker is also known as draw poker. The dealer uses a 52-card deck, which is played fresh after each playerHand. The player is dealt one five-card poker playerHand. After the first draw, which is automatic, you may hold any of the cards and draw again to replace the cards that you haven't chosen to hold. Your cards are compared to a table of winning combinations. The object is to get the best possible combination so that you earn the highest payout on the bet you placed.

Winning Combinations:  
1. One Pair: one pair of the same card
2. Two Pair: two sets of pairs of the same card denomination. 
3. Three of a Kind: three cards of the same denomination. 
4. Straight: five consecutive denomination cards of different suit. 
5. Flush: five non-consecutive denomination cards of the same suit. 
6. Full House: a set of three cards of the same denomination plus a set of two cards of the same denomination. 
7. Four of a kind: four cards of the same denomination. 
8. Straight Flush: five consecutive denomination cards of the same suit. 
9. Royal Flush: five consecutive denomination cards of the same suit, starting from 10 and ending with an ace

How to run (assuming in VideoPoker directory):
- javac TestVideoPoker.java VP/*.java
- java TestVideoPoker to start with $100 default
or 
- java TestVideoPoker N to start with $N amount.

How to use:
- A payout table will be displayed, followed by a prompt for a bet. 
- Enter a bet amount.
- The player will be dealt a hand and it will be displayed.
- A new prompt will ask if the player would like to replace any cards.
- Enter the card numbers to be replaced or -1 to keep all cards.