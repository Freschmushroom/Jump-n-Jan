import java.io.IOException;

import at.freschmushroom.Out;
import at.freschmushroom.ServiceProvider;
import at.jumpandjan.JumpAndJan;

/**
 * Class to start the Game from a jar
 * 
 * @author Felix
 *
 */
public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ServiceProvider serviceProvider = new ServiceProvider();
		serviceProvider.libs();
		serviceProvider.gameJar();
		serviceProvider.resources();
		if (serviceProvider.requiresRestart) {
			ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "JumpnJan.jar");
			try
			{
				processBuilder.inheritIO().start();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			System.exit(0);
		}
		Out.surpressAdditionalInformation();
		JumpAndJan.main(args);
	}

}
