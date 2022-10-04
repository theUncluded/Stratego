package application;

import java.io.Serializable;

public class Flag extends Piece implements Serializable {

	/**
	 * The constructor takes in parameters for the Piece that may change throughout the course of the game
	 * @param team - the color of the Piece's team
	 * @param x - the row of the Piece in the Board
	 * @param y - the column of the Piece in the Board
	 * @param isRevealed - if the Piece is revealed or not
	 */
	public Flag(String team, int x, int y, boolean isRevealed) {
		super(team, -1, x, y, isRevealed, false, false, true);
	}
	
	/**
	 * The attack method returns a -1 since a Flag is unable to attack or move
	 */
	public int attack(Piece enemyPiece) {
		return 0;
	}

	/**
	 * Returns the name of the Piece
	 */
	public String pieceType() {
		return "Flag ";
	}

}
