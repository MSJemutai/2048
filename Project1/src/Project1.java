/*
 * Name: Carolina Lion He
 * Lab partner: Mercy Salome Jemutai
 * NetID: clionhe
 * clionhe@u.rochester.edu
 * Assignment: HW 4
 * Lab: Tuesday/Thursday: 6:15-7:30 PM 
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel; 

public class Project1 extends JPanel implements KeyListener {

	int validMoves = 0; 

	boolean restartPressed = false; 
	boolean quitPressed = false; 

	//serves as counter for the score 
	int max = 0; 
	boolean gameOver = false;

	//constructor 
	public Project1() {
		addKeyListener(this); 
		setFocusable(true); 
	}

	int [][] array;
	JFrame frame;

	//main method 
	public static void main(String [] args) {

		Project1 project1 = new Project1();

		project1.array = new int [4][4]; 

		//loops through each cell and sets the value to 0.
		for(int row = 0; row <= project1.array.length-1; row++) {
			for(int column = 0; column <= project1.array[row].length-1; column++) {
				project1.array[row][column] = 0; 
			}
		}
		project1.updateBoard(); 

		//Frame 
		project1.frame = new JFrame();
		project1.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		project1.frame.setFocusable(true); //recognizes the keyboard keys
		project1.frame.addKeyListener(project1);
		project1.frame.setVisible(true); 

	}

	//checkIf0() boolean method  
	//check if 0 is available
	public boolean checkIf0() {
		boolean zeroLeft = false;
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column <4 ;column ++) {
				if(this.array[row][column] == 0) {
					zeroLeft = true;
				}
			}
		}
		return zeroLeft;
	}

	//updateBoard() method 
	public void updateBoard() {

		checkMax(); 

		int rowPosition = randomNumber();
		int columnPosition = randomNumber();

		//checks whether the array could be moved - if there is empty space (0)
		if(checkIf0()) {
			while(array[rowPosition][columnPosition] != 0) {
				rowPosition = randomNumber(); 
				columnPosition = randomNumber();
			}

			//generates 2s and 4s as the board updates 
			double twoAndFours = Math.random(); //random number from 0 to 1. 
			if(twoAndFours < 0.2) { //setting 2s and 4s as the random numbers after the board is updated. 
				//probability of a 2 appearing: 0.8 || probability of a 4 appearing: 0.2 
				this.array[rowPosition][columnPosition] = 4; 
			} else {
				this.array[rowPosition][columnPosition] = 2; 
			}
		}
		printArray();
		//winning the game - user must accumulate 2048 points in total 
		if(max == 2048) {
			System.out.println("You won!");
		}
		if(gameOver()) {
			System.out.println("Game Over!");
			System.out.println("Press R to Restart");
		}
	}

	//checkIfSame() boolean method 
	//	Going through each cell and checking whether the elements are the same 
	//	 - if same, return true, if not the same, return false.
	public boolean checkIfSame(int [][] array1, int [][] array2) {
		boolean toReturn = true; 
		for(int row = 0; row < 4; row++) {
			for(int column = 0; column < 4; column++) {
				if(array1[row][column] == array2[row][column]) {
					continue; 
				} else {
					toReturn = false; 
					break; 
				}
			}
		}
		return toReturn; 
	}

	//checkMax() method 
	public void checkMax() {
		for(int row = 0; row < array.length; row++) {
			for(int column = 0; column < array.length; column++) {
				if(array[row][column] > max) {
					max = array[row][column]; 
				}
			}
		}
	}

	/*
	 * Left 
	 */
	//removeZerosFromMovingLeft() method 
	public boolean removeZerosFromMovingLeft() {

		boolean leftFlag = false; 

		for(int i = 0; i < 3; i++) {
			for(int row = 0; row <= this.array.length-1; row++) {
				for(int column = this.array.length-1; column >= 0; column--) {
					if(column > 0) {
						if(array[row][column - 1] == 0) {
							array[row][column - 1] = array[row][column]; 
							array[row][column] = 0; 
							leftFlag = true; 
						}
					}
				}
			}
		}
		return leftFlag;  
	}

	//leftMovement() method 
	public void leftMovement(boolean countMove) {

		boolean leftFlagTwo = this.removeZerosFromMovingLeft(); 

		for(int row = 0; row <= this.array.length-1; row++) {
			for(int column = this.array.length-1; column >= 0; column--) {

				if(column > 0) {
					if(array[row][column-1] == array[row][column]) {
						array[row][column] += array[row][column]; 
						leftFlagTwo = true; 
						array[row][column - 1] = 0; 
					}
				}
			}
		}
		leftFlagTwo = this.removeZerosFromMovingLeft() || leftFlagTwo; //returns the true/false value from the removeZerosFromMovingLeft method 
		// if true - updates board w/ validMoves; if false - checks if it was already true 
		// with the second condition. 
		if(leftFlagTwo && countMove) {
			validMoves++; //updates the number of moves 
		}
	}

	/*
	 * Right
	 */
	//removeZerosFromMovingRight() method 
	public boolean removeZerosFromMovingRight() {

		boolean rightFlag = false; 

		for(int i = 0; i < 3; i++) {
			for(int row = 0; row <= this.array.length-1; row++) {
				for(int column = 0; column <= this.array[row].length-1; column++) {

					if(column < 3) {
						if(array[row][column+1] == 0) {
							array[row][column+1] = array[row][column];
							array[row][column] = 0; 
							rightFlag = true; 
						}
					}

				}
			}
		}
		return rightFlag; 
	}

	//rightMovement() method
	public void rightMovement(boolean countMove) {

		boolean rightFlagTwo = this.removeZerosFromMovingRight(); 

		for(int row = 0; row <= this.array.length-1; row++) {
			for(int column = 0; column <= this.array.length-1; column++) {

				if(column < 3) {
					if(array[row][column] == array[row][column+1]) {
						array[row][column] = array[row][column] * 2; 
						rightFlagTwo = true; 
						array[row][column + 1] = 0; //set the current cell to 0
					}
				}
			}
		}
		rightFlagTwo = this.removeZerosFromMovingRight() || rightFlagTwo; 
		if(rightFlagTwo && countMove) {
			validMoves++; 
		}
	}

	/*
	 * Up 
	 */
	//removeZerosFromUp() method 
	public boolean removeZerosFromMovingUp() {

		boolean upFlag = false; 

		for(int i = 0; i < 3; i++) {
			for(int column = 0; column <= this.array.length-1; column++) {
				for(int row = this.array.length-1; row > 0; row --) {

					if(row > 0) {
						if(array[row-1][column] == 0) {
							array[row-1][column] = array[row][column]; 
							array[row][column] = 0; 
							upFlag = true; 
						}
					}
				}
			}
		}
		return upFlag; 
	}

	//upMovement() method 
	public void upMovement(boolean countMove) {

		boolean upFlagTwo = this.removeZerosFromMovingUp(); 

		for(int column = 0; column <= this.array.length-1; column++) {
			for(int row = 0; row <= this.array.length-1; row++) {

				if(row < 3) {

					if(array[row][column] == array[row+1][column]) {
						array[row][column] = array[row+1][column]*2; 
						upFlagTwo = true; 
						array[row+1][column]= 0; 

					}
				}
			}
		}
		upFlagTwo = this.removeZerosFromMovingUp() || upFlagTwo; 
		if(upFlagTwo && countMove) {
			validMoves++; 
		}
	}

	/*
	 * Down 
	 */
	//removeZerosFromDown() method 
	public boolean removeZerosFromDown() {

		boolean downFlag = false; 

		for(int i = 0; i < 3; i++) {
			for(int column = 0; column <= this.array.length-1; column++) {
				for(int row = 0; row <= this.array.length-1; row++) {

					if(row < 3) {
						if(array[row+1][column] == 0) {
							array[row+1][column] = array[row][column]; 
							array[row][column] = 0; 		
							downFlag = true; 
						}

					}
				}
			}
		}
		return downFlag; 
	}

	//downMovement() method
	public void downMovement(boolean countMove) {

		boolean downFlagTwo = this.removeZerosFromDown(); 

		for(int column = 0; column <= this.array.length-1; column++) {
			for(int row = this.array.length-1; row > 0; row--) {

				if(row > 0) {
					if(array[row][column] == array[row-1][column]) {
						array[row][column] = array[row-1][column]*2; 
						downFlagTwo = true; 
						array[row-1][column] = 0; 
					}
				}
			}
		}
		downFlagTwo = this.removeZerosFromDown() || downFlagTwo; 
		if(downFlagTwo && countMove) {
			validMoves++; 
		}

	}

	//randomNumber() method - generates random location for the 2's and 4's to be printed
	public int randomNumber() {
		Random random = new Random(); 
		int number = random.nextInt(4); // 0 <= number < 4 --> {0, 1, 2, 3} 
		return number; 
	}

	//printArray() method - prints out 2D array 
	public void printArray() {
		for(int i = 0; i < 100 ;i++) { //for display purposes 
			System.out.println();
		}
		for(int row = 0; row <= this.array.length-1; row++) {
			for(int column = 0; column <= this.array[row].length-1; column++) {
				if(this.array[row][column] == 0) {
					System.out.print("*\t");
				} else {
					System.out.print(this.array[row][column] + "\t");
				}
			}
			System.out.println();
		}
	}

	/*
	 * KeyListeners 
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();

		if((keyCode == KeyEvent.VK_RIGHT || keyCode == 'D') && !this.gameOver) {
			rightMovement(true); 
			updateBoard(); 
			this.restartPressed = false;
			this.quitPressed = false;
			System.out.println("Right");
			System.out.println("Valid moves: " + validMoves);
			System.out.println("Maximum score: " + max); 
		} else if((keyCode == KeyEvent.VK_LEFT || keyCode == 'A') && !this.gameOver) {
			leftMovement(true); 
			updateBoard(); 
			this.restartPressed = false;
			this.quitPressed = false;
			System.out.println("Left");
			System.out.println("Valid moves: " + validMoves);
			System.out.println("Maximum score: " + max); 
		} else if((keyCode == KeyEvent.VK_UP || keyCode == 'W') && !this.gameOver) {
			upMovement(true);
			updateBoard(); 
			this.restartPressed = false;
			this.quitPressed = false;
			System.out.println("Up");
			System.out.println("Valid moves: " + validMoves);
			System.out.println("Maximum score: " + max); 
		} else if((keyCode == KeyEvent.VK_DOWN || keyCode == 'S') && !this.gameOver) {
			downMovement(true); 
			updateBoard(); 
			this.restartPressed = false;
			this.quitPressed = false;
			System.out.println("Down");
			System.out.println("Valid moves: " + validMoves);
			System.out.println("Maximum score: " + max); 
		} else if(keyCode == KeyEvent.VK_Q){
			this.restartPressed = false;
			if(quitPressed) {
				System.out.println("QUIT");
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			} else {
				System.out.println("Confirming: Press Q again to quit game!");
				quitPressed = true; 
			}
		} else if(keyCode == KeyEvent.VK_R) {
			this.quitPressed = false;
			if(restartPressed) {
				System.out.println("RESTART");
				restart();
			} else {
				System.out.println("Confirming: Press R again to restart game!");
				restartPressed = true;
			}
		} else {
			if(!this.gameOver) {
				System.out.println("Not valid");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	//gameOver() boolean method 
	public boolean gameOver() {

		int [][] copyOfArray = new int[4][4]; 

		for(int row = 0; row < 4; row++) {
			for(int column = 0; column < 4; column++) {
				copyOfArray[row][column] = array[row][column]; 
			}
		}

		rightMovement(false); 

		if(checkIfSame(copyOfArray, this.array)) {
			leftMovement(false); 

			if(checkIfSame(copyOfArray, this.array)) {
				upMovement(false);

				if(checkIfSame(copyOfArray, this.array)) {
					downMovement(false); 

					if(checkIfSame(copyOfArray, this.array)) {
						this.gameOver = true;
					}
				}
			}

		}
		this.array = copyOfArray;
		return this.gameOver;
	}


	//restart() method 
	public void restart() {

		for(int row = 0; row < this.array.length; row++) {
			for(int column = 0; column < this.array.length; column++) {
				array[row][column] = 0; 
			}
		}
		max = 0; 
		this.gameOver = false; 
		this.restartPressed = false;
		this.quitPressed = false;
		validMoves = 0; 
		updateBoard(); 
	}
}
