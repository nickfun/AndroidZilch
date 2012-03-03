package game.zilch;

/**
 * Manage a palyer of the game
 * @author nick & chad
 *
 */
public class Player {
    /** Used for creating a 'unique' name */
    private static int count = 0;
    /** name of the player */
    private String name;
    /** the score of the player */
    private int score;
    /**
     * Default constructor puts in okay defaults.
     */
    public Player() {
        name = "Player" + ++count;
        score = 0;
    }
    /**
     * Default score constructor. It expects a name to be passed in.
     * @param name Name for the player
     */
    public Player(String name) {
        this.name = name;
        score = 0;
    }
    /**
     * In case you need to set both, use this constructor.
     * @param name Name for the player
     * @param score Score to be initialized to
     */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /** Get this player's name */
    public String getName() {
        return name;
    }
    /** get this player's score */
    public int getScore() {
        return score;
    }
    /**
     * Set the name to something else
     * @param name Name to be set to player's name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Set the score to something else
     * @param score Score to be set to player's score
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Add to the player's current score.
     * @param add Add amount
     * @return the current score after add
     */
    public int addScore(int add) {
        return this.score += add;
    }
    /**
     * Remove from the player's current score.
     * @param remove Remove amount
     * @return the current score after remove
     */
    public int removeScore(int remove) {
        return this.score -= remove;
    }
    /**
     * Give a basic read out of the player's attributes
     * @return a string that gives all of player's information.
     */
    public String toString() {
        return name + "'s score is " + score;
    }
}
