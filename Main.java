package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image; 
import javafx.scene.image.ImageView; 

public class Main extends Application {
	
	//Labels, text fields, and button control for moving a Piece
	Label resultLabel = new Label();
	Label currentPromptLabel = new Label();
	Label directionsLabel = new Label();
	TextField currentRowText = new TextField();
	TextField currentColText = new TextField();
	TextField directionText = new TextField();
	Button moveButton = new Button();
	
	//Image for game banner
	ImageView strategoBanner = new ImageView(new Image("file:Images/strategoBanner.png", 550, 200, false, false));
	
	//Images for land and water
	Image land = new Image("file:Images/land.png", 90, 90, false, false);
	Image water = new Image("file:Images/water.png", 90, 90, false, false);
	
	//Images for blue pieces
	Image blueFlag = new Image("file:Images/blueFlag.png", 90, 90, false, false);
	Image blueBomb = new Image("file:Images/blueBomb.png", 90, 90, false, false);
	Image blueSpy = new Image("file:Images/blueSpy.png", 90, 90, false, false);
	Image blueMiner = new Image("file:Images/blueMiner.png", 90, 90, false, false);
	Image blueMajor = new Image("file:Images/blueMajor.png", 90, 90, false, false);
	Image blueColonel = new Image("file:Images/blueColonel.png", 90, 90, false, false);
	Image blueGeneral = new Image("file:Images/blueGeneral.png", 90, 90, false, false);
	Image blueMarshall = new Image("file:Images/blueMarshall.png", 90, 90, false, false);
	
	//Images for red pieces
	Image redBack = new Image("file:Images/redBack.png", 90, 90, false, false);
	Image redFlag = new Image("file:Images/redFlag.png", 90, 90, false, false);
	Image redBomb = new Image("file:Images/redBomb.png", 90, 90, false, false);
	Image redSpy = new Image("file:Images/redSpy.png", 90, 90, false, false);
	Image redMiner = new Image("file:Images/redMiner.png", 90, 90, false, false);
	Image redMajor = new Image("file:Images/redMajor.png", 90, 90, false, false);
	Image redColonel = new Image("file:Images/redColonel.png", 90, 90, false, false);
	Image redGeneral = new Image("file:Images/redGeneral.png", 90, 90, false, false);
	Image redMarshall = new Image("file:Images/redMarshall.png", 90, 90, false, false);
	
	//Board object that holds the 2D Piece array
	Board gameBoard = new Board();
	
	//Human and Computer Players
	HumanPlayer humanPlayer = new HumanPlayer();
	ComputerPlayer computerPlayer = new ComputerPlayer();
	
	//The display of the Board
	GridPane gameBoardPane = new GridPane();
	
	@Override
	public void start(Stage primaryStage) {
		
		//Creating a file menu for allowing the user to open a save file, saving a file, and to exit the game
		MenuBar menuBar = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		
		MenuItem openFile = new MenuItem("Open");
		MenuItem saveFile = new MenuItem("Save");
		MenuItem exitItem = new MenuItem("Exit");
		fileMenu.getItems().add(openFile);
		fileMenu.getItems().add(saveFile);
		fileMenu.getItems().add(exitItem);
		
		//Allows the user to choose a save file and load it into the game
		openFile.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose a file");
			File selectedFile = fileChooser.showOpenDialog(primaryStage);
			
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(selectedFile));
				Object gameSave = objectInputStream.readObject();
				gameBoard = (Board)gameSave;
				objectInputStream.close();
			} catch (Exception e) {
				System.out.println("Something went wrong opening a file!");
				e.printStackTrace();
			}
			
			//Update the display after the new game is loaded in
			updateView(gameBoardPane);
		});
		
		//Create an object file that stores the current state of the game
		saveFile.setOnAction(event -> {
			Object gameSave = gameBoard;
			try {
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("gameSave.dat"));
				objectOutputStream.writeObject(gameSave);
				objectOutputStream.close();
			} catch (IOException e) {
				System.out.println("Something went wrong saving a file!");
				e.printStackTrace();
			}
		});
		
		//Closes the scene when user chooses to exit
		exitItem.setOnAction(event ->
	    {
	       primaryStage.close();
	    });
		
		menuBar.getMenus().add(fileMenu);
		
		//Add the menu bar to a border pane
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(menuBar);
			
		//Grid pane settings
		gameBoardPane.setPadding(new Insets(150, 400, 150, 400));
		gameBoardPane.setHgap(2);
		gameBoardPane.setVgap(2);
			
		//Label that will update with move controls
		resultLabel.setText("Please enter a move!");
		resultLabel.setTranslateX(125);
		resultLabel.setTranslateY(60);
			
		//Prompts the user to enter the coordinates of a piece
		currentPromptLabel.setText("(ROW, COL) ");
			
		//Prompts the user to enter the direction of movement
		directionsLabel.setText("(U, D, L, R) ");
			
		//Button that will gather the coordinates and the direction from both text fields
		moveButton.setText("Move");
		moveButton.setTranslateX(200);
		moveButton.setTranslateY(20);
		
		//Initialize the board and update the display
		gameBoard.setupBoard();
		updateView(gameBoardPane);
		
		for(Piece x : gameBoard.getMovableComputerPieces()) {
			System.out.println(x.pieceType());
		}
		
		//The moveButton activates the movement of a Piece
		//If the moveButton is pressed and a valid move is made, then the computer responds with a random move
		//Every time a move is made, a winner is checked
		moveButton.setOnAction(e -> {
			//Gather coordinates and direction from text fields
			int currentPieceRow = Integer.parseInt(currentRowText.getText());
			int currentPieceCol = Integer.parseInt(currentColText.getText());
			String moveDirection = directionText.getText();
			
			//Use pieceName to display the piece that moved after human player makes a move
			String pieceName = gameBoard.getBoard()[currentPieceRow][currentPieceCol].pieceType();
			
			//Check if user inputs a valid movement
			if (!gameBoard.getBoard()[currentPieceRow][currentPieceCol].isMovable() || 
				gameBoard.isWater(currentPieceRow, currentPieceCol) || !gameBoard.getBoard()[currentPieceRow][currentPieceCol].getTeam().equals("BLUE")) {
					resultLabel.setText("Please enter a valid move!");
			}
			//If the move is valid, respond with a computer move
			else if (humanPlayer.makeMove(gameBoard, gameBoard.getBoard()[currentPieceRow][currentPieceCol], moveDirection)) {
				resultLabel.setText(pieceName + "has moved!");
					
				//Get a random piece for the computer to move
				Piece computerPiece = gameBoard.getMovableComputerPiece();
				if (computerPlayer.makeMove(gameBoard, computerPiece, gameBoard.getRandomMove(computerPiece))) {
					//resultLabel.setText("Computer has moved!");
				}
			}
				
			//Check for a winner after every set of moves
			if(humanPlayer.checkWinner(gameBoard)) {
				resultLabel.setText("Human has won!");
				moveButton.setDisable(true);
			}
			else if(computerPlayer.checkWinner(gameBoard)) {
				resultLabel.setText("Computer has won!");
				moveButton.setDisable(true);
			}
				
			updateView(gameBoardPane);
		});
			
		//Add all move controls to containers
		HBox currentMoveControls = new HBox(10, currentPromptLabel, currentRowText, currentColText);
		HBox directionMoveControls = new HBox(10, directionsLabel, directionText);
			
		//Add all controls and title image to a container
		VBox controls = new VBox(10, strategoBanner, currentMoveControls, directionMoveControls, moveButton, resultLabel);
		controls.setPadding(new Insets(125, 1150, 125, 1150));
			
		HBox container = new HBox(borderPane, controls);
			
		Group root = new Group(gameBoardPane, container);
		
		Scene scene = new Scene(root, 1800, 1000, Color.BURLYWOOD);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quick Arena Stratego");
		primaryStage.show();
			
	}

	/*
	 * Updates the view of the GridPane to the corresponding pieces at each location on the game board
	 * Loop checks for locations of water and if a piece is at every row and column
	 * If a piece is present at a location, then the piece's team, rank, and revealed status are checked to display the proper piece
	 * If no piece is present at a location, then "land" is placed resembling an open position on the game board
	 * @param gameBoardPane - the GridPane that is being used to display the game board
	 */
	public void updateView(GridPane gameBoardPane) {
		for (int row = 0; row < gameBoard.getBoard().length; row ++) {
			for (int col = 0; col < gameBoard.getBoard()[0].length; col++) {
				if (gameBoard.isWater(row, col)) {
					Rectangle border = new Rectangle(91, 91);
					border.setStrokeWidth(1);
					border.setStroke(Color.BLACK);
					gameBoardPane.add(new StackPane(border, new ImageView(water)), col, row);
				}
				else if(gameBoard.isEmpty(row, col)) {
					Rectangle border = new Rectangle(91, 91);
					border.setStrokeWidth(1);
					border.setStroke(Color.BLACK);
					gameBoardPane.add(new StackPane(border, new ImageView(land)), col, row);
				}
				else if(gameBoard.getBoard()[row][col].getTeam().equals("RED")) {
					if (gameBoard.getBoard()[row][col].isRevealed()) {
						Rectangle redPiece = new Rectangle(91, 91);
						redPiece.setStrokeWidth(1);
						redPiece.setStroke(Color.BLACK);
						redPiece.setFill(Color.RED);
						switch(gameBoard.getBoard()[row][col].getRank()) {
						case -1:
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redFlag)), col, row); 
							break;
						case 1:
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redSpy)), col, row); 
							break;
						case 3: 
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redMiner)), col, row); 
							break;
						case 7:
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redMajor)), col, row); 
							break;
						case 8: 
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redColonel)), col, row); 
							break;
						case 9:
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redGeneral)), col, row); 
							break;
						case 10: 
							gameBoardPane.add(new StackPane(redPiece, new ImageView(redMarshall)), col, row); 
							break;
						case 11:
							gameBoardPane.add(new StackPane(redPiece,  new ImageView(redBomb)), col, row); 
							break;
						}
					}
					else {
						Rectangle redPiece = new Rectangle(91, 91);
						redPiece.setStrokeWidth(1);
						redPiece.setStroke(Color.BLACK);
						redPiece.setFill(Color.RED);
						gameBoardPane.add(new StackPane(redPiece, new ImageView(redBack)), col, row);
					}
				}
				else if (gameBoard.getBoard()[row][col].getTeam().equals("BLUE")) {
					if (gameBoard.getBoard()[row][col].isRevealed()) {
						Rectangle bluePiece = new Rectangle(91, 91);
						bluePiece.setStrokeWidth(1);
						bluePiece.setStroke(Color.BLACK);
						bluePiece.setFill(Color.BLUE);
						switch(gameBoard.getBoard()[row][col].getRank()) {
						case -1:
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueFlag)), col, row); 
							break;
						case 1:
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueSpy)), col, row); 
							break;
						case 3: 
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueMiner)), col, row); 
							break;
						case 7:
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueMajor)), col, row); 
							break;
						case 8: 
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueColonel)), col, row); 
							break;
						case 9:
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueGeneral)), col, row); 
							break;
						case 10: 
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueMarshall)), col, row); 
							break;
						case 11:
							gameBoardPane.add(new StackPane(bluePiece, new ImageView(blueBomb)), col, row); 
							break;
						}
					}
				}
			}
		}			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
