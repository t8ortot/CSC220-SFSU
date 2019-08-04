import VP.VideoPoker;

/**
 * Used to test and play the project.
 * Default blanace is $100.
 * If a different balance is requested, use java TestVideoPoker N to start with $N amount.
 *
 * @author James Clark
 */
class TestVideoPoker {

    /**
     * main() for the project
     *
     * @param args amount of money to start with
     */
    public static void main(String[] args) {
        VideoPoker pokergame;
        // if custom amount of money is requested
        if (args.length > 0) {
            pokergame = new VideoPoker(Integer.parseInt(args[0]));
            //default
        } else {
            pokergame = new VideoPoker();
        }
        pokergame.play();
    }
}
