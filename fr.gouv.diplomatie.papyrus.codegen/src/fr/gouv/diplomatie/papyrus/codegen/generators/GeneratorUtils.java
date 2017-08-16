package fr.gouv.diplomatie.papyrus.codegen.generators;

import java.io.File;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils;

public class GeneratorUtils {
	
	public static final String MODEL_REPOSITORY = "src" + File.separator + "models" + File.separator;
	public static final String DATABASE_REPOSITORY = "database" + File.separator ;
	public static final String DAO_REPOSITORY = "src" + File.separator  + "dao" + File.separator ;
	
	public static String getModelPath(Classifier clazz) {
		return MODEL_REPOSITORY +  "models" + File.separator + "model-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getAttributesInterfacePath(Classifier clazz) {
		return MODEL_REPOSITORY +  "attributes" + File.separator + "attributes-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getMetierClassPath(Classifier clazz) {
		return MODEL_REPOSITORY +  "metier" + File.separator + "metier-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getDtoClassPath(Classifier clazz) {
		return MODEL_REPOSITORY +  "dto" + File.separator + "dto-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getDatabaseScriptPath(Package pakkage) {
		return DATABASE_REPOSITORY + "createTablesPostgres";
	}
	
	public static String getModelDaoPath(Package pakkage) {
		return DAO_REPOSITORY + "model-dao";
	}

}
