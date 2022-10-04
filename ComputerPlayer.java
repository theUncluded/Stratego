package application;

public class ComputerPlayer implements IPlayer {

	public ComputerPlayer() {
	}
	
	@Override
	public boolean makeMove(Board gameBoard, Piece pieceToMove, String direction) {
		if (gameBoard.moveComputerPiece(pieceToMove, gameBoard.getRandomMove(pieceToMove))) {
			return true;
		}
		return false;
	}

	/**
	 * The checkWinner method loops through the Pieces on the board that belong to the HumanPlayer
	 * If the HumanPlayer still has a Flag, then the computer hasn't won yet
	 * If the HumanPlayer is missing a Flag (it has been attacked), then the computer wins
	 */
	public boolean checkWinner(Board gameBoard) {
		for(int row = 0; row < gameBoard.getBoard().length; row++) {
			for(int col = 0; col < gameBoard.getBoard()[0].length; col++) {
				if (gameBoard.getBoard()[row][col].getTeam().equals("BLUE") && gameBoard.getBoard()[row][col].isFlag()) {
					return false;
				}
			}
		}
		return true;
	}

}
