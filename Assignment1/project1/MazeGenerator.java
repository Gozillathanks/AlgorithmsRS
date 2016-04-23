/**
 * 3. Union-Find - Maze
 * Created by ZiminGuo on 3/4/16.
 * Generate maze of arbitrary size using the union-find algorithm
 * Creat N*M cell separated by walls except entrance and exit
 * Randomly choose wall to knock down if not connected
 * Repeat until connect starting and ending cells
 * Better continue knocking down until every cell is reachable from every cell
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {

	private int length;
	private int width;
	private int[] cells;
	private int[] size;
	private int[] walls;
	private ArrayList<Integer> knockedDownWalls;
	
	public MazeGenerator() {
		this.length = 0;
		this.width = 0;
		this.cells = null;
		this.size = null;
		this.walls = null;
		this.knockedDownWalls = null;
	}
	
	public void setDimensions(int length, int width) {
		if(length > 0 && width > 0) {
			this.length = length;
			this.width = width;
			this.cells = new int[length * width];
			this.size = new int[length * width];
			this.walls = new int[(length * width * 4 - 2 * (length + width)) / 2]; 
			// (A * 4 - P) / 2
			// 4 walls per cell; outer walls (perimeter) not included; each wall shared by 2 cells
			this.knockedDownWalls = new ArrayList<Integer>(length * width - 1);
			// resulting graph will be a tree (1 path from every cell), 
			// so, at most (number of cells - 1) edges (knocked down walls)
		}
	}
	
	private void restart() {
		for (int i = 0; i < this.cells.length; i++) {
			this.cells[i] = i;
			this.size[i] = 1;
		}
		
		for (int i = 0; i < this.walls.length; i++) {
			this.walls[i] = i;
		}
		
		this.knockedDownWalls.clear();
	}
	
	private int root(int a) {
		while (a != this.cells[a]) {
			a = this.cells[a];
		}
		
		return a;
	}
	
	public void generateMaze() {
		Random random = new Random();
		int a;
		int wallNumber, xPosition, yPosition, cell1, cell2;
		int cell1ID, cell2ID;
		restart();
		
		for (int i = this.walls.length; i > 0; i--) {
			a = random.nextInt(i); // from 0 (inclusively) to i (exclusively)
			wallNumber = this.walls[a];
			xPosition = wallNumber % (2 * this.width - 1);
			yPosition = wallNumber / (2 * this.width - 1);
			
			if (xPosition < width - 1) { // wall is to the right of cell 1
				cell1 = yPosition * this.width + xPosition;
				cell2 = cell1 + 1;
			}
			
			else { // wall is at the bottom of cell 1
				cell1 = yPosition * this.width + xPosition - (this.width - 1);
				cell2 = cell1 + this.width;
			}
			
			if (root(cell1) != root(cell2)) { // cell 1 and 2 are not connected
				this.knockedDownWalls.add(Integer.valueOf(this.walls[a]));
				cell1ID = root(cell1);
				cell2ID = root(cell2);
				
				if (this.size[cell1ID] < this.size[cell2ID]) {
					this.cells[cell1ID] = cell2ID;
					this.size[cell2ID] += this.size[cell1ID];
				}
				
				else {
					this.cells[cell2ID] = cell1ID;
					this.size[cell1ID] += this.size[cell2ID];
				}
			}
			
			this.walls[a] = this.walls[i - 1]; 
			// moves wall at end of array closer so randomizer will keep running
		}
		
		System.out.println("List of knocked down walls:");
		
		for (int i = 0; i < this.knockedDownWalls.size(); i++) {
			wallNumber = this.knockedDownWalls.get(i);
			xPosition = wallNumber % (2 * this.width - 1);
			yPosition = wallNumber / (2 * this.width - 1);
			
			if (xPosition < width - 1) { // wall is to the right of cell 1
				cell1 = yPosition * this.width + xPosition;
				cell2 = cell1 + 1;
			}
			
			else { // wall is at the bottom of cell 1
				cell1 = yPosition * this.width + xPosition - (this.width - 1);
				cell2 = cell1 + this.width;
			}
			
			System.out.println("(" + cell1 + ", " + cell2 + ")");
		}
	}
	
	public void drawMaze() {
		/*	each cell is 2 x 2
		 *  ___________
		 * |     |     |
		 * |   __|   __|
		 * |  |     |  |
		 * |  |   __|  |
		 * |           |
		 * |__ __ __ __|
		 * 
		 * 	sample 3 x 4 maze
		 */
		
		for (int i = 0; i < this.walls.length; i++) {
			this.walls[i] = 1;
		}
		
		for (int i = 0; i < this.knockedDownWalls.size(); i++) {
			this.walls[this.knockedDownWalls.get(i)] = 0;
		}

		// drawing the ceiling
		System.out.print(" ");
		for (int i = 0; i < this.width * 3 - 1; i ++) {
			System.out.print("_");
		}
		System.out.println();
		
		for (int i = 0; i < this.length * 2; i++) {
			System.out.print("|"); // leftmost walls
			
			for (int j = 0; j < this.width; j++) {
				
				if (i != this.length * 2 - 1 && 
					(i % 2 == 0 || 
					this.walls[i / 2 * (2 * this.width - 1) + j + this.width - 1] == 0)) {
					// not the bottom, in the middle, or knocked down bottom wall
					
					if (j != this.width - 1 && 
						this.walls[i / 2 * (2 * this.width - 1) + j] == 0) { 
						// not the rightmost wall and knocked down right wall
						System.out.print("   ");
					}
					
					else { // right wall not knocked down
						System.out.print("  |");
					}
				}
				
				else { // either bottom-most or bottom wall not knocked down
					
					if (j != this.width - 1 && 
						this.walls[i / 2 * (2 * this.width - 1) + j] == 0) { 
							// not the rightmost wall and knocked down right wall
							System.out.print("__ ");
					}
						
					else { // right wall not knocked down
							System.out.print("__|");
					}
				}
			}
			
			System.out.println();
		}
		
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int length, width;
		String answer;
		MazeGenerator maze = new MazeGenerator();
		boolean restart = true;

		while (restart) {
			System.out.print("Maze Length: ");
			length = Integer.parseInt(sc.nextLine());
			System.out.print("Maze Width: ");
			width = Integer.parseInt(sc.nextLine());
			maze.setDimensions(length, width);
			maze.generateMaze();
			maze.drawMaze();

			System.out.print("Generate again? (Y/N) ");
			answer = sc.nextLine();
			System.out.println();

			if (answer.equalsIgnoreCase("n")) {
				restart = false;
			}
		}

	}
}

/* Program output
Maze Length: 10
Maze Width: 10
List of knocked down walls:
(17, 18)
(40, 41)
(75, 76)
(81, 82)
(21, 22)
(23, 33)
(70, 80)
(67, 68)
(34, 44)
(61, 71)
(42, 43)
(53, 63)
(51, 52)
(63, 64)
(15, 25)
(26, 27)
(24, 25)
(73, 83)
(54, 55)
(22, 32)
(47, 48)
(34, 35)
(15, 16)
(35, 45)
(78, 79)
(66, 76)
(2, 12)
(6, 7)
(55, 56)
(32, 33)
(94, 95)
(96, 97)
(12, 22)
(25, 35)
(33, 43)
(5, 6)
(56, 57)
(27, 37)
(52, 53)
(77, 78)
(43, 44)
(37, 47)
(91, 92)
(7, 17)
(51, 61)
(88, 89)
(86, 96)
(11, 12)
(58, 68)
(78, 88)
(20, 30)
(80, 81)
(76, 77)
(62, 63)
(3, 13)
(49, 59)
(87, 88)
(38, 48)
(64, 65)
(14, 24)
(50, 51)
(31, 32)
(48, 58)
(70, 71)
(68, 78)
(57, 58)
(16, 17)
(68, 69)
(90, 91)
(75, 85)
(50, 60)
(30, 31)
(40, 50)
(97, 98)
(27, 28)
(89, 99)
(29, 39)
(43, 53)
(74, 75)
(25, 26)
(82, 92)
(8, 18)
(84, 94)
(48, 49)
(46, 47)
(3, 4)
(73, 74)
(36, 46)
(10, 11)
(95, 96)
(18, 19)
(62, 72)
(13, 14)
(9, 19)
(83, 93)
(0, 1)
(76, 86)
(28, 29)
(1, 2)
 _____________________________
|        |     |        |  |  |
|__ __   |   __|__ __   |  |  |
|        |     |              |
|__ __   |__   |   __ __ __ __|
|  |     |  |                 |
|  |__   |  |__    __    __   |
|           |     |  |  |  |  |
|__ __ __   |     |  |  |  |__|
|     |        |  |           |
|   __|__    __|__|__ __      |
|           |              |  |
|      __   |__ __ __ __   |__|
|  |  |           |  |        |
|__|  |   __ __ __|  |__    __|
|     |  |                    |
|   __|__|   __       __    __|
|        |  |  |  |  |        |
|__ __   |  |  |__|  |__ __   |
|        |  |              |  |
|__ __ __|__|__ __ __ __ __|__|
Generate again? (Y/N) y

Maze Length: 10
Maze Width: 10
List of knocked down walls:
(16, 17)
(17, 18)
(37, 38)
(11, 12)
(87, 97)
(40, 50)
(54, 64)
(1, 2)
(16, 26)
(23, 24)
(63, 73)
(21, 22)
(18, 19)
(71, 81)
(73, 83)
(34, 35)
(33, 34)
(36, 46)
(43, 53)
(32, 33)
(18, 28)
(4, 14)
(78, 79)
(13, 23)
(89, 99)
(47, 48)
(36, 37)
(41, 51)
(47, 57)
(81, 91)
(62, 72)
(66, 67)
(3, 4)
(8, 9)
(20, 30)
(28, 38)
(21, 31)
(46, 56)
(6, 16)
(31, 32)
(35, 36)
(61, 62)
(73, 74)
(3, 13)
(53, 63)
(84, 94)
(41, 42)
(65, 66)
(27, 28)
(10, 20)
(12, 22)
(6, 7)
(67, 68)
(91, 92)
(48, 49)
(80, 90)
(30, 31)
(56, 66)
(75, 85)
(12, 13)
(48, 58)
(56, 57)
(15, 25)
(74, 75)
(35, 45)
(4, 5)
(24, 25)
(58, 59)
(72, 73)
(32, 42)
(52, 53)
(2, 12)
(8, 18)
(34, 44)
(86, 96)
(82, 83)
(45, 55)
(60, 61)
(83, 84)
(67, 77)
(50, 60)
(42, 43)
(68, 69)
(77, 78)
(92, 93)
(38, 39)
(53, 54)
(86, 87)
(29, 39)
(82, 92)
(70, 80)
(70, 71)
(77, 87)
(75, 76)
(78, 88)
(95, 96)
(97, 98)
(0, 1)
(98, 99)
 _____________________________
|        |        |     |     |
|__ __   |      __|   __|   __|
|  |        |  |  |           |
|  |__      |__|  |   __    __|
|  |     |        |  |     |  |
|  |   __|__ __ __|__|__   |  |
|                             |
|__ __    __          __ __ __|
|  |        |  |  |  |        |
|  |   __   |__|  |  |      __|
|  |  |        |  |     |     |
|  |__|__      |__|   __|__ __|
|        |  |  |              |
|__ __   |  |__|__ __    __ __|
|     |              |        |
|     |__    __    __|      __|
|  |  |        |  |     |  |  |
|  |  |   __   |__|     |__|  |
|  |        |  |     |        |
|__|__ __ __|__|__ __|__ __ __|
Generate again? (Y/N) n
 */