import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This algorithm takes as input a weighted graph with positive, negative or
 * neutral edges and splits the nodes to three (3) different lists based on the
 * conditions:
 * 
 * 1. If two nodes are connected with a positive edge they should be in the same
 * list
 * 
 * 2. If two nodes are connected with a negative edge they should be in
 * different lists
 * 
 * 3. If two nodes are connected with a neutral edge we don't care.
 * 
 * It uses as execution method the SAT Lingeling Solver so, you can only execute
 * it in Linux environment.
 * 
 * @author mpafit02 (Marios Pafitis)
 *
 */
public class GraphModeler {
	/**
	 * THis method creates the CNF file based on the graph input to be executed
	 * in the SAT Lingeling solver.
	 * 
	 * @param filename
	 * @param sign
	 * @param nodeNo
	 */
	public static void createCNF(String filename, String[][] sign, int nodeNo) {
		File file = new File(filename);
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			int[][] map = new int[nodeNo][3];
			// Initialize map
			int count = 1;
			for (int i = 0; i < nodeNo; i++) {
				for (int j = 0; j < 3; j++) {
					map[i][j] = count;
					count++;
				}
			}

			// Print Map
//			for (int i = 0; i < 3; i++) {
//				System.out.print("\tlist" + i + "\t");
//			}
//			System.out.println();
//			for (int i = 0; i < nodeNo; i++) {
//				System.out.print("X" + i + ":");
//				for (int j = 0; j < 3; j++) {
//					System.out.print("\t[" + i + ", " + j + "]: " + map[i][j] + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();

			// Calculate number of clauses
			int clauses = 3;
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

			// No list should be empty
			for (int j = 0; j < 3; j++) {
				for (int i = 0; i < nodeNo; i++) {
					writer.write(map[i][j] + " ");
				}
				writer.write("0\n");
			}
			// Create the body of CNF
			for (int i = 0; i < nodeNo; i++) {
				// For each node

				// Make sure you have at least one list that you are true
				for (int j = 0; j < 3; j++) {
					writer.write(map[i][j] + " ");
				}
				writer.write("0\n");

				// Make sure that you are true in only one list
				writer.write("-" + map[i][0] + " " + "-" + map[i][1]);
				writer.write(" 0\n");
				writer.write("-" + map[i][1] + " " + "-" + map[i][2]);
				writer.write(" 0\n");
				writer.write("-" + map[i][2] + " " + "-" + map[i][0]);
				writer.write(" 0\n");

				// For the nodes that I have a connection
				for (int j = i; j < nodeNo; j++) {
					if (sign[i][j].equals("+")) {
						// If I have a positive edge, check that we are in the
						// same list

						// If you are in the first list I have to be too
						writer.write("-" + map[i][0] + " " + "-" + map[j][1]);
						writer.write(" 0\n");
						writer.write("-" + map[i][0] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

						// If you are in the second list I have to be too
						writer.write("-" + map[i][1] + " " + "-" + map[j][0]);
						writer.write(" 0\n");
						writer.write("-" + map[i][1] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

						// If you are in the third list I have to be too
						writer.write("-" + map[i][2] + " " + "-" + map[j][0]);
						writer.write(" 0\n");
						writer.write("-" + map[i][2] + " " + "-" + map[j][1]);
						writer.write(" 0\n");

					} else if (sign[i][j].equals("-")) {
						// If we have a negative edge make sure we are in
						// different lists

						// If you are in the first list I have to be the second
						// or third list
						writer.write("-" + map[i][0] + " " + "-" + map[j][0]);
						writer.write(" 0\n");

						// If you are in the second list I have to be the first
						// or third list
						writer.write("-" + map[i][1] + " " + "-" + map[j][1]);
						writer.write(" 0\n");

						// If you are in the third list I have to be the first
						// or second list
						writer.write("-" + map[i][2] + " " + "-" + map[j][2]);
						writer.write(" 0\n");

					}
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Execute the Linegling Solver and print the appropriate results.
	 * 
	 * @param lingeling
	 * @param file_cnf
	 */
	public static void executeLingeling(String lingeling, String file_cnf) {
		String[] params = { lingeling, file_cnf };
		String result = null;
		boolean solution = false;
		try {
//			System.out.println("\nLingeling Started!");
			Process proc = Runtime.getRuntime().exec(params);
			BufferedReader lingeling_out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = lingeling_out.readLine()) != null) {
				if (line.charAt(0) == 'v') {
					result = line;
//					System.out.println(line);
					solution = true;
				}
			}
//			System.out.println("Lingeling Finished!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!solution) {
			System.out.println("The given graph cannot be divided into a total of three (3) sets");
			return;
		}

		ArrayList<Integer> set1 = new ArrayList<>();
		ArrayList<Integer> set2 = new ArrayList<>();
		ArrayList<Integer> set3 = new ArrayList<>();
		String[] res = result.split(" ");
		for (int i = 1; i < res.length - 1; i++) {
			if (res[i].charAt(0) != '-') {
				if (i % 3 == 1) {
					set1.add((int) Math.ceil(i / 3.0));
				} else if (i % 3 == 2) {
					set2.add((int) Math.ceil(i / 3.0));
				} else {
					set3.add((int) Math.ceil(i / 3.0));
				}
			}
		}
		System.out.println("The three (3) set that can be generated based on the matrix above are:");
		System.out.println("Set 1 = " + set1.toString());
		System.out.println("Set 2 = " + set2.toString());
		System.out.println("Set 3 = " + set3.toString());
	}

	/**
	 * Generate the sign array in the case that you had selected the argument
	 * one (1)
	 * 
	 * @param nodeNo
	 * @param negPerc
	 * @param posPerc
	 * @param density
	 * @return
	 */
	public static String[][] generateSigns(int nodeNo, float negPerc, float posPerc, float density) {
		String[][] sign = new String[nodeNo][nodeNo];
		for (int i = 0; i < nodeNo; i++) {
			for (int j = 0; j < nodeNo; j++) {
				sign[i][j] = "0";
			}
		}

		int edges = (int) ((density * nodeNo * (nodeNo - 1)) / 2.0);
		int i = 0;
		int j = 0;
		for (int e = 0; e < edges; e++) {
			do {
				i = (int) (Math.random() * nodeNo);
				j = (int) (Math.random() * nodeNo);
			} while (!sign[i][j].equals("0") || i <= j);
			if (Math.random() <= posPerc) {
				sign[i][j] = "+";
				sign[j][i] = "+";
			} else {
				sign[i][j] = "-";
				sign[j][i] = "-";
			}
		}

		return sign;
	}

	/**
	 * Read the input from the user and act accordingly to the arguments. If the
	 * argument is 0 then read a graph.txt file from the user. If the argument
	 * is 1 then ask the user for the number of nodes, the percentage of
	 * positive and negative edges and the density of the graph.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan_input = new Scanner(System.in);
		String file_input = null;

		String file_cnf = "./cnf/graph.cnf";
		String lingeling = "./lingeling/lingeling";

		int[][] graph = null;
		String[][] sign = null;

		Scanner scan = null;
		int nodeNo = 0;
		float negPerc = 0;
		float posPerc = 0;
		float density = 0;

		if (args[0].equals("0")) {
			// Read filename
			System.out.print("Filename: ");
			file_input = scan_input.next();

			File file = new File(file_input);
			try {
				// Read file input
				scan = new Scanner(file);
				nodeNo = scan.nextInt();
				negPerc = scan.nextFloat();
				posPerc = scan.nextFloat();
				density = scan.nextFloat();
				graph = new int[nodeNo][nodeNo];
				sign = new String[nodeNo][nodeNo];
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
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else if (args[0].equals("1")) {
			// Read input from user
			System.out.print("Number of Nodes: ");
			nodeNo = scan_input.nextInt();
			System.out.print("Negative Percentage: ");
			negPerc = scan_input.nextFloat();
			System.out.print("Positives Percentage: ");
			posPerc = scan_input.nextFloat();
			System.out.print("Density: ");
			density = scan_input.nextFloat();
			System.out.println();
			// Create sign table
			sign = generateSigns(nodeNo, negPerc, posPerc, density);

			// Create graph table
			graph = new int[nodeNo][nodeNo];
			for (int i = 0; i < nodeNo; i++) {
				for (int j = 0; j < nodeNo; j++) {
					if (!sign[i][j].equals("0")) {
						graph[i][j] = 1;
					}
				}
			}
		}
		scan_input.close();

		// Print Graph
		System.out.println("Graph:");
		for (int i = 0; i < nodeNo; i++) {
			for (int j = 0; j < nodeNo; j++) {
				System.out.print(graph[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();

		// Print Signs
		System.out.println("Signs:");
		for (int i = 0; i < nodeNo; i++) {
			for (int j = 0; j < nodeNo; j++) {
				System.out.print(sign[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();

		// Create the CNF file
		createCNF(file_cnf, sign, nodeNo);

		// Execute the lingeling with CNF input
		executeLingeling(lingeling, file_cnf);
	}

}
