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
	private Scanner scanner;
	
	/**
	 * Costruttore della classe
	 * @param percorso Percorso del file
	 * @throws IOException
	 */
	public CalcoloNPRicorsivo(String percorso) throws IOException{
		//Scannerizza l'espressione presente nel file nel percorso passato come parametro
		scanner = new Scanner(leggiEspressione(percorso));
		//Setta la lingua locale cosicch� non ci siano problemi con il carattere '.'
		scanner.useLocale(new Locale("en", "us"));
	}
	
	/**
	 * Legge l'espressione in un determinato percorso (se esiste) e restituisci
	 * la stringa dell'espressione
	 * @param percorso Percorso del file
	 * @return Espressione
	 * @throws IOException Il file non esiste o � una cartella
	 */
	private String leggiEspressione(String percorso) throws IOException {
		File f = new File(percorso);
		if (!f.exists() || f.isDirectory())
			throw new IOException("Il percorso per il file dell'espressione non � valido");
		return new String(Files.readAllBytes(Paths.get(percorso)));
	}
	
	/**
	 * Metodo che calcola il tempo di esecuzione ed esegue il calcolo
	 * @return il risultato del calcolo
	 * @throws Exception Formato stringa errato
	 */
	public double calcoloRicorsivo() throws Exception {
		// se il primo token � un numero non va bene
		if (scanner.hasNextDouble())
			throw new Exception("La stringa non pu� iniziare con un numero!");
		long start = System.currentTimeMillis();
		double res = calcolaRec();
		long end = System.currentTimeMillis();
		System.out.println("Calcolo eseguito in " + (end - start) + " millisecondi.");
		scanner.close();
		//libera la ram
		scanner = null;
		return res;
	}

	/**
	 * Meotodo ricorsivo che esegue i calcoli
	 * @return risultato del calcolo
	 * @throws Exception
	 */
	private double calcolaRec() throws Exception {
		String next = scanner.next();
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
