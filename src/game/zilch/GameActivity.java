package game.zilch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Android Activity for the screen of choosing dice and rolling. The heart of the application is right here.
 * @author nick & chad
 *
 */
public class GameActivity extends Activity {

	private ImageButton btnDie1;
	private ImageButton btnDie2;
	private ImageButton btnDie3;
	private ImageButton btnDie4;
	private ImageButton btnDie5;
	private ImageButton btnDie6;
	private Button btnRoll;
	private Button btnEndRound;
	private TextView playerName;
	private TextView playerScore;
	private TextView selectedScore;
	private TextView bankScore;
	private DicePool dice;
	private List<Die> result;
	private ArrayList<ImageButton> diceButtons;
	private boolean[] highlighted = new boolean[6];
	private int currentBankScore;
	private ZilchResult rollZilchResult;
	private ZilchResult currentZilchResult;
	boolean firstRoll;

	/**
	 * Set a die to a highlighted image
	 * @param dieButton
	 * @param value
	 */
	public void setDieButtonToHighlighted(ImageButton dieButton, int value) {
		switch(value) {
		case 1:
			dieButton.setImageResource(R.drawable.die1h);
			break;
		case 2:
			dieButton.setImageResource(R.drawable.die2h);
			break;
		case 3:
			dieButton.setImageResource(R.drawable.die3h);
			break;
		case 4:
			dieButton.setImageResource(R.drawable.die4h);
			break;
		case 5:
			dieButton.setImageResource(R.drawable.die5h);
			break;
		case 6:
			dieButton.setImageResource(R.drawable.die6h);
			break;
		default:
			dieButton.setImageResource(R.drawable.die0h);
		}
	}
	/**
	 * Set the ImageButton to a image from resource
	 * @param dieButton
	 * @param value
	 */
	public void setDieButtonTo(ImageButton dieButton, int value) {
		switch(value) {
		case 1:
			dieButton.setImageResource(R.drawable.die1);
			break;
		case 2:
			dieButton.setImageResource(R.drawable.die2);
			break;
		case 3:
			dieButton.setImageResource(R.drawable.die3);
			break;
		case 4:
			dieButton.setImageResource(R.drawable.die4);
			break;
		case 5:
			dieButton.setImageResource(R.drawable.die5);
			break;
		case 6:
			dieButton.setImageResource(R.drawable.die6);
			break;
		default:
			dieButton.setImageResource(R.drawable.die0);
		}
	}
	/**
	 * Toggles the state of the button from highlight to unhighlight.
	 * @param button Button that's going to be changed
	 * @param buttonNumber the number in the results this represents
	 */
	public void toggleDieButtonClick(ImageButton button, int buttonNumber) {
		highlighted[buttonNumber] = !highlighted[buttonNumber]; // toggle this
		if(highlighted[buttonNumber] == true) {
			setDieButtonToHighlighted(button, result.get(buttonNumber).getLastValue());
		} else {
			setDieButtonTo(button, result.get(buttonNumber).getLastValue());
		}
	}
	/**
	 * Setup the zilch display
	 */
	public void setupZilch() {
		selectedScore.setText("Selected Score: ZILCH!");
		bankScore.setText("Bank: ZILCH!");
		btnRoll.setText("ZILCH!");
		btnEndRound.setText("ZILCH!");
		currentBankScore = 0;
	}
	/**
	 * update the score display along with other things such as the currentZilchResult from selecting.
	 */
	public void updateSelectScore() {
		int[] table = new int[7];
		for(int i = 0; i < 7; i++) {
			table[i] = 0;
		}
		for(int i = 0; i < dice.size(); i++) {
			if(highlighted[i] == true) {
				table[result.get(i).getLastValue()]++;
			}
		}
		ZilchResult zr = new ZilchResult(table);
		currentZilchResult = zr;
		selectedScore.setText("Selected Score: " + Integer.toString(zr.score));
	}
	/**
	 * The number of dice that are actually being used when ZilchResult is being done.
	 * This prevents someone from selecting all of the dice to force a reroll of all dice or dice that don't count
	 * @return the number of dice that have effect on score
	 */
	public int numberOfDiceUsed() {
		ZilchResult zr = currentZilchResult;
		int[] table = dice.totalsByValue();
		int dice = 0;
		if(zr.straight) return 6;
		if(zr.pairs == 3) return 6;
		if(zr.firstTriple > 0) dice += table[zr.firstTriple];
		if(zr.secondTriple > 0) return 6;
		if(zr.firstTriple == 1) dice += (zr.ones - 3);
		else dice += zr.ones;
		if(zr.firstTriple == 5 || zr.secondTriple == 5) dice += (zr.fives - 3);
		else dice += zr.fives;
		return dice;
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamescreen_layout);

		// grab the buttons
		btnDie1 = (ImageButton) this.findViewById(R.id.btnDie1);
		btnDie2 = (ImageButton) this.findViewById(R.id.btnDie2);
		btnDie3 = (ImageButton) this.findViewById(R.id.btnDie3);
		btnDie4 = (ImageButton) this.findViewById(R.id.btnDie4);
		btnDie5 = (ImageButton) this.findViewById(R.id.btnDie5);
		btnDie6 = (ImageButton) this.findViewById(R.id.btnDie6);
		diceButtons = new ArrayList<ImageButton>();
		diceButtons.add(btnDie1);
		diceButtons.add(btnDie2);
		diceButtons.add(btnDie3);
		diceButtons.add(btnDie4);
		diceButtons.add(btnDie5);
		diceButtons.add(btnDie6);
		btnRoll = (Button) this.findViewById(R.id.btnRoll);
		btnEndRound = (Button) this.findViewById(R.id.btnEndRound);
		playerName = (TextView) this.findViewById(R.id.playerName);
		playerScore = (TextView) this.findViewById(R.id.playerScore);
		selectedScore = (TextView) this.findViewById(R.id.selectedScore);
		bankScore = (TextView) this.findViewById(R.id.bankScore);
		dice = new DicePool(6, 6);
		result = dice.getAllDice();
		playerName.setText(Game.currentPlayer.getName());
		playerScore.setText("Player Score: " + Integer.toString(Game.currentPlayer.getScore()));
		selectedScore.setText("Selected Score: 0");
		btnRoll.setText("Click for first roll!");
		bankScore.setText("Bank: 0");
		for(ImageButton b: diceButtons) {
			setDieButtonToHighlighted(b, 0);
		}
		for(int i = 0; i < dice.size(); i++) {
			highlighted[i] = false;
		}
		currentBankScore = 0;
		currentZilchResult = new ZilchResult(new int[]{0, 0, 0, 0, 0, 0, 0});
		rollZilchResult = new ZilchResult(new int[]{0, 0, 0, 0, 0, 0, 0});
		firstRoll = true;

		// register callbacks for all of these godamn things why so MANY!?

		// CALLBACK for the DIE 1 button
		btnDie1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code index 0
				if(dice.size() < 1) return;
				toggleDieButtonClick(btnDie1, 0);
				updateSelectScore();
			}
		});

		// CALLBACK for the DIE 2 button
		btnDie2.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// chad code
				if(dice.size() < 2) return;
				toggleDieButtonClick(btnDie2, 1);
				updateSelectScore();
			}
		});

		// CALLBACK for the DIE 3 button
		btnDie3.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code
				if(dice.size() < 3) return;
				toggleDieButtonClick(btnDie3, 2);
				updateSelectScore();
			}
		});

		// CALLBACK for the DIE 4 button
		btnDie4.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code
				if(dice.size() < 4) return;
				toggleDieButtonClick(btnDie4, 3);
				updateSelectScore();
			}
		});

		// CALLBACK for the DIE 5 button
		btnDie5.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code
				if(dice.size() < 5) return;
				toggleDieButtonClick(btnDie5, 4);
				updateSelectScore();
			}
		});

		// CALLBACK for the DIE 6 button
		btnDie6.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code
				if(dice.size() < 6) return;
				toggleDieButtonClick(btnDie6, 5);
				updateSelectScore();
			}
		});
		
		// CALLBACK for the ROLL button
		btnRoll.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				btnRoll.setText("Select Dice To Roll Again");
				if(firstRoll || currentZilchResult.score > 0) {
					firstRoll = false;
					currentBankScore += currentZilchResult.score;
					bankScore.setText("Bank: " + currentBankScore);
					int afterTake = dice.size() - numberOfDiceUsed();
					if(afterTake == 0) {
						dice = new DicePool(6, 6);
					}
					else {
						dice = new DicePool(afterTake, 6);
					}
					dice.rollAll();
					result = dice.getAllDice();
					currentZilchResult = new ZilchResult(dice);
					rollZilchResult = new ZilchResult(dice);
					for(int i = 0; i < 6; i++) {
						highlighted[i] = false;
					}
					for(int i = 0; i < dice.size(); i++) {
						setDieButtonTo(diceButtons.get(i), result.get(i).getLastValue());
					}
					for(int i = dice.size(); i < 6; i++) {
						setDieButtonTo(diceButtons.get(i), 0);
					}
					if(currentZilchResult.zilch) {
						setupZilch();
						return;
					}
					updateSelectScore();
				} else if (rollZilchResult.zilch) {
					Game.switchCurrentPlayer();
					Intent myIntent = new Intent(v.getContext(), ScoreScreenActivity.class);
					startActivityForResult(myIntent, 0);
					finish();
				}
			}
		});
		
		// CALLBACK for the END ROUND button
		btnEndRound.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code
				Game.currentPlayer.addScore(currentBankScore + currentZilchResult.score);
				if(Game.lastTurn) {
					Game.finish = true;
				}
				if(Game.currentPlayer.getScore() >= Game.limit && Game.lastTurn == false) {
					Game.lastTurn = true;
					Game.pastThePostFirst = Game.currentPlayer;
				}
				Game.switchCurrentPlayer();
				Intent myIntent = new Intent(v.getContext(), ScoreScreenActivity.class);
				startActivityForResult(myIntent, 0);
				finish();
			}
		});
	}

}
