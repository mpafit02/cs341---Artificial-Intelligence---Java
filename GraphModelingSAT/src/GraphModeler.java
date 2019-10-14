import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphModeler {

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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
