import at.freschmushroom.Out;
import at.freschmushroom.ServiceProvider;
import at.jumpandjan.JumpAndJan;

/**
 * Class to start the Game from a jar
 * @author Felix
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServiceProvider.libs();
		Out.surpressAdditionalInformation();
		JumpAndJan.main(args);
	}

}
