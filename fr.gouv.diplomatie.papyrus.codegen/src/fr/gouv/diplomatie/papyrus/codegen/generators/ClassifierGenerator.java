package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierAttributesInterfaceGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierMetierClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierModelGenerator;

public class ClassifierGenerator {
	
	public static void generateModel(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("ClassifierGenerator.generateModel : "  + clazz.getName());

		String fileName = GeneratorUtils.getModelPath(clazz, false) + ".ts";
		
		fileSystemAccess.generateFile(fileName, ClassifierModelGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateAttributesInterface(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("ClassifierGenerator.generateAttributesInterface : "  + clazz.getName());

		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz, false) + ".ts";
		
		fileSystemAccess.generateFile(fileName, ClassifierAttributesInterfaceGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateMetierClass(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("ClassifierGenerator.generateMetierClass : "  + clazz.getName());

		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		
		fileSystemAccess.generateFile(fileName, ClassifierMetierClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateDto(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("ClassifierGenerator.generateDto : "  + clazz.getName());

		String fileName = GeneratorUtils.getDtoClassPath(clazz, false) + ".ts";
		
		fileSystemAccess.generateFile(fileName, ClassifierDtoClassGenerator.generateCode(clazz).toString());	
		
	}
	
	public static void generateValueObjectMetierClass(Classifier clazz,  IPFileSystemAccess fileSystemAccess) {
		System.out.println("ClassifierGenerator.generateValueObjectMetierClass : "  + clazz.getName());

		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		
		fileSystemAccess.generateFile(fileName, ClassifierMetierClassGenerator.generateCode(clazz).toString());
	}

}
