import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author Riccardo Cannella & Corrado Petrelli
 *
 */
public class CalcoloNPRicorsivo {
	private Scanner s;
	
	/**
	 * Costruttore della classe
	 * @param percorso Percorso del file
	 * @throws IOException
	 */
	public CalcoloNPRicorsivo(String percorso) throws IOException{
		//Scannerizza l'espressione presente nel file nel percorso passato come parametro
		s = new Scanner(leggiEspressione(percorso));
		//Setta la lingua locale cosicchè non ci siano problemi con il carattere '.'
		s.useLocale(new Locale("en", "us"));
	}
	
	/**
	 * Legge l'espressione in un determinato percorso (se esiste) e restituisci
	 * la stringa dell'espressione
	 * @param percorso Percorso del file
	 * @return Espressione
	 * @throws IOException Il file non esiste o è una cartella
	 */
	private String leggiEspressione(String percorso) throws IOException {
		File f = new File(percorso);
		if (!f.exists() || f.isDirectory())
			throw new IOException("Il percorso per il file dell'espressione non è valido");
		String expr = new String(Files.readAllBytes(Paths.get(percorso)));
		return expr;
	}
	
	/**
	 * Metodo che calcola il tempo di esecuzione ed esegue il calcolo
	 * @return il risultato del calcolo
	 * @throws Exception Formato stringa errato
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
		//libera la ram
		s = null;
		return res;
	}

	

	/**
	 * Meotodo ricorsivo che esegue i calcoli
	 * @return risultato del calcolo
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
