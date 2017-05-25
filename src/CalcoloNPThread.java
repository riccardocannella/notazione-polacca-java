import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * 
 * @author Riccardo Cannella & Corrado Petrelli
 *
 */
public class CalcoloNPThread {

	private Scanner scanner;
	/**
	 * Mappa che rappresenterà un albero binario: il figlio sx avrà indice
	 * 2*indiceRadice+1 il figlio dx avrà indice 2*indiceRadice+2 è utile per
	 * poi creare i thread
	 */
	private HashMap<Integer, Object> albero;

	/**
	 * Costruttore della classe
	 * 
	 * @param percorso
	 *            Percorso del file
	 * @throws IOException
	 */
	public CalcoloNPThread(String percorso) throws IOException {
		scanner = new Scanner(leggiEspressione(percorso));
		scanner.useLocale(new Locale("en", "us"));
	}

	/**
	 * Calcolo dell'esecuzione del Thread
	 * 
	 * @param percorso
	 *            Percorso dell'esecuzione
	 * @return Risultato del thread
	 * @throws IOException
	 */
	public double calcoloConThread() throws IOException {
		long start = System.currentTimeMillis();
		double res = this.calcolaThreadPrivato();
		long end = System.currentTimeMillis();
		System.out.println("Calcolo in multithreading eseguito in " + (end - start) + " millisecondi totali.");
		return res;
	}

	/**
	 * Legge l'espressione in un determinato percorso (se esiste) e restituisci
	 * la stringa dell'espressione
	 * 
	 * @param percorso
	 *            Percorso del file
	 * @return Espressione
	 * @throws IOException
	 *             Il file non esiste o è una cartella
	 */
	private String leggiEspressione(String percorso) throws IOException {
		File f = new File(percorso);
		if (!f.exists() || f.isDirectory())
			throw new IOException("Il percorso per il file dell'espressione non è valido");
		return new String(Files.readAllBytes(Paths.get(percorso)));
	}

	/**
	 * Crea l'albero
	 * 
	 * @return
	 */
	private double calcolaThreadPrivato() {
		System.out.println("Inizio creazione dell'albero.");
		albero = new HashMap<Integer, Object>(8500000); // con DEPTH==22 questo valore è sufficiente
		long startAlbero = System.currentTimeMillis();
		costruisciAlbero(0);
		long fineAlbero = System.currentTimeMillis();
		scanner.close();
		scanner = null;
		System.out.println("Albero costruito in " + (fineAlbero - startAlbero) + " millisecondi.");
		// a questo punto devo iniziare a eseguire tutti i calcoli che sono già
		// possibili per poi andare mano a mano a consumare tutto l'albero
		
		// che tipo di struttura? una coda?
		return 0.0;
	}

	/**
	 * Costruisco l'albero generato dall'espressione
	 * 
	 * @param indice
	 */
	private void costruisciAlbero(int indice) {		
		// se sono qui sicuramente il prossimo token è un segno
		// inserisco l'indice come chiave e leggo il segno
		albero.put(indice, scanner.next());

		// CONTROLLI
		// se il prossimo token è un numero, lo metto all'indice 2*indice+1
		// (decisione: il numero subito dopo il segno diventa figlio sinistro)
		if (scanner.hasNextDouble()) {
			albero.put(2 * indice + 1, scanner.nextDouble());
		}
		// (ho un altro segno) inizio col prossimo sottoalbero, andando a sx
		else {
			costruisciAlbero(2 * indice + 1);
		}
		// Stessa identica cosa per il figlio destro
		if (scanner.hasNextDouble()) {
			albero.put(2 * indice + 2, scanner.nextDouble());
		} else {
			// Ho trovato ancora un altro segno
			costruisciAlbero(2 * indice + 2);
		}

	}

}
