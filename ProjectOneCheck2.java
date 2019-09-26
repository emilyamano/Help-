import java.util.*;
import java.io.*;

public class ProjectOneCheck2 {

	// variables that will be used in whole scope of the program
	public static Scanner x = new Scanner(System.in);
	public static char[][] map;
	public static char obstacle;
	public static final String PROMPT = "Enter a cell (row and column; zeros to exit):";
	
	public static void main(String[] args) throws FileNotFoundException {
	//get file to be loaded
		System.out.print("Enter the name of the map file to be loaded: ");
		Scanner file = new Scanner(new File(x.nextLine()));
		int[] coordinates = buildMap(file);
		
		while(file.hasNextLine()) {
			String line = file.nextLine();
			int[] placement = obstacleLoc(line);
			//System.out.println(Arrays.toString(placement));
			if (placement[0] > 0)
				replace(placement);
		}
		
		//Random generate a number from the width+height to go through loop
		//to randomly pick coordiantes for a treasure
		Random rand = new Random();
		int num = rand.nextInt(coordinates[0] + coordinates[1]);
		for (int i = 0; i < num+1; i++) {
			int[] placement = randomPlace(coordinates);
			// if not in the same spot put it in the map
			int a = placement[0]+1;
			int b = placement[1]+1;
			
			if (map[a][b] == '.')
				replace(placement);
		}
		printMap();
		
		while (true) {
			int[] guess = getInput();
			checkInput(guess);
			if (guess[0] == 0 && guess[1] == 0)
				break;
		}
	}
	
	//build the map
	public static int[] buildMap(Scanner fileInput) {
		// get height and width from first line of file
		int h = fileInput.nextInt();
		int w = fileInput.nextInt();
		int[] heightWidth = {h, w};
		
		map = new char[h+3][w+4]; //add 3 for the numbers and border of row/column
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				// putting the top and bottom border
				if (i == 1 || i == h+2) {
					if (j == 2 || j == w+3)
						map[i][j] = '+';
					else if (j > 2 && j < w+3)
						map[i][j] = '-';
				}
				
				else if (i == 0 && j > 3 && j <= w+3) {
					int num = (j-3) % 10;
					map[i][j] = (char)(num + '0');
				}
				else if (j == 0 && i > 1 && i <= h+3) {
					int num = (i-1) % 10;
					map[i][j] = (char)(num + '0');
				}
				
				else if (i > 1 && i < h+3) {
					if (j == 1 || j == w+2)
						map[i][j] = '|';
					else if (j > 1 && j < w+2)
						map[i][j] = '.';
				}
			}
		} return heightWidth;
	}
	
	//Find Location of Obstacles
	public static int[] obstacleLoc(String line) {
		int[] placement = new int[4];
		Scanner parse = new Scanner(line);
		
		if (parse.hasNext()) {
			String obstacle = parse.next();
			placement[0] = parse.nextInt();
			placement[1] = parse.nextInt();
			placement[2] = parse.nextInt();
			placement[3] = parse.nextInt();
		}
		return placement;
	}
	
	//Replace interior of map with obstacle or treasure - helper method
	public static void replace(int[] placement) {
		if (placement.length == 4) {
			for (int i = placement[0]+1; i <= placement[2]+1; i++) {
				for (int j = placement[1]+1; j <= placement[3]+1; j++)
						map[i][j] = '*';
			}
		}
		else
			map[placement[0]+1][placement[1]+1] = '$';
	}
	
	// generate random place for the treasure to be placed
	public static int[] randomPlace(int[] coordinates) {
		Random rand = new Random();
		int[] placement = new int[2];
		placement[0] = rand.nextInt(coordinates[0]+2);
		placement[1] = rand.nextInt(coordinates[1]+2);
		return placement;
	}
	
	//Print the Map
	public static void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
					System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}	
	}
	
	//get input from the user
	public static int[] getInput() {
		Scanner x = new Scanner(System.in);
		while (true) {
			System.out.print(PROMPT);
			String input = x.nextLine();
			String[] points = input.split(" ");
			
			/* try {
				Integer.parseInt(points[0]);
			} catch (NumberFormatException e) {
				invalid = true;
			} try {
				Integer.parseInt(points[1]);
			} catch (NumberFormatException e) {
				invalid = true;
			}	
			*/ 
			
			if (points.length == 2)
				break;
		}
		
		int[] input = new int[2];
		input[0] = x.nextInt();
		input[1] = x.nextInt();
		
		return input;
	}
	
	//check what the input is
	public static void checkInput(int[] guess) {
		if (guess[0] == 0 && guess[1] == 0)
			System.out.println("Goodbye");
		else if (map[guess[0]+1][guess[1]+1] == '$')
			System.out.println("(" + guess[0] + "," + guess[1] + ") is a treasure");
		else if (map[guess[0]+1][guess[1]+1] == '*')
			System.out.println("(" + guess[0] + "," + guess[1] + ") is within an obstacle");
		else if (map[guess[0]+1][guess[1]+1] == '.')
			System.out.println("(" + guess[0] + "," + guess[1] + ") is an empty cell");
		else
			System.out.println("(" + guess[0] + "," + guess[1] + ") is out of the map");
	}

}