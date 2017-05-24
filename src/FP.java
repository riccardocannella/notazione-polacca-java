import java.util.*;
import java.io.*;

public class FP {
	private Random r;
	private FileWriter out;
	private final int DEPTH = 22;

	public static void main(String[] args) {
		String percorso = System.getProperty("user.home") + "/Desktop/expression.txt";
		new FP(percorso);
		double res = 0;
		// calcolo ricorsivo
		System.out.println("Inizio del calcolo in maniera ricorsiva.");
		try{
		res = new CalcoloNPRicorsivo(percorso).calcoloRicorsivo();
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		System.out.println("Risultato: " + res);
		
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
		} else{
			return " " + r.nextDouble();
			}
		return "";
	}
}
