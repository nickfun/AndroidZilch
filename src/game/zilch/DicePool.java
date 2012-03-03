package game.zilch;
import java.util.*;

/**
 * Dice Pool class manages a group of dice. Can have several dice be held from rolling the group.
 * @author nick & chad
 *
 */
public class DicePool {
    /** There is a potential that the DicePool has dice of different kinds. If this is the case. This is set to true */
    private boolean mixedPool;
    /** The maximum value the dice pool can generate from a single die */
    private int maxValue;
    /** The collection of dice in the dice pool */
    private ArrayList<Die> dice;
    /**
     * Default constructor makes no dice. This can be expanded by addDie and addDice.
     */
    public DicePool() {
        mixedPool = false;
        dice = new ArrayList<Die>();
        maxValue = 0;
    }
    /**
     * Sets up a dice pool with a specific amount.
     * For zilch, this will be 6 and 6.
     * @param numberOfDice Number of dice in dice pool
     * @param maxValue The maximum value the dice can achieve in this initial set
     */
    public DicePool(int numberOfDice, int maxValue) {
    	dice = new ArrayList<Die>();
        mixedPool = false;
        this.maxValue = maxValue;
        numberOfDice = Math.abs(numberOfDice);
        for(int i = 0; i < numberOfDice; i++) {
            dice.add(new Die(maxValue));
        }
    }
    /**
     * Add a set of dice. It can have different sizes from what already exists in the pool
     * @param numberOfDice number of dice to be added.
     * @param maxValue the max value of the dice to be added.
     * @return true if all of the dice were successfully added.
     */
    public boolean addDice(int numberOfDice, int maxValue) {
        if (maxValue == 0) maxValue = 1;
        maxValue = Math.abs(maxValue);
        setMaxValue(maxValue);
        boolean success = true;
        for(int i = 0; i < numberOfDice; i++) {
            success = addDie(maxValue) && success;
        }
        return success;
    }
    /**
     * Adds one die at one specific value.
     * @param maxValue The highest the die can be rolled at
     * @return true if the die was added
     */
    public boolean addDie(int maxValue) {
        boolean success = dice.add(new Die(maxValue));
        if(success) setMaxValue(maxValue);
        return success;
    }
    /**
     * This is important in Zilch because we need to know if all of the dice are held.
     * @return true if all dice are held
     */
    public boolean areAllDiceHeld() {
        for(Die d: dice) {
            if(d.getHold() == false) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns a list of dice of a certain value. Keep in mind that initialized dice start at 0, so 0 is a possible outcome if the die was not rolled.
     * @return List of Lists. Each sublist is a list of dice at the value within the index.
     */
    public List<List<Die>> diceByLastValue() {
        List<List<Die>> tableDice = new ArrayList<List<Die>>();
        // create each sub list
        for(int i = 0; i <= maxValue; i++) { // Need to add one more in order to account for 0 values
            tableDice.add(new ArrayList<Die>());
        }
        // add each die to each sub list corresponding to their value
        for(Die d: dice) {
            int value = d.getLastValue();
            tableDice.get(value).add(d);
        }
        return tableDice;
    }
    public List<Die> getAllDice() {
        return new ArrayList<Die>(dice);
    }
    public int getMaxValue() {
        return maxValue;
    }
    /**
     * If this is set to true then it means the dice pool is heterogenous; or, in other words, the pool has dice that have different ranges
     * @return True if mixed pool, false if not.
     */
    public boolean isMixedPool() {
        return mixedPool;
    }
    /** Issues roll to the collection of dice */
    public void rollAll() {
        for(Die d: dice) {
            d.roll();
        }
    }
    public boolean removeAllAtMaxValue(int maxValue) {
        boolean success = true;
        for(Die d: this.getAllDice()) {
            if (d.getMaxValue() == maxValue) {
                success = this.removeDie(d) && success;
            }
        }
        return success;
    }
    public boolean removeAllDice() {
        boolean success = true;
        for(Die d: getAllDice()) {
            success = removeDie(d) && success;
        }
        return success;
    }
    /**
     * Removes the Die using the reference to the Die. This is probably the best way to remove the die besides by value
     * @param d The die you want to remove from the list
     * @return true if removing the die was successful.
     */
    public boolean removeDie(Die d) {
        boolean success = dice.remove(d);
        if(success && mixedPool) {
            int min = maxValue;
            int max = 0;
            for(Die di: dice) {
                min = Math.min(min, di.getMaxValue());
                max = Math.max(max, di.getMaxValue());
            }
            mixedPool = min != max;
            maxValue = max;
        }
        return success;
    }
    /** Issues setHold to all dice. Usually used for resetting hold */
    public void setAllHold(boolean hold) {
        for(Die d: dice) {
            d.setHold(hold);
        }
    }
    /**
     * This will be reworked into other methods in order to optimize for homogenous dice pools.
     * @param maxValue The maxValue being passed into the class
     */
    private void setMaxValue(int maxValue) {
        if(this.maxValue == 0) {
            this.maxValue = maxValue;
        } else if(this.maxValue != maxValue) {
            this.maxValue = Math.max(this.maxValue, maxValue);
            mixedPool = true;
        }
    }
    /**
     * Returns the number of dice in the dice pool
     * @return 
     */
    public int size() {
        return dice.size();
    }
    /**
     * Returns a table of totals in an int[] array. 
     * @return Array of numbers. Each index is a value for the maxValue, so the number of elements is actually maxValue + 1 where the 0th index is for dice that happen to not be ever rolled.
     */
    public int[] totalsByValue() {
        int[] tableValues = new int[maxValue + 1]; // 0 index for 0 counts. N index for N counts.
        // Initialize all values
        for(int i = 0; i < maxValue; i++) {
            tableValues[i] = 0;
        }
        // Increment the dice with values
        for(Die d: dice) {
            tableValues[d.getLastValue()]++;
        }
        return tableValues;
    }
    /**
     * A basic toString that will print out all of the values of last rolls in no particular order.
     * @return String in a digit + space format.
     */
    @Override
    public String toString() {
        String ltorPrint = Integer.toString(size()) + " dice: ";
        for(Die d: dice) {
            ltorPrint += d.getLastValue() + " ";
        }
        return ltorPrint;
    }
}
