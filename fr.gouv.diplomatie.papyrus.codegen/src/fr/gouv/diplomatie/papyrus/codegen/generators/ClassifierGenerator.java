package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierAttributesInterfaceGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierMetierClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierModelGenerator;

public class ClassifierGenerator extends HornetGenerator{
	
	public static void generateModel(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getModelPath(clazz, false) + ".ts";
		
		out.println("ClassifierGenerator.generateModel : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, ClassifierModelGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateAttributesInterface(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz, false) + ".ts";
		
		out.println("ClassifierGenerator.generateAttributesInterface : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, ClassifierAttributesInterfaceGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateMetierClass(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		
		out.println("ClassifierGenerator.generateMetierClass : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, ClassifierMetierClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateDto(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getDtoClassPath(clazz, false) + ".ts";
		
		out.println("ClassifierGenerator.generateDto : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, ClassifierDtoClassGenerator.generateCode(clazz).toString());	
		
	}
	
	public static void generateValueObjectMetierClass(Classifier clazz,  IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		
		out.println("ClassifierGenerator.generateValueObjectMetierClass : "  + clazz.getName() + ", fichier : " + fileName);
		
		fileSystemAccess.generateFile(fileName, ClassifierMetierClassGenerator.generateCode(clazz).toString());
	}

}
