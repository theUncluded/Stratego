package application;

import java.io.Serializable;

public class EmptyPiece extends Piece implements Serializable {

	/**
	 * Calls the default super constructor
	 */
	public EmptyPiece() {
		super();
	}
	
	/**
	 * The attack method returns a -1 since an EmptyPiece is unable to attack or move
	 */
	public int attack(Piece enemyPiece) {
		return 0;
	}


	/**
	 * An EmptyPiece has no type
	 */
	public String pieceType() {
		return null;
	}
	
}
