package application;

import java.io.Serializable;

public class Colonel extends Piece implements Serializable {

	/**
	 * The constructor takes in parameters for the Piece that may change throughout the course of the game
	 * @param team - the color of the Piece's team
	 * @param x - the row of the Piece in the Board
	 * @param y - the column of the Piece in the Board
	 * @param isRevealed - if the Piece is revealed or not
	 */
	public Colonel(String team, int x, int y, boolean isRevealed) {
		super(team, 8, x, y, isRevealed, true, false, false);
	}
	
	/**
	 * The attack method returns an integer based on the result of the attack
	 * Return a 1 if the Colonel's rank is greater than the enemy Piece
	 * Return a 0 if the Colonel attacks another Colonel or a Bomb
	 * Return a -1 if the Colonel loses the attack
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
		return "Colonel (Rank 8) ";
	}

}
