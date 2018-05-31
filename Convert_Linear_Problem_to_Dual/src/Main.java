import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		int n = 0;
		boolean noterror = true; /*
									 * a flag which become false if something
									 * goes wrong with my input file
									 */

		int[] MinMAx = new int[1]; /*
									 * A table where I save-1 if I have a
									 * minimization problem or +1 if I have a
									 * maximization problem
									 */
		ArrayList<Integer> c = new ArrayList<>();
		ArrayList<Integer> b = new ArrayList<>();
		ArrayList<Integer> Eqin = new ArrayList<>();
		/* A 2 dimension array where I save the st' s coefficients */
		ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>();

		ReadFile reader = new ReadFile();
		noterror = reader.isNoterror(); /*
										 * Check if there was any problem with
										 * input file
										 */
		/* If there wasn't any problem start to find the LP-2 */
		if (noterror) {

			int[] tempMinMAx = new int[1]; /*
											 * A table where I save-1 if I have
											 * a minimization problem or +1 if I
											 * have a maximization problem. It
											 * takes his value from the LP-1
											 */
			ArrayList<Integer> tempC = new ArrayList<>(); /*
															 * the C's values
															 * from LP-1
															 */
			ArrayList<Integer> tempB = new ArrayList<>(); // the b' values from
															// LP-1
			ArrayList<Integer> tempEqin = new ArrayList<>(); // The Eqin's
																// values
			// from LP-1
			/* The A's values from LP-1 */
			ArrayList<ArrayList<Integer>> tempA = new ArrayList<ArrayList<Integer>>();

			tempC = reader.getC();
			tempMinMAx = reader.getMinMAx();
			tempB = reader.getB();
			tempEqin = reader.getEqin();
			tempA = reader.getA();
			n = tempC.size();
			c = tempB;
			b = tempC;
			int[] variableCounter = new int[tempB.size()];
			/*
			 * A table where I store my variables from LP-1
			 */
			for (int k = 0; k < n; k++) {

				A.add(new ArrayList<Integer>());
			}
			int i = 0;
			while (i < tempA.size()) {
				int j = 0;
				while (j < tempA.get(i).size()) {
					/*
					 * If my indexes point to a diagonal's value then put that
					 * value in A, else swap this value with it's symmetric
					 */
					if (i == j) {
						A.get(i).add(tempA.get(i).get(i));
					} else {
						A.get(j).add(i, tempA.get(i).get(j));
					}
					j++;
				}
				i++;
			}

			if (tempMinMAx[0] == -1) {
				/*
				 * If LP-1 was a minimization problem store its value to
				 * variableCounter and add -1 to the new Eqin array
				 */
				MinMAx[0] = 1;
				for (int k = 0; k < tempEqin.size(); k++) {
					variableCounter[k] = tempEqin.get(k);
				}
				for (int k = 0; k < tempC.size(); k++) {
					Eqin.add(-1);
				}
			} else {
				/*
				 * If LP-1 was a maximization problem store its value to
				 * variableCounter multiplied with (-1) and add 1 to the new
				 * Eqin array
				 */
				MinMAx[0] = -1;
				for (int k = 0; k < tempEqin.size(); k++) {
					variableCounter[k] = (-1) * tempEqin.get(k);
				}
				for (int k = 0; k < tempC.size(); k++) {
					Eqin.add(1);
				}
			}
			reader.setA(A);
			reader.setB(b);
			reader.setC(c);
			reader.setEqin(Eqin);
			reader.setMinMAx(MinMAx);
			reader.setVariableCounter(variableCounter);
			reader.writerFile();

		}
	}
}
