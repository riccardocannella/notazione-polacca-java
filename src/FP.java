import java.util.*;
import java.io.*;
/**
 * 
 * @author Riccardo Cannella & Corrado Petrelli
 *
 */
public class FP {
	private Random r;
	private FileWriter out;
	/**
	 * Profondità dell'albero
	 */
	private final int DEPTH = 22;

	public static void main(String[] args) {
		// Stabilisci percorso assoluto del file dell'espressione
		String separatore = System.getProperty("file.separator");
		String percorso = System.getProperty("user.home") + separatore + "Desktop" + separatore + "expression.txt";
		System.out.println("Scrivo il file di testo in " + percorso);
		// Genera il file
		new FP(percorso);
		System.out.println("Fine scrittura");
		System.out.println("---------------------------------");
		double risultato = 0;
		
		// #####################
		// # calcolo ricorsivo #
		// #####################
		System.out.println("Inizio del calcolo in maniera ricorsiva.");
		try {
			risultato = new CalcoloNPRicorsivo(percorso).calcoloRicorsivo();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Risultato: " + risultato);
		System.out.println("---------------------------------");
		risultato = 0;
		
		// #######################
		// # calcolo multithread #
		// #######################
		System.out.println("Inizio del calcolo con i thread.");
		try {
			new CalcoloNPThread(percorso);
			System.out.println("Fine");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.exit(0);
	}

	/**
	 * Costruttore per generare il file
	 * @param percorso Percorso del file
	 */
	public FP(String percorso) {
		r = new Random();
		String e;
		StringTokenizer st;
		try {
			out = new FileWriter(percorso);
			e = go(DEPTH);
			st = new StringTokenizer(e);
			while (st.hasMoreTokens())
				out.write(" " + st.nextToken());
			out.flush();
			out.close();
		} catch (Exception f) {
			System.exit(1);
		}
	}

	/*
	 * Metodo ricorsivo che genera la stringa dell'espressione
	 */
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
			default: 
				return "";
			}
		} else {
			return " " + r.nextDouble();
		}
	}
}
