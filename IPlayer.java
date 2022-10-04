package application;

public interface IPlayer {
	public boolean makeMove(Board gameBoard, Piece pieceToMove, String direction);
	public boolean checkWinner(Board gameBoard);
}
