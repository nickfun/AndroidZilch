package game.zilch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

/**
 * Android Activity for the start up screen. Players choose their name.
 * @author nick & chad
 *
 */
public class MenuActivity extends Activity {

	/** keep a copy of the widgets in the layout */
	private Button btnPlay;
	private Button btnQuit;
	private EditText txtPlayer1;
	private EditText txtPlayer2;

	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// get access to the buttons & stuff from layout
		this.btnPlay = (Button) this.findViewById(R.id.btnPlay);
		this.btnQuit = (Button) this.findViewById(R.id.btnQuit);
		this.txtPlayer1 = (EditText) this.findViewById(R.id.txtPlayer1);
		this.txtPlayer2 = (EditText) this.findViewById(R.id.txtPlayer2);

		// CALLBACK for the PLAY button
		this.btnPlay.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code here
				Game.player1 = new Player(txtPlayer1.getText().toString(), 0);
				Game.player2 = new Player(txtPlayer2.getText().toString(), 0);
				Game.currentPlayer = Game.player1;
				// goto the score screen
				Intent myIntent = new Intent(v.getContext(), ScoreScreenActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		// CALLBACK for the QUIT button
		this.btnQuit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// chad code here
				finish();
			}
		});
	}
}