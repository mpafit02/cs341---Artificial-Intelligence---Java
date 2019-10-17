import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphModeler {

	public static void createCNF(String filename, String[][] sign, int nodeNo) {
		File file = new File(filename);
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			int[][] map = new int[nodeNo][3];
			// Initialize map
			int count = 1;
			System.out.println("Map: ");
			for (int i = 0; i < 3; i++) {
				System.out.print("\tlist" + i + "\t");
			}
			System.out.println();
			for (int i = 0; i < nodeNo; i++) {
				System.out.print("X" + i + ":");
				for (int j = 0; j < 3; j++) {
					map[i][j] = count;
					System.out.print("\t[" + i + ", " + j + "]: " + map[i][j] + " ");
					count++;
				}
				System.out.println();
			}
			System.out.println();

			// Calculate number of clauses
			int clauses = 0;
			for (int i = 0; i < nodeNo; i++) {
				clauses += 4;
				for (int j = i; j < nodeNo; j++) {
					if (sign[i][j].equals("+")) {
						clauses += 6;
					} else if (sign[i][j].equals("-")) {
						clauses += 3;
					}
				}
			}
			// Number of variables
			int vars = 3 * nodeNo;
			
			// Write the Header of the CNF
			writer.write("p cnf " + vars + " " + clauses + "\n");

			// Create the body of CNF
			for (int i = 0; i < nodeNo; i++) {
				// For each node
				System.out.println("X" + i + ":");

				// Make sure you have at least one list that you are true
				for (int j = 0; j < 3; j++) {
					writer.write(map[i][j] + " ");
					System.out.print(map[i][j] + " ");
				}
				writer.write("0\n");
				System.out.println("0");

				// Make sure that you are true in only one list
				System.out.print("-" + map[i][0] + " " + "-" + map[i][1]);
				System.out.println(" 0");
				System.out.print("-" + map[i][1] + " " + "-" + map[i][2]);
				System.out.println(" 0");
				System.out.print("-" + map[i][2] + " " + "-" + map[i][0]);
				System.out.println(" 0");

				writer.write("-" + map[i][0] + " " + "-" + map[i][1]);
				writer.write(" 0\n");
				writer.write("-" + map[i][1] + " " + "-" + map[i][2]);
				writer.write(" 0\n");
				writer.write("-" + map[i][2] + " " + "-" + map[i][0]);
				writer.write(" 0\n");

				// For the nodes that I have a connections
				for (int j = i; j < nodeNo; j++) {
					if (sign[i][j].equals("+")) {
						// If I have a positive edge, check that we are in the same list

						// If you are in the first list I have to be too
						System.out.print("-" + map[i][0] + " " + "-" + map[j][1]);
						System.out.println(" 0");
						System.out.print("-" + map[i][0] + " " + "-" + map[j][2]);
						System.out.println(" 0");

						writer.write("-" + map[i][0] + " " + "-" + map[j][1]);
						writer.write(" 0\n");
						writer.write("-" + map[i][0] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

						// If you are in the second list I have to be too
						System.out.print("-" + map[i][1] + " " + "-" + map[j][0]);
						System.out.println(" 0");
						System.out.print("-" + map[i][1] + " " + "-" + map[j][2]);
						System.out.println(" 0");

						writer.write("-" + map[i][1] + " " + "-" + map[j][0]);
						writer.write(" 0\n");
						writer.write("-" + map[i][1] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

						// If you are in the third list I have to be too
						System.out.print("-" + map[i][1] + " " + "-" + map[j][0]);
						System.out.println(" 0");
						System.out.print("-" + map[i][1] + " " + "-" + map[j][2]);
						System.out.println(" 0");

						writer.write("-" + map[i][1] + " " + "-" + map[j][0]);
						writer.write(" 0\n");
						writer.write("-" + map[i][1] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

					} else if (sign[i][j].equals("-")) {
						// If we have a negative edge make sure we are in different lists

						// If you are in the first list I have to be the second or third list
						System.out.print("-" + map[i][0] + " " + "-" + map[j][0]);
						System.out.println(" 0");

						// If you are in the second list I have to be the first or third list
						System.out.print("-" + map[i][1] + " " + "-" + map[j][1]);
						System.out.println(" 0");

						// If you are in the third list I have to be the first or second list
						System.out.print("-" + map[i][2] + " " + "-" + map[j][2]);
						System.out.println(" 0");

						writer.write("-" + map[i][0] + " " + "-" + map[j][0]);
						writer.write(" 0\n");
						writer.write("-" + map[i][1] + " " + "-" + map[j][1]);
						writer.write(" 0\n");
						writer.write("-" + map[i][2] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

					}
					System.out.println();
				}
				System.out.println();
			}
			System.out.println("Total number of clauses: " + clauses);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ArrayList<Integer> positives = new ArrayList<>();
		ArrayList<Integer> negatives = new ArrayList<>();
		ArrayList<Integer> neutrals = new ArrayList<>();
		String filename = ".\\graphs\\graph.txt";
		File file = new File(filename);
		Scanner scan;
		int nodeNo;
		float negPerc;
		float posPerc;
		float density;
		try {
			scan = new Scanner(file);
			nodeNo = scan.nextInt();
			negPerc = scan.nextFloat();
			posPerc = scan.nextFloat();
			density = scan.nextFloat();
			System.out.println(nodeNo + " " + negPerc + " " + posPerc + " " + density);
			int[][] graph = new int[nodeNo][nodeNo];
			String[][] sign = new String[nodeNo][nodeNo];
			for (int i = 0; i < nodeNo; i++) {
				for (int j = 0; j < nodeNo; j++) {
					graph[i][j] = scan.nextInt();
				}
			}
			scan.nextLine();
			for (int i = 0; i < nodeNo; i++) {
				for (int j = 0; j < nodeNo; j++) {
					sign[i][j] = scan.next();
				}
			}
			scan.close();

			for (int i = 0; i < nodeNo; i++) {
				for (int j = 0; j < nodeNo; j++) {
					System.out.print(graph[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();

			int realNo_i;
			int realNo_j;
			boolean hasCategory;

			for (int i = 0; i < nodeNo; i++) {
				for (int j = 0; j < nodeNo; j++) {
					realNo_i = i + 1;
					realNo_j = j + 1;
					hasCategory = false;
					// Add to neutrals
					if (sign[i][j].equals("0")) {
						if (!neutrals.contains(realNo_i)) {
							neutrals.add(realNo_i);
						}
						if (!neutrals.contains(realNo_j)) {
							neutrals.add(realNo_j);
						}
						hasCategory = true;
					}
					// Add to positives
					if (sign[i][j].equals("+")) {
						if (!positives.contains(realNo_i)) {
							positives.add(realNo_i);
						}
						if (!positives.contains(realNo_j)) {
							positives.add(realNo_j);
						}
						hasCategory = true;
					}
					// Add to negatives
					if (sign[i][j].equals("-")) {
						if (!negatives.contains(realNo_i)) {
							negatives.add(realNo_i);
						}
						if (!negatives.contains(realNo_j)) {
							negatives.add(realNo_j);
						}
						hasCategory = true;
					}
					if (!hasCategory) {
						System.out.print("Wrong input, can't match sign to a category!");
						System.exit(0);
					}
					System.out.print(sign[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println("Nutrals: " + neutrals.toString());
			System.out.println("Positives: " + positives.toString());
			System.out.println("Negatives: " + negatives.toString());

			System.out.println();
			createCNF("./graph.cnf", sign, nodeNo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
