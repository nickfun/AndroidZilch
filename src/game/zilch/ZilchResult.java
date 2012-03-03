package game.zilch;

/**
 * ZilchResult detects scoring patterns in rolls of dice pools
 * @author nick & chad
 *
 */
public class ZilchResult {
    public final int[] results;
    public final boolean zilch;
    public final boolean straight;
    public final int pairs;
    public final int firstTriple;
    public final int secondTriple;
    public final int ones;
    public final int fives;
    public final int score;
    public ZilchResult() {
        results = new int[0];
        zilch = true;
        straight = false;
        score = pairs = firstTriple = secondTriple = ones = fives = 0;
        
    }
    public ZilchResult(DicePool dp) {
        this(dp.totalsByValue());
    }
    public ZilchResult(int[] results) {
        this.results = results;
        boolean temp_straight = true;
        int temp_pairs = 0;
        int temp_firstTriple = 0;
        int temp_secondTriple = 0;
        ones = results[1];
        fives = results[5];
        for(int i = 1; i < results.length; i++) {
            temp_straight = (results[i] == 1) && temp_straight;
            if(results[i] == 2) temp_pairs++;
            if(results[i] >= 3 && temp_firstTriple == 0) temp_firstTriple = i;
            else if (results[i] >= 3) temp_secondTriple = i;
        }
        straight = temp_straight;
        firstTriple = temp_firstTriple;
        secondTriple = temp_secondTriple;
        pairs = temp_pairs;
        zilch = (straight == true || pairs == 3 || firstTriple != 0 || fives > 0 || ones > 0) == false;
        // Calculate Score
        int temp_score = 0;
        int leftover_ones = 0;
        int leftover_fives = 0;
        if(firstTriple == 1) {
            leftover_ones = ones - 3;
        } else {
            leftover_ones = ones;
        }
        if(firstTriple == 5 || secondTriple == 5) {
            leftover_fives = fives - 3;
        } else {
            leftover_fives = fives;
        }
        if (firstTriple == 1) {
            temp_score = 1000;
        }
        if(firstTriple > 1) {
            temp_score += firstTriple * 100;
        }
        if(secondTriple > 1) {
            temp_score += secondTriple * 100;
        }
        // BEGIN NEW CODE
        if(firstTriple != 1) {
        	temp_score += (leftover_ones * 100);
        }
        if(firstTriple != 5 || secondTriple != 5) {
        	temp_score += (leftover_fives * 50);
        }
        if(firstTriple != 0 && results[firstTriple] > 3) {
        	temp_score += (results[firstTriple] - 3) * 100;
        }
        if(secondTriple != 0 && results[firstTriple] > 3) {
        	temp_score += (results[secondTriple] - 3) * 100;
        }
        // END NEW CODE
        if(straight) {
            temp_score = 1500;
        } else if (pairs == 3) {
            temp_score = 750;
        }
        score = temp_score;
    }
    @Override
    public String toString() {
        String s = "";
        s += "Dice : ";
        for(int i = 0; i < results.length; i++) {
            s += "" + results[i] + " ";
        }
        s += "\n";
        s += ((straight)? "Straight. 1500 points!" : "Not a Straight.") + "\n";
        s += ((pairs == 3)? "3 Pair. 750 points!" : (pairs != 0)? "Only " + pairs + " pairs." : "No pairs.") + "\n";
        if(firstTriple == 1) {
            s += "Triple of 1. 1000 points!\n";
        } else if(firstTriple != 0) {
            s += "Triple of " + firstTriple + ".\n";
        }
        if(secondTriple != 0)
        {
            s += "Another triple of " + secondTriple + ".\n";
        }
        s += "" + ones + " ones.\n";
        s += "" + fives + " fives.\n";
        s += "Score: " + score + "\n";
        s += ((zilch)? "Zilch!" : "Able to reroll") + "\n";
        return s;
    }
}
