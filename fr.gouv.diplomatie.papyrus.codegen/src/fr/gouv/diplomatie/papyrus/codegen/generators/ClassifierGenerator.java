package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierAttributesInterfaceGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierMetierClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.ClassifierModelGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.utils.ClassifierUtils;

public class ClassifierGenerator{
	
	public static void generateModel(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getModelPath(clazz, false) + ".ts";
		
		if(ClassifierUtils.canBeGenerated(clazz)) {
			GeneratorUtils.out.println("ClassifierGenerator.generateModel : "  + clazz.getName() + ", fichier : " + fileName);
			fileSystemAccess.generateFile(fileName, ClassifierModelGenerator.generateCode(clazz).toString());
		}else {
			GeneratorUtils.out.println("Le modèle de la classe "+ clazz.getName() + " ne sera pas généré car elle possède une propriété generated à false");
		}
		
	}
	
	public static void generateAttributesInterface(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			GeneratorUtils.out.println("ClassifierGenerator.generateAttributesInterface : "  + clazz.getName() + ", fichier : " + fileName);
			
			fileSystemAccess.generateFile(fileName, ClassifierAttributesInterfaceGenerator.generateCode(clazz).toString());
		}else {
			GeneratorUtils.out.println("L'interface attributs de la classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	public static void generateMetierClass(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			GeneratorUtils.out.println("ClassifierGenerator.generateMetierClass : "  + clazz.getName() + ", fichier : " + fileName);
			
			fileSystemAccess.generateFile(fileName, ClassifierMetierClassGenerator.generateCode(clazz).toString());
		}else {
			GeneratorUtils.out.println("La classe metier de la classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
		
	}
	
	public static void generateDto(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getDtoClassPath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			GeneratorUtils.out.println("ClassifierGenerator.generateDto : "  + clazz.getName() + ", fichier : " + fileName);
			
			fileSystemAccess.generateFile(fileName, ClassifierDtoClassGenerator.generateCode(clazz).toString());	
		}else {
			GeneratorUtils.out.println("Le dto de la classe "+ clazz.getName() + " ne sera pas généré car elle possède une propriété generated à false");
		}
	}
	
	public static void generateValueObjectMetierClass(Classifier clazz,  IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getMetierClassPath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			GeneratorUtils.out.println("ClassifierGenerator.generateValueObjectMetierClass : "  + clazz.getName() + ", fichier : " + fileName);
			
			fileSystemAccess.generateFile(fileName, ClassifierMetierClassGenerator.generateCode(clazz).toString());
		}else {
			GeneratorUtils.out.println("La classe metier de la classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}

}
