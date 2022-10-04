package application;

public class HumanPlayer implements IPlayer {
	
	public HumanPlayer() {
	}

	/**
	 * The makeMove method for the HumanPlayer calls the appropriate move method from the Board class
	 */
	//public boolean makeMove(Board gameBoard, Piece pieceToMove, String direction) {
	//	if(gameBoard.movePiece(pieceToMove, direction)) {
	//		return true;
	//	}
	//	return false;
	//}
	
	/**
	 * This method takes in a piece and a desired direction to move
	 * Based on the direction, the new location of the piece is created
	 * If the new location is valid, a new piece is created at the new location
	 * If the new location contains an enemy piece, then the piece's attack method is called
	 * The board is updated depending on the result of the attack
	 * @param gameBoard - the Board class holding the 2D Piece array
	 * @param pieceToMove - Piece that is going to be moved
	 * @param direction - direction the piece will move
	 */
	public boolean makeMove(Board gameBoard, Piece pieceToMove, String direction) {
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
		
		if(gameBoard.isEmpty(newRow, newCol)) {
			if(!gameBoard.isWater(newRow, newCol)) {
				switch(pieceToMove.getRank()) {
				case 1:
					gameBoard.getBoard()[newRow][newCol] = new Spy(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 3:
					gameBoard.getBoard()[newRow][newCol] = new Miner(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 7:
					gameBoard.getBoard()[newRow][newCol] = new Major(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 8: 
					gameBoard.getBoard()[newRow][newCol] = new Colonel(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 9:
					gameBoard.getBoard()[newRow][newCol] = new General(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 10:
					gameBoard.getBoard()[newRow][newCol] = new Marshall(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				}
				gameBoard.setEmpty(row, col);
				return true;
			}
		}
		else if(gameBoard.getBoard()[newRow][newCol].getTeam().equals("RED")) {
			//Get the result of the attack
			int attackResult = gameBoard.getBoard()[row][col].attack(gameBoard.getBoard()[newRow][newCol]);
			if (attackResult == 1) {
				gameBoard.setEmpty(newRow, newCol);
				switch(pieceToMove.getRank()) {
				case 1:
					gameBoard.getBoard()[newRow][newCol] = new Spy(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 3:
					gameBoard.getBoard()[newRow][newCol] = new Miner(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 7:
					gameBoard.getBoard()[newRow][newCol] = new Major(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 8: 
					gameBoard.getBoard()[newRow][newCol] = new Colonel(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 9:
					gameBoard.getBoard()[newRow][newCol] = new General(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				case 10:
					gameBoard.getBoard()[newRow][newCol] = new Marshall(pieceToMove.getTeam(), newRow, newCol, true);
					break;
				}
				gameBoard.setEmpty(row, col);
				return true;
			}
			else if(attackResult == 0) {
				gameBoard.setEmpty(row, col);
				gameBoard.setEmpty(newRow, newCol);
				return true;
			}
			else if(attackResult == -1) {
				gameBoard.setEmpty(row, col);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The checkWinner method loops through the Pieces on the board that belong to the ComputerPlayer
	 * If the ComputerPlayer still has a Flag, then the HumanPlayer hasn't won yet
	 * If the ComputerPlayer is missing a Flag (it has been attacked), then the HumanPlayer wins
	 */
	public boolean checkWinner(Board gameBoard) {
		for(int row = 0; row < gameBoard.getBoard().length; row++) {
			for(int col = 0; col < gameBoard.getBoard()[0].length; col++) {
				if (gameBoard.getBoard()[row][col].getTeam().equals("RED") && gameBoard.getBoard()[row][col].isFlag()) {
					return false;
				}
			}
		}
		return true;
	}

}