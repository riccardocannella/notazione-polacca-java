import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Riccardo Cannella & Petrelli Corrado
 *
 */
public class CalcoloNPRicorsivo {
	private Scanner s;
	
	/**
	 * @param percorso
	 * @throws IOException
	 */
	public CalcoloNPRicorsivo(String percorso) throws IOException{
		s = new Scanner(leggiEspressione(percorso));
		s.useLocale(new Locale("en", "us"));
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public double calcoloRicorsivo() throws Exception {
		// se il primo token è un numero non va bene
		if (s.hasNextDouble())
			throw new Exception("La stringa non può iniziare con un numero!");
		long start = System.currentTimeMillis();
		double res = calcolaRec();
		long end = System.currentTimeMillis();
		System.out.println("Calcolo eseguito in " + (end - start) + " millisecondi.");
		s.close();
		s = null;
		return res;
	}

	/**
	 * @param percorso
	 * @return
	 * @throws IOException
	 */
	private String leggiEspressione(String percorso) throws IOException {
		File f = new File(percorso);
		if (!f.exists() || f.isDirectory())
			throw new IOException("Il percorso per il file dell'espressione non è valido");
		String expr = new String(Files.readAllBytes(Paths.get(percorso)));
		return expr;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	private double calcolaRec() throws Exception {
		String next = s.next();
		if (next.equals("-"))
			return calcolaRec() - calcolaRec();
		else if (next.equals("+"))
			return calcolaRec() + calcolaRec();
		else if (next.equals("*"))
			return calcolaRec() * calcolaRec();
		else if (next.equals("/"))
			return calcolaRec() / calcolaRec();
		else
			return Double.parseDouble(next);
	}
}
