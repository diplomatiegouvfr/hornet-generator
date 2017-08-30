package fr.gouv.diplomatie.papyrus.codegen.generators;

import java.io.File;

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
	
	public static String getModelPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "models" + File.separator + "model-" + Utils.toTypescriptFileName(clazz.getName());
		}
		return MODEL_REPOSITORY +  "models" + File.separator + "model-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getAttributesInterfacePath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "attributes" + File.separator + "attributes-" + Utils.toTypescriptFileName(clazz.getName());
		}
		return MODEL_REPOSITORY +  "attributes" + File.separator + "attributes-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getMetierClassPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "metier" + File.separator + "metier-" + Utils.toTypescriptFileName(clazz.getName());
		}
		return MODEL_REPOSITORY +  "metier" + File.separator + "metier-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getDtoClassPath(Classifier clazz, Boolean inCode) {
		if(inCode) {
			return MODEL_INCODE_REPOSITORY +  "dto" + File.separator + "dto-" + Utils.toTypescriptFileName(clazz.getName());
		}
		return MODEL_REPOSITORY +  "dto" + File.separator + "dto-" + Utils.toTypescriptFileName(clazz.getName());
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
			return MODEL_INCODE_REPOSITORY + "enum-" + Utils.toTypescriptFileName(clazz.getName());
		}
		return MODEL_REPOSITORY  + "enum-" + Utils.toTypescriptFileName(clazz.getName());
	}

}
