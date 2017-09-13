package fr.gouv.diplomatie.papyrus.codegen.core.console;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleUtils {

	public MessageConsole myConsole = findConsole("Hornet Papyrus Générateur");
	
	//stream classique
	public MessageConsoleStream out = myConsole.newMessageStream();
	
	//stream d'erreur
	public MessageConsoleStream err = getErrorMessageStream();
	
	//stream de warning
	public MessageConsoleStream warning = getWarningMessageStream();
	
	public ConsoleUtils(String consoleName){
		myConsole= findConsole(consoleName);
		out = myConsole.newMessageStream();
		err = getErrorMessageStream();
		warning = getWarningMessageStream();
	}
	
	public ConsoleUtils() {
		
	}
	
	/**
	 * récupération de la console liée au projet
	 * @param name
	 * @return
	 */
	public static MessageConsole findConsole(String name) {
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	         if (name.equals(existing[i].getName()))
	            return (MessageConsole) existing[i];
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	}
	
	/**
	 * récupération du stream des messages d'erreur
	 * les messages s'afficheront en rouge 
	 * @return
	 */
	public MessageConsoleStream getErrorMessageStream() {
		MessageConsoleStream stream = myConsole.newMessageStream();
		stream.setColor(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		return stream;
	}
	
	/**
	 * récupération du stream des warnings
	 * ils seront affichés en orange/jaune
	 * @return
	 */
	public MessageConsoleStream getWarningMessageStream() {
		MessageConsoleStream stream = myConsole.newMessageStream();
		Device device = Display.getCurrent ();
		Color orange = new Color (device, 245, 121, 0);
		stream.setColor(orange);
		return stream;
	}
}
