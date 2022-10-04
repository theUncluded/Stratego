package application;

import java.io.Serializable;

public class General extends Piece implements Serializable {

	/**
	 * The constructor takes in parameters for the Piece that may change throughout the course of the game
	 * @param team - the color of the Piece's team
	 * @param x - the row of the Piece in the Board
	 * @param y - the column of the Piece in the Board
	 * @param isRevealed - if the Piece is revealed or not
	 */
	public General(String team, int x, int y, boolean isRevealed) {
		super(team, 9, x, y, isRevealed, true, false, false);
	}
	
	/**
	 * The attack method returns an integer based on the result of the attack
	 * Return a 1 if the General's rank is greater than the enemy Piece
	 * Return a 0 if the General attacks another General or a Bomb
	 * Return a -1 if the General loses the attack
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
		return "General (Rank 9) ";
	}

}
