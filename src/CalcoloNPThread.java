import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class CalcoloNPThread {
	private StringTokenizer st = null;
	private LinkedList<NPThread> listaThread = null;
	private LinkedList<Albero> alberiComputabili = null;
	private Albero nodoPrincipale;

	private String leggiEspressione(String percorso) throws IOException {
		File f = new File(percorso);
		if (!f.exists() || f.isDirectory())
			throw new IOException("Il percorso per il file dell'espressione non è valido");
		return new String(Files.readAllBytes(Paths.get(percorso)));
	}

	private void calcola() {
		int numCoreDisponibili = Runtime.getRuntime().availableProcessors();
		System.out.println("Creo " + numCoreDisponibili + " thread.");
		for (int i = numCoreDisponibili; i > 0; i--)
			listaThread.add(new NPThread());
		for (NPThread t : listaThread)
			t.start();
		try {
			for (NPThread t : listaThread)
				t.join();
		} catch (InterruptedException e) {
			System.err.println("Esecuzione interrotta");
			// esco male
			System.exit(-1);
		}
	}

	public CalcoloNPThread(String percorso) throws IllegalArgumentException, NoSuchElementException, IOException {
		String expr = leggiEspressione(percorso);
		st = new StringTokenizer(expr);
		String prova = "";
		try {
			prova = st.nextToken();
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Il file non può essere vuoto");
		}
		try {
			Double.parseDouble(prova);
			throw new IllegalArgumentException("Il file non può iniziare con un numero");
		} catch (NumberFormatException e) {
			// se sono qui il file è buono
			alberiComputabili = new LinkedList<Albero>();
			listaThread = new LinkedList<NPThread>();
			System.out.println("Inizio costruzione albero.");
			long start = System.currentTimeMillis();
			// costruisco tutto l'albero con questa prima chiamata
			nodoPrincipale = new Albero(null, prova);
			System.out.println("Albero costruito in " + (System.currentTimeMillis() - start) + " millisecondi.");
			System.out.println("Inizio del calcolo.");
			long startCalcolo = System.currentTimeMillis();
			calcola();
			long fine = System.currentTimeMillis();
			System.out.println("Calcolo eseguito in " + (fine - startCalcolo) + " millisecondi.");
			System.out.println("Risultato: " + nodoPrincipale.risultato);
			System.out.println("Tempo totale multithread: " + (fine - start) + " millisecondi.");
		}
	}

	/**
	 * Thread che eseguirà i calcoli nell'albero, partendo dal basso e via via
	 * risalendo fino al nodo radice.
	 * 
	 */
	private class NPThread extends Thread {

		public void run() {
			synchronized (alberiComputabili) {
				// se il risultato della radice è ancora NaN vado avanti
				while (Double.isNaN(nodoPrincipale.risultato)) {
					try {
						risolviSottoAlbero(alberiComputabili.removeFirst());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		/**
		 * Calcola il risultato della singola operazione di un sottoalbero.
		 * 
		 * @param a
		 *            L'albero da calcolare
		 * @throws InterruptedException
		 *             Se viene interrotto il calcolo dall'esterno.
		 */
		private synchronized void risolviSottoAlbero(Albero a) throws InterruptedException {
			switch (a.operazione) {
			case "+":
				a.risultato = a.sx.risultato + a.dx.risultato;
				break;
			case "-":
				a.risultato = a.sx.risultato - a.dx.risultato;
				break;
			case "*":
				a.risultato = a.sx.risultato * a.dx.risultato;
				break;
			case "/":
				a.risultato = a.sx.risultato / a.dx.risultato;
				break;
			default:
				throw new IllegalArgumentException(a.operazione + " non è un'operazione valida");
			}
			if (a.padre != null && a.padre.isCalcolabile())
				alberiComputabili.addFirst(a.padre);
		}
	}

	public class Albero {

		private Albero padre, dx, sx = null;
		private String operazione = "";
		private double risultato = 0.0d;

		/**
		 * Se è calcolabile, entrambi i figli hanno il risultato diverso da NaN
		 * (e ovviamente non sono null).
		 * 
		 * @return true se questo sottoalbero è calcolabile, false altrimenti.
		 */
		public boolean isCalcolabile() {
			return dx != null ? !Double.isNaN(sx.risultato) && !Double.isNaN(dx.risultato) : false;
		}

		Albero(Albero padre, String operazione) {
			risultato = Double.NaN;
			this.padre = padre;
			this.operazione = operazione;
			String ss = "";
			try {
				ss = st.nextToken();
				sx = new Albero(Double.parseDouble(ss));
			}
			// se sono qui significa che ho trovato un'altra operazione
			catch (NumberFormatException e0) {
				sx = new Albero(this, ss);
			}
			// stessa cosa per il figlio destro
			try {
				ss = st.nextToken();
				dx = new Albero(Double.parseDouble(ss));
				// se anche il figlio destro è un numero quest'albero è calcolabile
				alberiComputabili.addFirst(this);
			} catch (NumberFormatException e0) {
				dx = new Albero(this, ss);
			}
		}

		Albero(double valore) {
			risultato = valore;
		}
	}
}