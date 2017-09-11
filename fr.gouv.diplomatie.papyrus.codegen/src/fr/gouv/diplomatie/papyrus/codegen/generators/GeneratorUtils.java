package fr.gouv.diplomatie.papyrus.codegen.generators;

import java.io.File;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils;

public class GeneratorUtils {
	
	public static final String SRC_REPOSITORY = "src-gen" + File.separator;
	public static final String SRC_INCODE_REPOSITORY = "src" + File.separator;
	
	public static final String MODEL_REPOSITORY = SRC_REPOSITORY + "models" + File.separator;
	public static final String MODEL_INCODE_REPOSITORY = SRC_INCODE_REPOSITORY + "models" + File.separator;
	
	public static final String DATABASE_REPOSITORY = SRC_REPOSITORY + "database" + File.separator ;
	public static final String DATABASE_INCODE_REPOSITORY = "database" + File.separator ;
	
	public static final String DAO_REPOSITORY = SRC_REPOSITORY  + "dao" + File.separator ;
	public static final String DAO_INCODE_REPOSITORY = SRC_INCODE_REPOSITORY  + "dao" + File.separator ;
	
	public static MessageConsole myConsole = findConsole("Hornet Papyrus Générateur");
	public static MessageConsoleStream out = myConsole.newMessageStream();
	
	public static String getModelPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "models" + File.separator  + Utils.toTypescriptFileName(clazz.getName()) + "-model";
		}
		return MODEL_REPOSITORY +  "models" + File.separator  + Utils.toTypescriptFileName(clazz.getName())+ "-model";
	}
	
	public static String getAttributesInterfacePath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "attributes" + File.separator + Utils.toTypescriptFileName(clazz.getName()) + "-attributes";
		}
		return MODEL_REPOSITORY +  "attributes" + File.separator + Utils.toTypescriptFileName(clazz.getName()) + "-attributes";
	}
	
	public static String getMetierClassPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "metier" + File.separator + Utils.toTypescriptFileName(clazz.getName())+ "-metier";
		}
		return MODEL_REPOSITORY +  "metier" + File.separator + Utils.toTypescriptFileName(clazz.getName())+ "-metier";
	}
	
	public static String getDtoClassPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "dto" + File.separator  + Utils.toTypescriptFileName(clazz.getName())+ "-dto";
		}
		return MODEL_REPOSITORY +  "dto" + File.separator + Utils.toTypescriptFileName(clazz.getName())+ "-dto";
	}
	
	public static String getDatabaseScriptPath(Package pakkage, Boolean inCode) {
		if(inCode) {
			return DATABASE_INCODE_REPOSITORY + "createTablesPostgres";
		}
		return DATABASE_REPOSITORY + "createTablesPostgres";
	}
	
	public static String getModelDaoPath(Package pakkage, Boolean inCode) {
		if(inCode) {
			return DAO_INCODE_REPOSITORY + "model-dao";
		}
		return DAO_REPOSITORY + "model-dao";
	}
	
	public static String getEnumPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY + Utils.toTypescriptFileName(clazz.getName()) + "-enum";
		}
		return MODEL_REPOSITORY  +Utils.toTypescriptFileName(clazz.getName())+ "-enum";
	}
	
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
}
