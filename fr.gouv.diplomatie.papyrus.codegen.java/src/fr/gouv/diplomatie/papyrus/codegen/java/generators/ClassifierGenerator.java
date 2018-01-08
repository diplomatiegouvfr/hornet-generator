package fr.gouv.diplomatie.papyrus.codegen.java.generators;

import java.io.File;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier.ClassifierJPAEntityGenerator;
import fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier.NomenclatureConverterGenerator;
import fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier.NomenclatureGenerator;

public class ClassifierGenerator {

	public static void generateEntity(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		
		String fileName = GeneratorUtils.getEntityPath(clazz, false) + File.separator + clazz.getName() + ".java";
		
		if(ClassifierUtils.canBeGenerated(clazz)) {
			
			Utils.console.out.println("ClassifierGenerator.generateEntity : "  + clazz.getName() + ", fichier : " + fileName);
			fileSystemAccess.generateFile(fileName, ClassifierJPAEntityGenerator.generateCode(clazz).toString());
			
		}else {
			Utils.console.warning.println("L'entité JPA de la classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	public static void generateNomenclature(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		
		String fileName = GeneratorUtils.getEntityPath(clazz, false) + File.separator + clazz.getName() + ".java";
		
		if(ClassifierUtils.canBeGenerated(clazz)) {
			
			Utils.console.out.println("ClassifierGenerator.generateNomenclature : "  + clazz.getName() + ", fichier : " + fileName);
			fileSystemAccess.generateFile(fileName, NomenclatureGenerator.generateCode(clazz).toString());
			
		}else {
			Utils.console.warning.println("L'entité JPA de la classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	public static void generateNomenclatureConverter(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		
		String fileName = GeneratorUtils.getEntityPath(clazz, false) + File.separator + clazz.getName() + "Converter.java";
		
		if(ClassifierUtils.canBeGenerated(clazz)) {
			
			Utils.console.out.println("ClassifierGenerator.generateNomenclatureConverter : "  + clazz.getName() + ", fichier : " + fileName);
			fileSystemAccess.generateFile(fileName, NomenclatureConverterGenerator.generateCode(clazz).toString());
			
		}else {
			Utils.console.warning.println("Le converter de la classe "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
}
