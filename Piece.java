package application;

import java.io.Serializable;

public abstract class Piece implements Serializable {

	protected String team;
	protected int rank, x, y;
	protected boolean isRevealed, isMovable, isBomb, isFlag;
	
	//Attack method is defined by each individual Piece
	public abstract int attack(Piece enemyPiece);
	
	//pieceType method is defined by each individual Piece
	public abstract String pieceType();
	
	/**
	 * Default constructor gives a Piece a rank of 0 and no team
	 */
	public Piece() {
		team = "NONE";
		rank = 0;
		isMovable = false;
	}
	
	/**
	 * Constructor sets all attributes of a Piece
	 * @param team - the color of the team that the Piece belongs to
	 * @param rank - the rank value of the Piece
	 * @param x - the row of the Piece
	 * @param y - the column of the Piece
	 * @param isRevealed - if the Piece is visible to the player
	 * @param isMovable - if the Piece is able to move
	 * @param isBomb - if the Piece is a Bomb
	 * @param isFlag - if the Piece is a Flag
	 */
	public Piece(String team, int rank, int x, int y, boolean isRevealed, boolean isMovable, boolean isBomb, boolean isFlag) {
		this.team = team;
		this.rank = rank;
		this.setX(x);
		this.setY(y);
		this.setRevealed(isRevealed);
		this.setMovable(isMovable);
		this.setBomb(isBomb);
		this.setFlag(isFlag);
	}
	
	/**
	 * Get the piece's color that represents its team
	 * The piece's team controls the color it will display in the GUI
	 * @return the color of the piece's team
	 */
	public String getTeam() {
		return team;
	}
	
	/**
	 * Get the piece's rank
	 * The piece's rank is used in attacks to choose a winner based on the rank
	 * @return the numerical rank of the piece
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Set the piece's rank
	 * @param rank - the numerical rank belonging to the piece
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * Get the revealed status of a piece
	 * The revealed status will determine if a piece's rank will be displayed in the GUI
	 * @return true or false
	 */
	public boolean isRevealed() {
		return isRevealed;
	}

	/**
	 * Set the revealed status of a piece
	 * A piece's revealed status can change after an attack
	 * @param isRevealed - true or false depending on the piece's new status
	 */
	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}

	/**
	 * Get the movable status of a piece
	 * @return isMovable - true if the Piece is a Bomb or Flag
	 */
	public boolean isMovable() {
		return isMovable;
	}

	/**
	 * Set the movable status of a piece
	 * A Flag and Bomb cannot move
	 * @param isMovable - true or false depending on the Piece
	 */
	public void setMovable(boolean isMovable) {
		this.isMovable = isMovable;
	}

	/**
	 * Check if a Piece is a Bomb
	 * @return true - if the Piece is a Bomb
	 */
	public boolean isBomb() {
		return isBomb;
	}

	/**
	 * Sets a Piece as a Bomb
	 * @param isBomb - true or false
	 */
	public void setBomb(boolean isBomb) {
		this.isBomb = isBomb;
	}

	/**
	 * Check if a Piece is a Flag
	 * @return true - if the Piece is a Flag
	 */
	public boolean isFlag() {
		return isFlag;
	}

	/**
	 * Sets a Piece as a Flag
	 * @param isFlag - true or false
	 */
	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	/**
	 * Get the row of the Piece in a Board
	 * @return - x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the row of a Piece in a Board
	 * @param x - the row
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get the column of a Piece in a Board
	 * @return - y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the row of a Piece in a Board
	 * @param y - the column
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
