package fr.gouv.diplomatie.papyrus.codegen.core.generators;

import java.io.File;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class GeneratorUtils {

	
	public static final String SRC_REPOSITORY = "src-gen" + File.separator;
	public static final String SRC_INCODE_REPOSITORY = "src" + File.separator;
	
	public static final String MODEL_REPOSITORY = SRC_REPOSITORY + "models" + File.separator;
	public static final String MODEL_INCODE_REPOSITORY = SRC_INCODE_REPOSITORY + "models" + File.separator;
	
	public static final String DATABASE_REPOSITORY = SRC_REPOSITORY + "database" + File.separator ;
	
	public static final String DAO_REPOSITORY = SRC_REPOSITORY  + "dao" + File.separator ;
	public static final String DAO_INCODE_REPOSITORY = SRC_INCODE_REPOSITORY  + "dao" + File.separator ;
	
	/**
	 * chemin du fichier contenant la classe modèle de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getModelPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "models" + File.separator  + Utils.toTypescriptFileName(clazz.getName()) + "-model";
		}
		return MODEL_REPOSITORY +  "models" + File.separator  + Utils.toTypescriptFileName(clazz.getName())+ "-model";
	}
	
	/**
	 * chemin du fichier contenant l'interface attributes de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getAttributesInterfacePath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "attributes" + File.separator + Utils.toTypescriptFileName(clazz.getName()) + "-attributes";
		}
		return MODEL_REPOSITORY +  "attributes" + File.separator + Utils.toTypescriptFileName(clazz.getName()) + "-attributes";
	}
	
	/**
	 * chemin du fichier contenant la classe metier de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getMetierClassPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "metier" + File.separator + Utils.toTypescriptFileName(clazz.getName())+ "-metier";
		}
		return MODEL_REPOSITORY +  "metier" + File.separator + Utils.toTypescriptFileName(clazz.getName())+ "-metier";
	}
	
	/**
	 * chemin du fichier contenant le dto de la classe clazz
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getDtoClassPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "dto" + File.separator  + Utils.toTypescriptFileName(clazz.getName())+ "-dto";
		}
		return MODEL_REPOSITORY +  "dto" + File.separator + Utils.toTypescriptFileName(clazz.getName())+ "-dto";
	}
	
	/**
	 * chemin du fichier de création de la base de données
	 * @return
	 */
	public static String getDatabaseScriptPath(Package pakkage) {
		return DATABASE_REPOSITORY + "createTablesPostgres";
	}
	
	/**
	 * chemin du fichier modelDAO
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getModelDaoPath(Package pakkage, Boolean inCode) {
		if(inCode) {
			return DAO_INCODE_REPOSITORY + "model-dao";
		}
		return DAO_REPOSITORY + "model-dao";
	}
	
	/**
	 * chemin du fichier contenant la classe Metier de l'enum
	 * @param clazz
	 * @param inCode indique si le chemin est pour la génération du chemin du fichier  ou pour un import dans le code
	 * @return
	 */
	public static String getEnumPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY + Utils.toTypescriptFileName(clazz.getName()) + "-enum";
		}
		return MODEL_REPOSITORY  +Utils.toTypescriptFileName(clazz.getName())+ "-enum";
	}
	
}
