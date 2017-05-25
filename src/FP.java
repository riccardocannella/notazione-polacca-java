import java.util.*;
import java.io.*;

public class FP {
	private Random r;
	private FileWriter out;
	private final int DEPTH = 22;

	public static void main(String[] args) {
		// Stabilisci percorso assoluto del file dell'espressione
		String percorso = System.getProperty("user.home") + "/Desktop/expression.txt";
		System.out.println("Scrivo il file di testo in " + percorso);
		// Genera il file
		new FP(percorso);
		System.out.println("Fine scrittura");
		System.out.println("---------------------------------");
		double risultato = 0;
		// calcolo ricorsivo
		System.out.println("Inizio del calcolo in maniera ricorsiva.");
		try {
			risultato = new CalcoloNPRicorsivo(percorso).calcoloRicorsivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Risultato: " + risultato);
		System.out.println("---------------------------------");
		risultato = 0;
		// calcolo multithread
		System.out.println("Inizio del calcolo con i thread.");
		try {
			risultato = new CalcoloNPThread(percorso).calcoloConThread();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Risultato: " + risultato);

		System.out.println("Fine");
	}

	FP(String percorso) {
		r = new Random();
		String e;
		StringTokenizer st;
		try {
			out = new FileWriter(percorso);
			e = go(DEPTH);
			st = new StringTokenizer(e);
			while (st.hasMoreTokens())
				out.write(" " + st.nextToken());
			out.close();
		} catch (Exception f) {
			System.exit(1);
		}
	}

	private String go(int n) {
		if (n > 0) {
			switch (r.nextInt(3)) {
			case 0:
				return "+ " + go(n - 1) + " " + go(n - 1);
			case 1:
				return "- " + go(n - 1) + " " + go(n - 1);
			case 2:
				return "* " + go(n - 1) + " " + go(n - 1);
			case 3:
				return "/ " + go(n - 1) + " " + go(n - 1);
			}
		} else {
			return " " + r.nextDouble();
		}
		return "";
	}
}
