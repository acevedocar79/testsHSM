package pruebaHSM.pruebaHSM;

import java.util.ArrayList;
import java.util.List;

import es.anzen.real.Realsecure;

/**
 * <p></p>
 * 
 * @author acevedito
 * @version pruebaHSM
 * @since pruebaHSM
 * @category
 */
public class App {

	/**
	 * <p></p>
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws InterruptedException {

		int requests = Integer.parseInt(args[1]);
		int connections = Integer.parseInt(args[0]);

		List<Thread> foo = new ArrayList<>();
		for (int i = 0; i < connections; i++) {
			Realsecure secure = new Realsecure("localhost", 2001, 4);
			Runnable bar = new StressTest(requests, secure);
			
			Thread connection = new Thread(bar);
			connection.setName("Stress Test: " + i);
			foo.add(connection);
		}

		for (Thread x : foo) {
			x.start();
		}

		System.out.println("All stress tests are running");
	}
}
