package fr.gouv.diplomatie.papyrus.codegen.typescript.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureAttributesClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureEnumClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.NomenclatureModelGenerator;
import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;

public class NomenclatureGenerator{

	public static void generateEnumClass(Classifier clazz, IPFileSystemAccess fileSystemAccess) {		
		String fileName = GeneratorUtils.getEnumPath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			Utils.console.out.println("NomenclatureGenerator.generateEnumClass : "  + clazz.getName() + ", fichier : " + fileName);
			
			fileSystemAccess.generateFile(fileName, NomenclatureEnumClassGenerator.generateCode(clazz).toString());
		}else {
			Utils.console.warning.println("La classe metier de l'énumération  "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
	
	public static void generateEnumModel(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getModelPath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			Utils.console.out.println("NomenclatureGenerator.generateEnumModel : "  + clazz.getName() + ", fichier : " + fileName);
	
			fileSystemAccess.generateFile(fileName, NomenclatureModelGenerator.generateCode(clazz).toString());
		}else {
			Utils.console.warning.println("Le modèle de l'énumération  "+ clazz.getName() + " ne sera pas généré car elle possède une propriété generated à false");
		}
	}
	
	public static void generateEnumDto(Classifier clazz, IPFileSystemAccess fileSystemAccess) {		
		String fileName = GeneratorUtils.getDtoClassPath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			Utils.console.out.println("NomenclatureGenerator.generateEnumDto : "  + clazz.getName() + ", fichier : " + fileName);
	
			fileSystemAccess.generateFile(fileName, NomenclatureDtoClassGenerator.generateCode(clazz).toString());
		}else {
			Utils.console.warning.println("Le dto de l'énumération  "+ clazz.getName() + " ne sera pas généré car elle possède une propriété generated à false");
		}
	}
	
	public static void generateEnumAttributesInterface(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz, false) + ".ts";
		if(ClassifierUtils.canBeGenerated(clazz)) {
			Utils.console.out.println("NomenclatureGenerator.generateEnumAttributesInterface : "  + clazz.getName() + ", fichier : " + fileName);
	
			fileSystemAccess.generateFile(fileName, NomenclatureAttributesClassGenerator.generateCode(clazz).toString());
		}else {
			Utils.console.warning.println("La'interface attributs de l'énumération  "+ clazz.getName() + " ne sera pas générée car elle possède une propriété generated à false");
		}
	}
}
