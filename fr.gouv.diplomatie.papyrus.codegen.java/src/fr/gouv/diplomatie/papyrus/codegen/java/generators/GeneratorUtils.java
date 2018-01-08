package fr.gouv.diplomatie.papyrus.codegen.java.generators;

import java.io.File;

import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class GeneratorUtils {

	
	public static final String SRC_REPOSITORY = "src-gen" + File.separator;
	public static final String SRC_INCODE_REPOSITORY = "src" + File.separator;
	
	public static final String MAIN_REPOSITORY = "main" + File.separator;
	public static final String JAVA_REPOSITORY = "java" + File.separator;
	public static final String INTEGRATION_REPOSITORY = "integration" + File.separator;
	
	/**
	 * répertoire contenant le fichier de l'entity
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getEntityPath(Classifier clazz, Boolean inCode) {
		
		String rootPackage = GeneratorUtils.getEntityPackage(clazz);
		
		if(inCode) {
			return SRC_INCODE_REPOSITORY + MAIN_REPOSITORY + JAVA_REPOSITORY + rootPackage;
		}
		return SRC_REPOSITORY + MAIN_REPOSITORY + JAVA_REPOSITORY + rootPackage ;
	}
	
	public static String getEntityPackage(Classifier clazz) {
		String rootPackage = Utils.getPackagePath(clazz);
		
		return rootPackage + File.separator + INTEGRATION_REPOSITORY + Utils.getFirstToLowerCase(clazz.getName());
	}
	
	
}
