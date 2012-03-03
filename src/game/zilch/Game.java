package game.zilch;

/**
 * Game class manages the state of the game, the players, and the score
 * @author nick & Chad
 *
 */
public class Game {
	static Player player1 = new Player();
	static Player player2 = new Player();
	static Player currentPlayer = player1;
	static Player pastThePostFirst = player1; // this will get set later.
	static boolean lastTurn = false;
	static boolean finish = false;
	static int limit = 10000;
	static void switchCurrentPlayer() {
		currentPlayer = (currentPlayer == player1)? player2 : player1;
	}
}
