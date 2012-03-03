package game.zilch;
import java.util.Random;
/**
 * This class is a comprehensive die rolling object that can go from 1 to the limit of integer's positive range.
 * @author Chad J Lucas
 */
public class Die {
    /** The maximum possible value that can be return from a roll. */
    private int maxValue;
    /** This will save the last roll made. It's convenient caching and makes the object worth having. */
    private int lastValue;
    /** This is the random number generator for this die. */
    private Random roller;
    /** This is used to determine whether to hold the dice and not roll */
    private boolean hold;
    /**
     * By default the max value is 6 because most dice made are 6. This is so it can also generate the right values;
     */
    public Die() {
        maxValue = 6;
        roller = new Random(System.nanoTime());
        lastValue = 0;
    }
    /**
     * This will set the maximum value of the die to the value passed in.
     * If it takes a negative number, it will be set positive because this class
     * really shouldn't be throwing exceptions for something so basic. It's 
     * really your fault if you pass in a negative value expecting something as 
     * intuitive as a dice roller to do something different.
     * @param maxValue Takes the absolute value of this number and sets it as max value
     */
    public Die(int maxValue) {
        if(maxValue == 0) maxValue = 1;
        this.maxValue = Math.abs(maxValue);
        roller = new Random(System.nanoTime());
        lastValue = 0;
    }
    public void setHold(boolean hold) {
        this.hold = hold;
    }
    public boolean getHold() {
        return hold;
    }
    /**
     * This class saves the last value that was rolled for your convenience.
     * @return The value from the last roll
     */
    public int getLastValue() {
        return lastValue;
    }
    /**
     * The maximum value a roll will generate.
     * @return The maximum value a roll will generate.
     */
    public int getMaxValue() {
        return maxValue;
    }
    /**
     * Sets maxValue field to the absolute value of what is passed into this method.
     * @param maxValue The magnitude of the maximum possible outcome form this roller.
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = Math.abs(maxValue);
    }
    /**
     * Roll will generate a number from 1 to maxValue.
     * @return 1 to maxValue.
     */
    public int roll()
    {
        if (hold) {
            return lastValue;
        } else {
            return lastValue = roller.nextInt(maxValue) + 1;
        }
    }
}
