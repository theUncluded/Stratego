package application;

import java.io.Serializable;

public class Marshall extends Piece implements Serializable {

	/**
	 * The constructor takes in parameters for the Piece that may change throughout the course of the game
	 * @param team - the color of the Piece's team
	 * @param x - the row of the Piece in the Board
	 * @param y - the column of the Piece in the Board
	 * @param isRevealed - if the Piece is revealed or not
	 */
	public Marshall(String team, int x, int y, boolean isRevealed) {
		super(team, 10, x, y, isRevealed, true, false, false);
	}
	
	/**
	 * The attack method returns an integer based on the result of the attack
	 * Returns a 1 if the Marshall beats the enemy Piece
	 * Returns a 0 if the Marshall attacks another Marshall or a Bomb
	 * Returns a -1 if the Marshall loses the attack
	 */
	public int attack(Piece enemyPiece) {
		enemyPiece.setRevealed(true);
		this.setRevealed(true);
		if (rank > enemyPiece.getRank()) {
			return 1;
		}
		else if (rank == enemyPiece.getRank() || enemyPiece.isBomb()) {
			return 0;
		}
		else {
			return -1;
		}
	}

	/**
	 * Returns the name of the Piece and the Piece's rank
	 */
	public String pieceType() {
		return "Marshall (Rank 10) ";
	}
	
	

}
