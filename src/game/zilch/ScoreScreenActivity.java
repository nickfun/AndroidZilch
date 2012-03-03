package game.zilch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Android Activity for the Score Screen. Displays the scores for both players. Is displayed between player rounds.
 * @author nick & chad
 *
 */
public class ScoreScreenActivity extends Activity {

	// public static is the easy way out!
	public static Button btnQuit;
	public static Button btnContinue;
	public static TextView lblPlayer1score;
	public static TextView lblPlayer2score;
	public static TextView lblPlayer1name;
	public static TextView lblPlayer2name;

	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scorescreen_layout);

		// get the score lables
		lblPlayer1score = (TextView) this.findViewById(R.id.lblScorePlayer1);
		lblPlayer2score = (TextView) this.findViewById(R.id.lblScorePlayer2);
		lblPlayer1name = (TextView) this.findViewById(R.id.lblNamePlayer1);
		lblPlayer2name = (TextView) this.findViewById(R.id.lblNamePlayer2);
		lblPlayer1name.setText(Game.player1.getName());
		lblPlayer2name.setText(Game.player2.getName());
		lblPlayer1score.setText("Score: " + Integer.toString(Game.player1.getScore()));
		lblPlayer2score.setText("Score: " + Integer.toString(Game.player2.getScore()));

		// get the buttons
		btnContinue = (Button) this.findViewById(R.id.btnContinue);
		if(Game.finish) {
			Player winner = (Game.player1.getScore() > Game.player2.getScore()) ? Game.player1 : Game.player2;
			if(Game.player1.getScore() == Game.player2.getScore()) { // in the rare case of a tie
				winner = Game.pastThePostFirst;
			}
			btnContinue.setText(winner.getName() + " Wins!");
		} else if(Game.lastTurn) {
			btnContinue.setText("Last turn for " + Game.currentPlayer.getName());
		} else {
			btnContinue.setText("Continue as " + Game.currentPlayer.getName());
		}
		btnQuit = (Button) this.findViewById(R.id.btnQuit);

		// CALLBACK for the CONTINUE button
		btnContinue.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// Chad code
				if (Game.finish != true) {
					// goto the game screen
					Intent myIntent = new Intent(v.getContext(), GameActivity.class);
					startActivityForResult(myIntent, 0);
				}
				finish();
			}
		});

		// CALLBACK for the QUIT button
		btnQuit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// Chad code
				System.exit(0);
			}
		});

	}
}