package fr.gouv.diplomatie.papyrus.codegen.generators;

import org.eclipse.papyrus.infra.tools.file.IPFileSystemAccess;
import org.eclipse.uml2.uml.Classifier;

import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureAttributesClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureDtoClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureEnumClassGenerator;
import fr.gouv.diplomatie.papyrus.codegen.xtend.classifier.NomenclatureModelGenerator;

public class NomenclatureGenerator {

	public static void generateEnumClass(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("NomenclatureGenerator.generateEnumClass : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getEnumPath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, NomenclatureEnumClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateEnumModel(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("NomenclatureGenerator.generateEnumModel : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getModelPath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, NomenclatureModelGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateEnumDto(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("NomenclatureGenerator.generateEnumDto : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getDtoClassPath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, NomenclatureDtoClassGenerator.generateCode(clazz).toString());
		
	}
	
	public static void generateEnumAttributesInterface(Classifier clazz, IPFileSystemAccess fileSystemAccess) {
		System.out.println("NomenclatureGenerator.generateEnumAttributesInterface : "  + clazz.getName());
		
		String fileName = GeneratorUtils.getAttributesInterfacePath(clazz) + ".ts";
		
		fileSystemAccess.generateFile(fileName, NomenclatureAttributesClassGenerator.generateCode(clazz).toString());
		
	}
}
