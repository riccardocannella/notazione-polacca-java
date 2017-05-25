import java.util.concurrent.Callable;

/**
 * 
 */

/**
 * @author Riccardo Cannella
 *
 */
public class NPThread implements Callable<Double> {

	private char operazione;
	private double operando1;
	private double operando2;

	public NPThread(char operazione, double operando1, double operando2) throws Exception {
		if (!checkOperazione(operazione))
			throw new Exception("L'operazione non è valida");
		this.operazione = operazione;
		this.operando1 = operando1;
		this.operando2 = operando2;
	}

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
		// ci fidiamo di operando2? potrebbe essere 0...
		case '/':
			return operando1 / operando2;
		default:
			throw new Exception("Qualcosa è andato storto");
		}
	}

}
