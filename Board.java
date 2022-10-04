package application;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.Serializable;

public class Board implements Serializable {
	
	private final int BOARD_SIZE = 8;
	private Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
	
	/**
	 * Default constructor sets every Piece in the array to an EmptyPiece
	 */
	public Board() {
		for (int row = 0; row < board.length; row ++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col] = new EmptyPiece();
			}
		}
	}
	
	/**
	 * The board is setup using a text file
	 * A Piece is created with the proper attributes from the text file
	 */
	public void setupBoard() {
		Scanner input = new Scanner(System.in);
		
		//Open setup text file
		try {
			input = new Scanner(new File("Setup.txt"));
		} catch (Exception e) {
			System.out.println("Error opening file!");
			e.printStackTrace();		
		}
		
		//Read until the end of the file
		while (input.hasNext()) {
			String pieceTeam = input.next().toUpperCase();
			int pieceRank = input.nextInt();
			int pieceRow = input.nextInt();
			int pieceCol = input.nextInt();
			
			//Create a new Piece on the game board, if the piece belongs to the enemy ("RED"), then the piece isn't revealed
			//The Piece's rank is taken into account to create the correct Piece
			if (pieceTeam.equals("RED")) {
				switch(pieceRank) {
				case -1: 
					board[pieceRow][pieceCol] = new Flag(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 1:
					board[pieceRow][pieceCol] = new Spy(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 3:
					board[pieceRow][pieceCol] = new Miner(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 7:
					board[pieceRow][pieceCol] = new Major(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 8: 
					board[pieceRow][pieceCol] = new Colonel(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 9:
					board[pieceRow][pieceCol] = new General(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 10:
					board[pieceRow][pieceCol] = new Marshall(pieceTeam, pieceRow, pieceCol, false);
					break;
				case 11:
					board[pieceRow][pieceCol] = new Bomb(pieceTeam, pieceRow, pieceCol, false);
					break;
				}
			}
			else if (pieceTeam.equals("BLUE")) {
				switch(pieceRank) {
				case -1: 
					board[pieceRow][pieceCol] = new Flag(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 1:
					board[pieceRow][pieceCol] = new Spy(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 3:
					board[pieceRow][pieceCol] = new Miner(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 7:
					board[pieceRow][pieceCol] = new Major(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 8: 
					board[pieceRow][pieceCol] = new Colonel(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 9:
					board[pieceRow][pieceCol] = new General(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 10:
					board[pieceRow][pieceCol] = new Marshall(pieceTeam, pieceRow, pieceCol, true);
					break;
				case 11:
					board[pieceRow][pieceCol] = new Bomb(pieceTeam, pieceRow, pieceCol, true);
					break;
				} 
			}
		}
		input.close();
	}
	
	/**
	 * This method checks if "water" is present at a coordinate in the Piece array
	 * @param row - a row in the board
	 * @param col - a column in the board
	 * @return true if coordinates match to any of the water's four coordinates
	 */
	public boolean isWater(int row, int col) {
		return ((row > 2 && row < 5) && (col == 2 || col == 5));
	}
	
	/**
	 * This method checks if a spot is empty in the Piece array
	 * @param row - a row in the board
	 * @param col - a column in the board
	 * @return true if an EmptyPiece is present at the row and column
	 */
	public boolean isEmpty(int row, int col) {
		return board[row][col].getRank() == 0;
	}
	
	/**
	 * This method creates an EmptyPiece at a location on the board
	 * @param row - a row in the board
	 * @param col - a column in the board
	 */
	public void setEmpty(int row, int col) {
		board[row][col] = new EmptyPiece();
	}
	
	/**
	 * This method takes in a piece and a desired direction to move
	 * Based on the direction, the new location of the piece is created
	 * If the new location is valid, a new piece is created at the new location
	 * If the new location contains an enemy piece, then the piece's attack method is called
	 * The board is updated depending on the result of the attack
	 * @param pieceToMove - Piece that is going to be moved
	 * @param direction - direction the piece will move
	 */
	/*public boolean movePiece(Piece pieceToMove, String direction) {
		direction = direction.toUpperCase();
		int row = pieceToMove.getX();
		int col = pieceToMove.getY();
		int newRow = row, newCol = col;
		
		switch(direction) {
		case "U":
			newRow = row - 1;
			break;
		case "D": 
			newRow = row + 1;
			break;
		case "L":
			newCol = col - 1;
			break;
		case "R":
			newCol = col + 1;
			break;
		}
		
		if(isEmpty(newRow, newCol)) {
			if(!isWater(newRow, newCol)) {
				switch(pieceToMove.getRank()) {
				case 1:
					board[newRow][newCol] = new Spy(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 3:
					board[newRow][newCol] = new Miner(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 7:
					board[newRow][newCol] = new Major(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 8: 
					board[newRow][newCol] = new Colonel(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 9:
					board[newRow][newCol] = new General(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 10:
					board[newRow][newCol] = new Marshall(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				}
				setEmpty(row, col);
				return true;
			}
		}
		else if(board[newRow][newCol].getTeam().equals("RED")) {
			//Get the result of the attack
			int attackResult = board[row][col].attack(board[newRow][newCol]);
			if (attackResult == 1) {
				setEmpty(newRow, newCol);
				switch(pieceToMove.getRank()) {
				case 1:
					board[newRow][newCol] = new Spy(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 3:
					board[newRow][newCol] = new Miner(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 7:
					board[newRow][newCol] = new Major(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 8: 
					board[newRow][newCol] = new Colonel(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 9:
					board[newRow][newCol] = new General(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 10:
					board[newRow][newCol] = new Marshall(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				}
				setEmpty(row, col);
				return true;
			}
			else if(attackResult == 0) {
				setEmpty(row, col);
				setEmpty(newRow, newCol);
				return true;
			}
			else if(attackResult == -1) {
				setEmpty(row, col);
				return true;
			}
		}
		 
		return false;
	}*/
	
	public boolean moveComputerPiece(Piece pieceToMove, String direction) {
		int row = pieceToMove.getX();
		int col = pieceToMove.getY();
		int newRow = row, newCol = col;
		
		switch(direction) {
		case "U":
			newRow = row + 1;
			break;
		case "D": 
			newRow = row - 1;
			break;
		case "L":
			newCol = col + 1;
			break;
		case "R":
			newCol = col - 1;
			break;
		}
		
		if(isEmpty(newRow, newCol)) {
			if(!isWater(newRow, newCol)) {
				switch(pieceToMove.getRank()) {
				case 1:
					board[newRow][newCol] = new Spy(pieceToMove.getTeam(), newRow, newCol, pieceToMove.isRevealed());
					break;
				case 3:
					board[newRow][newCol] = new Miner(pieceToMove.getTeam(), newRow, newCol, pieceToMove.isRevealed());
					break;
				case 7:
					board[newRow][newCol] = new Major(pieceToMove.getTeam(), newRow, newCol, pieceToMove.isRevealed());
					break;
				case 8: 
					board[newRow][newCol] = new Colonel(pieceToMove.getTeam(), newRow, newCol, pieceToMove.isRevealed());
					break;
				case 9:
					board[newRow][newCol] = new General(pieceToMove.getTeam(), newRow, newCol, pieceToMove.isRevealed());
					break;
				case 10:
					board[newRow][newCol] = new Marshall(pieceToMove.getTeam(), newRow, newCol, pieceToMove.isRevealed());
					break;
				}
				setEmpty(row, col);
				return true;
			}
		}
		else if(board[newRow][newCol].getTeam().equals("BLUE")) {
			//Get the result of the attack
			int attackResult = board[row][col].attack(board[newRow][newCol]);
			if (attackResult == 1) {
				setEmpty(newRow, newCol);
				switch(pieceToMove.getRank()) {
				case 1:
					board[newRow][newCol] = new Spy(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 3:
					board[newRow][newCol] = new Miner(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 7:
					board[newRow][newCol] = new Major(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 8: 
					board[newRow][newCol] = new Colonel(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 9:
					board[newRow][newCol] = new General(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 10:
					board[newRow][newCol] = new Marshall(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				}
				setEmpty(row, col);
				return true;
			}
			else if(attackResult == 0) {
				setEmpty(row, col);
				setEmpty(newRow, newCol);
				return true;
			}
			else if(attackResult == -1) {
				setEmpty(row, col);
				return true;
			}
		}
		 
		return false;
	}
	
	public String getRandomMove(Piece pieceToMove) {
		if (!checkAvailableComputerMoves(pieceToMove).isEmpty()) {
			Random rng = new Random();
			int randomMove = rng.nextInt(checkAvailableComputerMoves(pieceToMove).size());
			return checkAvailableComputerMoves(pieceToMove).get(randomMove);
		}
		else {
			return null;
		}
	}
	
	public Piece getMovableComputerPiece() {
		if (!getMovableComputerPieces().isEmpty()) {
			Random rng = new Random();
			int randomPiece = rng.nextInt(getMovableComputerPieces().size());
			return getMovableComputerPieces().get(randomPiece);
		}
		else {
			return null;
		}
	}
	
	public ArrayList<Piece> getMovableComputerPieces() {
		ArrayList<Piece> movableComputerPieces = new ArrayList<Piece>();
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].getTeam().equals("RED") && board[row][col].isMovable() && !checkAvailableComputerMoves(board[row][col]).isEmpty()) {
					movableComputerPieces.add(board[row][col]);
				}
			}
		}
		
		return movableComputerPieces;
	}
	
	public ArrayList<String> checkAvailableComputerMoves(Piece pieceToMove) {
		ArrayList<String> moves = new ArrayList<String>();
		
		int row = pieceToMove.getX();
		int col = pieceToMove.getY();
		
		if (!isWater(row + 1, col) && row + 1 <= BOARD_SIZE - 1) {
			if (!board[row + 1][col].getTeam().equals("RED")) {
				moves.add("U");
			}
		}
		/*if (!isWater(row - 1, col) && row - 1 >= 0) {
			if (!board[row - 1][col].getTeam().equals("RED")) {
				moves.add("D");
			}
		}*/
		if (!isWater(row, col + 1) && col + 1 <= BOARD_SIZE - 1) {
			if (!board[row][col + 1].getTeam().equals("RED")) {
				moves.add("L");
			}
		}
		if (!isWater(row, col - 1) && col - 1 >= 0) {
			if (!board[row][col - 1].getTeam().equals("RED")) {
				moves.add("R");
			}
		}
		
		return moves;
	}
	
	/**
	 * Get the current board
	 * Can be used to access board methods such as movePiece
	 * @return board - the current 2D array of Pieces
	 */
	public Piece[][] getBoard() {
		return board;
	}

	/**
	 * Sets the current board to a different board
	 * Useful for when loading in a saved game
	 * @param board - replacement board
	 */
	public void setBoard(Piece[][] board) {
		this.board = board;
	}

}
