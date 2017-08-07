package fr.gouv.diplomatie.papyrus.codegen.generators;

import java.io.File;

import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.Utils;

public class GeneratorUtils {
	
	public static final String MODEL_REPOSITORY = "src" + File.separator + "models" + File.separator;
	
	public static String getModelPath(Classifier clazz) {
		return MODEL_REPOSITORY +  "model-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getAttributesInterfacePath(Classifier clazz) {
		return MODEL_REPOSITORY +  "attributes-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getMetierPath(Classifier clazz) {
		return MODEL_REPOSITORY +  "metier-" + Utils.toTypescriptFileName(clazz.getName());
	}
	
	public static String getDtoPath(Classifier clazz) {
		return MODEL_REPOSITORY +  "dto-" + Utils.toTypescriptFileName(clazz.getName());
	}

}
