import java.util.concurrent.Callable;

/**
 * 
 */

/**
 * 
 * @author Riccardo Cannella & Corrado Petrelli
 *
 */
public class NPThread implements Callable<Double> {

	private char operazione;
	private double operando1;
	private double operando2;

	/**
	 * Costruttore per creare il thread
	 * @param operazione
	 * @param operando1
	 * @param operando2
	 * @throws Exception
	 */
	public NPThread(char operazione, double operando1, double operando2) throws Exception {
		if (!checkOperazione(operazione))
			throw new Exception("L'operazione non è valida");
		this.operazione = operazione;
		this.operando1 = operando1;
		this.operando2 = operando2;
		if(operando2 == 0 && operazione == '/')
			throw new ArithmeticException("Divisione per 0");
			
	}

	/**
	 * Controllo che sia un'operazione consentita
	 * @param c carattere dell'operazione
	 * @return true se è un'operazione consentita altrimenti false
	 */
	private boolean checkOperazione(char c) {
		switch (c) {
		case '-':
			return true;
		case '*':
			return true;
		case '/':
			return true;
		case '+':
			return true;
		default:
			return false;
		}
	}

	/**
	 * Chiamata del thread
	 */
	@Override
	public Double call() throws Exception {
		// eseguo il calcolo
		switch (operazione) {
		case '-':
			return operando1 - operando2;
		case '+':
			return operando1 + operando2;
		case '*':
			return operando1 * operando2;
		case '/':
			return operando1 / operando2;
		default:
			throw new Exception("Qualcosa è andato storto");
		}
	}

}
