import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class CalcoloNPThread {
	
	private Scanner s;
	/**
	 * Mappa che rappresenterà un albero binario:
	 * 		il figlio sx avrà indice 2*indiceRadice+1
	 * 		il figlio dx avrà indice 2*indiceRadice+2
	 * è utile per poi creare i thread
	 */
	private HashMap<Integer, Object> albero;
	
	/**
	 * Costruttore
	 * @param percorso Percorso dell'espressione
	 * @throws IOException
	 */
	public CalcoloNPThread(String percorso) throws IOException{
		s = new Scanner(new File(percorso));
		s.useLocale(new Locale("en", "us"));
	}
	

	/**
	 * Calcolo dell'esecuzione del Thread
	 * @param percorso Percorso dell'esecuzione
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
	 * 
	 * @return
	 */
	private double calcolaThreadPrivato() {
		System.out.println("Inizio creazione dell'albero.");
		albero = new HashMap<Integer, Object>(10000000);
		long startAlbero = System.currentTimeMillis();
		costruisciAlbero(0);
		long fineAlbero = System.currentTimeMillis();
		s.close();
		s=null;
		System.out.println("Albero costruito in " + (fineAlbero - startAlbero) + " millisecondi.");
		// a questo punto devo iniziare a eseguire tutti i calcoli che sono già
		// possibili per poi andare mano a mano a consumare tutto l'albero

		// che tipo di struttura? una coda?
		return 0.0;
	}

	/**
	 * @param indice
	 */
	private void costruisciAlbero(int indice) {
		boolean ilFiglioSxEUnNumero = false;
		// se sono qui sicuramente il prossimo token è un segno
		albero.put(new Integer(indice), s.next());
		// inizio i check:
		// se il prossimo token è un numero, lo metto all'indice 2*indice+1
		if (s.hasNextDouble()) {
			albero.put(2 * indice + 1, s.nextDouble());
			ilFiglioSxEUnNumero = true;
		}
		// altrimenti inizio col prossimo sottoalbero, andando a sx
		else
			costruisciAlbero((2 * indice) + 1);
		// stessa identica cosa per il figlio destro
		if (s.hasNextDouble()) {
			albero.put(2 * indice + 2, s.nextDouble());
			if (ilFiglioSxEUnNumero) {
				// aggiungo questo mini albero a quelli computabili
				// immediatamente dato che entrambi i figli sono numeri
				
				// la struttura va usata qui e nel metodo che genererà i thread
			}
		} else
			costruisciAlbero(2 * indice + 2);
	}

}
